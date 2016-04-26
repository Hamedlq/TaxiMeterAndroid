

package com.mibarim.main.core;

/**
 * Bootstrap constants
 */
public final class Constants {
    private Constants() {}

    public static final class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.mibarim.main";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "Mibarim";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.mibarim.main.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;
        /**
         * mobile login
         */
        public static final String REG_MOBILE = "mobile";
        public static final String REG_PASSWORD = "password";
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static final class Http {
        private Http() {}


        /**
         * Base URL for all requests
         */
//        public static final String URL_BASE = "http://localhost:50226";
//        public static final String URL_BASE = "http://mibarim.ir/CoreApi";
         public static final String URL_BASE = "http://mibarim.ir/testApp/CoreApi";


        /**
         * Authentication URL
         */
        public static final String URL_AUTH_FRAG = "/TokenAuthentication";

        public static final String URL_REGISTER_FRAG = "/RegisterWebUser";
        public static final String URL_MOBILE_CONFIRM_FRAG = "/ConfirmMobileNo";
        public static final String URL_AUTH = URL_BASE + URL_AUTH_FRAG;

        /**
         * RouteResponse Requesta
         */
        public static final String URL_INSERT_ROUTE = "/InsertUserRoute";
        public static final String URL_CONFIRM_ROUTE = "/ConfirmRoute";
        public static final String URL_NOT_CONFIRM_ROUTE = "/NotConfirmRoute";
        public static final String URL_JOIN_GROUP = "/JoinGroup";
        public static final String URL_DELETE_GROUP = "/DeleteGroupSuggest";
        public static final String URL_LEAVE_GROUP = "/LeaveGroup";
        public static final String URL_ACCEPT_ROUTE = "/AcceptSuggestedRoute";
        public static final String URL_DELETE_ROUTE_SUGGESTION = "/DeleteRouteSuggest";
        public static final String URL_DELETE_ROUTE = "/DeleteRoute";
        public static final String URL_GET_ROUTE = "/GetUserRoutes";
        /**
         * UserInfoService
         */
        public static final String URL_GET_MESSAGE = "/GetGroupComments";
        public static final String URL_SEND_MESSAGE = "/SubmitComment";
        public static final String MESSAGE_IMAGE_URL= "/GetCommentUserImage";
        /**
         * UserInfoService
         */
        public static final String URL_LICENSE_INFO = "/GetLicenseInfo";
        public static final String URL_CAR_INFO = "/GetCarInfo";
        public static final String URL_PERSON_INFO = "/GetPersonalInfo";
        public static final String URL_SET_PERSON_INFO = "/InsertPersoanlInfo";
        public static final String URL_SET_LICENSE_INFO = "/InsertLicenseInfo";
        public static final String URL_SET_CAR_INFO = "/InsertCarInfo";
        public static final String URL_SET_PERSON_IMAGE = "/InsertPersonalPic";
        public static final String URL_SET_LICENSE_IMAGE = "/InsertLicensePic";
        public static final String URL_SET_CAR_IMAGE = "/InsertCarPics";
        /**
         * List Users URL
         */
        public static final String URL_USERS_FRAG =  "/1/users";
        public static final String URL_USERS = URL_BASE + URL_USERS_FRAG;


        /**
         * List News URL
         */
        public static final String URL_NEWS_FRAG = "/1/classes/News";
        public static final String URL_NEWS = URL_BASE + URL_NEWS_FRAG;


        /**
         * List Checkin's URL
         */
        public static final String URL_CHECKINS_FRAG = "/1/classes/Locations";
        public static final String URL_CHECKINS = URL_BASE + URL_CHECKINS_FRAG;

        /**
         * PARAMS for auth
         */
        public static final String PARAM_USERNAME = "username";
        public static final String PARAM_PASSWORD = "password";
        public static final String PARAM_REG_USERNAME = "Mobile";
        public static final String PARAM_REG_PASSWORD = "Password";
        public static final String PARAM_GRANT_TYPE = "grant_type";
        public static final String PARAM_RESPONSE_TYPE = "response_type";
        public static final String PARAM_AUTHORIZATION = "Authorization";
        public static final String PARAM_CONTENT = "Content-Type";

        public static final String PARSE_APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
        public static final String PARSE_REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
        public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";


        public static final String ROUTE_IMAGE_URL = "/GetRouteUserImage";
        public static final String SUGGEST_ROUTE_URL = "/GetSuggestRoute";

    }

    public static final class Geocoding {
        public static final String SERVICE_BASE_URL = "https://maps.googleapis.com";
        public static final String SERVICE_URL = "/maps/api/geocode/json";
        public static final String LAT_LONG_KEY = "latlng";
        public static final String LANGUAGE_KEY = "language";
        public static final String LANGUAGE_VALUE = "fa";
        public static final String LOCATION_TYPE_KEY = "location_type";
        public static final String LOCATION_TYPE_VALUE = "GEOMETRIC_CENTER|APPROXIMATE";
        public static final String GOOGLE_SERVICE_KEY = "key";
        public static final String GOOGLE_SERVICE_VALUE = "AIzaSyAbHHEaaGfcm2jtmfdbvu_qraFZAbr0QGM";
    }


    public static final class Extra {
        private Extra() {}

        public static final String NEWS_ITEM = "news_item";

        public static final String USER = "user";

    }

    public static final class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.mibarim.main.";

    }

    public static class Notification {
        private Notification() {
        }

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }

}


