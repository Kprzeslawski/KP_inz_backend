package inz.kp.KP_inz_backend.loggedUsers;

import inz.kp.KP_inz_backend.User.UserRepository;
import inz.kp.KP_inz_backend.data.*;
import inz.kp.KP_inz_backend.restaurants.rnr.AddReview;
import inz.kp.KP_inz_backend.restaurants.rnr.MakeReservation;
import inz.kp.KP_inz_backend.restaurants.rnr.ReservRec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggedUsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private ReservationsRepository reservationsRepository;

    @Autowired
    private TablesRepository tablesRepository;
    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private FoodTypeRepository foodTypeRepository;


    public Long addReview(AddReview request) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var rest = restaurantRepository.getReferenceById((long)request.getRest_id());
        if(!rest.isValid()) return -1L;
        if(!reviewsRepository.alreadyAdded(uID,request.getRest_id()).isEmpty())return -1L;

        ratingsRepository.adjustRating(rest.getRatings().getId(),request.getRating());

        reviewsRepository.save(
                new Reviews(
                        rest.getRatings(),
                        userRepository.getReferenceById(uID),
                        request.getRating(),
                        request.getDescription()
                )
        );

        return 0L;
    }


    public List<Integer> showAvReservationsD(Integer id, LocalDate date, Integer tab) {

        Integer time_interval =  100;
        var r = restaurantRepository.getReferenceById((long)id);
        if(!r.isValid())return new ArrayList<>();
        var tbCount = tablesRepository.getTableCount(tab,id);
        if(tbCount.isEmpty())return new ArrayList<>();

        List<Integer> asIntArray = reservationsRepository
                .showTableInDay(r.getId(),date,tab)
                .stream()
                .map(Reservations::getTime)
                .toList();

        var res = new ArrayList<Integer>();

        for(int i=r.getOpen(); i < r.getClose(); i += time_interval)
            if(Collections.frequency(asIntArray,i) < tbCount.get())
                res.add(i);

        return res;
    }

    public Long makeReservation(MakeReservation request) {
        var r = showAvReservationsD(request.getRest_id(),request.getDate(),request.getTable());
        if (!r.contains(request.getTime())) return -1L;

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var re = reservationsRepository.userReservInDay(uID,request.getDate());
        if(!re.isEmpty())return -1L;

        reservationsRepository.save(
                new Reservations(
                        request.getTime(),
                        request.getDate(),
                        request.getTable(),
                        restaurantRepository.getReferenceById((long)request.getRest_id()),
                        userRepository.getReferenceById(uID)
                )
        );

        return 0L;
    }


    public Boolean isRatingSet(Integer id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        return reviewsRepository.alreadyAdded(uID,id).isPresent();
    }


    public Boolean deleteMyReserv(Integer id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        return reservationsRepository.deleteReserv(id,uID) == 0;
    }


    public List<ReservRec> showMyReserv() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        return reservationsRepository
                .userReserv(uID)
                .stream()
                .map(rec ->
                        new ReservRec(
                                rec.getId(),
                                rec.getDay(),
                                rec.getTime(),
                                rec.getTable_size(),
                                rec.getRestaurant().getName()
                        ))
                .toList();
    }


    public List<Integer> getPosTables(Integer id) {
        var r = restaurantRepository.getReferenceById((long)id);
        if(!r.isValid())return new ArrayList<>();

        return r
                .getTables()
                .stream()
                .map(Tables::getTable_size)
                .toList();
    }
}
