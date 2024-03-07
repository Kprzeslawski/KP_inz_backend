package inz.kp.KP_inz_backend.restaurants;


import inz.kp.KP_inz_backend.User.UserRepository;
import inz.kp.KP_inz_backend.data.*;
import inz.kp.KP_inz_backend.menage.rnr.GetTablesResponse;
import inz.kp.KP_inz_backend.menage.rnr.MenuResponse;
import inz.kp.KP_inz_backend.restaurants.rnr.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RestaurantsService {

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

    public List<RestaurantInfo> getRestaurants() {
        return restaurantRepository
                .findAllValid()
                .stream()
                .map(
                        record ->
                                new RestaurantInfo(
                                        record.getId(),
                                        record.getName(),
                                        record.getFood_type().getFood_name(),
                                        record.getCity().getCity_name(),
                                        record.getAddres(),
                                        record.getRatings().getReviews(),
                                        record.getRatings().getReviews() != 0 ?
                                                ((float) record.getRatings().getStars_geted() / record.getRatings().getReviews()) :
                                                0
                                )
                ).toList();
    }

    public Optional<RestaurantDetails> getRestaurant(Integer id) {
        var rec = restaurantRepository.getReferenceById((long )id);
        if(!rec.isValid()) return Optional.empty();
        return Optional.of(
                new RestaurantDetails(
                rec.getName(),
                rec.getDescription(),
                rec.getFood_type().getFood_name(),
                rec.getAddres(),
                rec.getOpen(),
                rec.getClose(),
                rec.getRatings().getReviews(),
                rec.getRatings().getReviews() != 0 ?
                        ((float) rec.getRatings().getStars_geted() / rec.getRatings().getReviews()) :
                        0
                )
        );

    }

    //not publ to rem
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

    public List<ReviewRec> showReviews(Integer id) {
        var rest = restaurantRepository.getReferenceById((long) id);
        if(!rest.isValid()) return new ArrayList<>();

        return rest
                .getRatings()
                .getReviewsList()
                .stream()
                .map(record ->
                        new ReviewRec(
                                record.getDescription(),
                                record.getRating_val(),
                                record.getUser().getUsername()
                        )
                )
                .toList();
    }

    //not publ to rem
    public List<Integer> showAvReservationsD(Integer id, LocalDate date, Integer tab) {
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

        for(int i=r.getOpen();i<r.getClose();i+=100)
            if(Collections.frequency(asIntArray,i) < tbCount.get())
                res.add(i);

        return res;
    }

    //not publ to rem
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


    public List<MenuResponse> showMenuId(Integer id) {

        List<MenuResponse> resp = new ArrayList<>();
        var r = restaurantRepository.getReferenceById((long)id);
        if(!r.isValid())return new ArrayList<>();

        for(Menu m : r.getMenu())
            resp.add(new MenuResponse(
                    m.getCost(),
                    m.getName(),
                    m.getIndegriends(),
                    m.getDescription(),
                    m.getId()
            ));

        return resp;
    }

    //not publ to rem
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

    //not publ to rem
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

    //not publ to rem
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

    //not publ to rem
    public List<Integer> getPosTables(Integer id) {
        var r = restaurantRepository.getReferenceById((long)id);
        if(!r.isValid())return new ArrayList<>();

        return r
                .getTables()
                .stream()
                .map(Tables::getTable_size)
                .toList();
    }

    public List<String> getCities() {
        return citiesRepository
                .findAll()
                .stream()
                .map(Cities::getCity_name)
                .toList();

    }

    public List<String> getFoodTypes() {
        return  foodTypeRepository
                .findAll()
                .stream()
                .map(FoodType::getFood_name)
                .toList();

    }
}
