package ua.martishyn.app.utils.constants;

public class ControllerConstants {

    private ControllerConstants() {
    }

    /**
     * User controller constants
     */
    public static final String USER_REDIRECT = "redirect:/users/admin/all";
    public static final String USER_LIST = "/admin/users/user_list";
    public static final String USER_EDIT_PAGE = "/admin/users/user_edit";

    /**
     * Route controller constants
     */
    public static final String ROUTE_REDIRECT = "redirect:/admin/routes";
    public static final String ROUTE_LIST = "/admin/routes/route_list";
    public static final String ROUTE_ADD_PAGE = "/admin/routes/route_add";
    public static final String ROUTE_POINT_ADD_PAGE = "/admin/routes/route_point_add";
    public static final String ROUTE_POINT_EDIT_PAGE = "/admin/routes/route_point_edit";

    /**
     * Station controller constants
     */
    public static final String STATION_REDIRECT = "redirect:/admin/stations";
    public static final String STATION_LIST = "/admin/stations/station_list";
    public static final String STATION_ADD_PAGE = "/admin/stations/station_add";
    public static final String STATION_EDIT_PAGE = "/admin/stations/station_edit";

    /**
     * Wagon controller constants
     */
    public static final String WAGON_REDIRECT = "redirect:/admin/wagons";
    public static final String WAGON_LIST = "/admin/wagons/wagon_list";
    public static final String WAGON_ADD_PAGE = "/admin/wagons/wagon_add";
    public static final String WAGON_EDIT_PAGE = "/admin/wagons/wagon_edit";

    /**
     * Other controller constants
     */
    public static final String INDEX_PAGE = "/index";
    public static final String INDEX_PAGE_REDIRECT = "redirect:/index";

}
