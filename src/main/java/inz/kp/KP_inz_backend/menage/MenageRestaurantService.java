package inz.kp.KP_inz_backend.menage;

import inz.kp.KP_inz_backend.User.UserRepository;
import inz.kp.KP_inz_backend.data.*;
import inz.kp.KP_inz_backend.menage.rnr.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenageRestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private TablesRepository tablesRepository;
    @Autowired
    private RatingsRepository ratingsRepository;
    @Autowired
    private ReservationsRepository reservationsRepository;
    @Autowired
    private CitiesRepository citiesRepository;
    @Autowired
    private FoodTypeRepository foodTypeRepository;

    public Long setDescription(
            SetDescriptionRequest request
    ) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var findByOwner = restaurantRepository.findByUserId(uID);

        var find_city = citiesRepository.findByCity_name(request.getCity());
        if(find_city.isEmpty())return -1L;

        var find_food = foodTypeRepository.findByFood_name(request.getFood_type());
        if(find_food.isEmpty())return -1L;

        if(findByOwner.isEmpty()){
            Restaurant restaurant = new Restaurant(
                    userRepository.getReferenceById(uID),
                    find_city.get(),
                    request.getName(),
                    request.getDescription(),
                    find_food.get(),
                    request.getAddres(),
                    request.getOpen(),
                    request.getClose()
            );
            restaurantRepository.save(restaurant);
        } else {
            restaurantRepository.updateRecord(
                    findByOwner.get().getId(),
                    request.getName(),
                    request.getDescription(),
                    find_food.get(),
                    request.getAddres(),
                    request.getOpen(),
                    request.getClose(),
                    find_city.get()
            );
        }
        return 0L;
    }
    public GetDescriptionResponse getDescription() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }
        var findByOwner = restaurantRepository.findByUserId(uID);

        if(findByOwner.isPresent()) {
            var record = findByOwner.get();
            return new GetDescriptionResponse(
                    record.getName(),
                    record.getDescription(),
                    record.getFood_type().getFood_name(),
                    record.getAddres(),
                    record.getCity().getCity_name(),
                    record.getOpen(),
                    record.getClose()
            );
        }
        else return new GetDescriptionResponse();
    }

    public Long addToMenu(AddToMenuRequest request) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var findByOwner = restaurantRepository.findByUserId(uID);
        if(findByOwner.isEmpty()) return -1L;

        var dish = new Menu(
                findByOwner.get(),
                request.getName(),
                request.getInde(),
                request.getDesc(),
                request.getCost(),
                request.isAvailable()
        );
        menuRepository.save(dish);
        return 0L;
    }

    public List<MenuResponse> showMenu() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        List<MenuResponse> resp = new ArrayList<>();
        for(Menu m : menuRepository.findAllByUserId(uID))
            resp.add(new MenuResponse(
                    m.getCost(),
                    m.getName(),
                    m.getIndegriends(),
                    m.getDescription(),
                    m.getId()
            ));

        return resp;
    }
    public Long setTables(List<SetTablesRequest> request) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var rest = restaurantRepository.findByUserId(uID);
        if(rest.isEmpty()) return -1L;

        var tables = tablesRepository.findByOwnerId(uID);

        var contained = tables.stream().map(Tables::getTable_size).toList();

        for (SetTablesRequest rec : request) {
            if(rec.getTable_count() == 0){
                tablesRepository.deleteRecord(
                        rec.getTable_size(),
                        rest.get()
                );
            }else if(contained.contains(rec.getTable_size())) {
                tablesRepository.updateTablesCount(
                        rest.get(),
                        rec.getTable_size(),
                        rec.getTable_count()
                );
            }else {
                tablesRepository.save(new Tables(
                        rest.get(),
                        rec.getTable_size(),
                        rec.getTable_count()
            ));
            }
        }

        return 0L;
    }

    public List<GetTablesResponse> getTables() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var rest = restaurantRepository.findByUserId(uID);
        if(rest.isEmpty()) return new ArrayList<>();

        var tab = tablesRepository.findByOwnerId(uID);

        return tab
                .stream()
                .map(tables -> new GetTablesResponse(
                        tables.getTable_size(),
                        tables.getTable_count()
                ))
                .toList();

    }

    public List<UsersReservations> showReservations() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }
        var rest = restaurantRepository.findByUserId(uID);
        if(rest.isEmpty())return new ArrayList<>();
        LocalDate today = LocalDate.now();

        return reservationsRepository
                .showNextReservations(rest.get().getId(),today)
                .stream()
                .map(reservations ->
                        new UsersReservations(
                                reservations.getUser().getEmail(),
                                reservations.getDay(),
                                reservations.getTime(),
                                reservations.getTable_size()
                        ))
                .toList();

    }

    public Long delFromMenu(Integer id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var rest = restaurantRepository.findByUserId(uID);
        return rest.map(
                restaurant -> (long) menuRepository.deleteRecord(id, restaurant.getId())
        ).orElse(-1L);
    }

    public Long validate() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var rest = restaurantRepository.findByUserId(uID);

        if(
           rest.isEmpty() ||
           menuRepository.findAllByUserId(uID).isEmpty() ||
           tablesRepository.findByOwnerId(uID).isEmpty()
        )return -1L;

        if(ratingsRepository.findByRestId(rest.get().getId()).isEmpty()) {
            ratingsRepository.save(
                    new Ratings(
                            rest.get()
                    )
            );
        }

        return (long) restaurantRepository.setValidByOwner(uID);
    }

    public Boolean isValid() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authentication.getAuthorities());

        int uID = 0;
        for (GrantedAuthority auth : grantedAuthorities) {
            if(auth.getAuthority().contains("ID_"))
                uID = Integer.parseInt(auth.getAuthority().replaceAll("[^0-9]", ""));
        }

        var rest = restaurantRepository.findByUserId(uID);
        return rest.map(Restaurant::isValid).orElse(false);
    }
}
