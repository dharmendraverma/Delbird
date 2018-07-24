package in.delbird.chemist.Controller;

public class Constants {

   // public static final String varify = "http://52.37.31.105:8080/dellbird/user/verify/phone?mobile_no=";


    public static final String chekvarifiyUser = "http://52.37.31.105:8080/dellbird/check/user/phone?mobile_no=";
    //public static final String createUser = "http://52.37.31.105:8080/dellbird/create/user";
    public static final String nearByShopList = "http://52.2.132.71:8080/dellbird/nearby/shop?";
    public static final String shopCategoryList = "http://192.168.0.132:10095/dellbird/place/list?";

    //public static final String CategoryList = "http://52.37.31.105:8080/dellbird/get/categories/icon";

    //roshi urls
    public static final String CategoryList = "http://52.77.98.134/api/get/category";
    public static final String SerachList="http://52.77.98.134/api/search?";
    public static final String RetailerList = "http://52.77.98.134/api/get/retailer/list?";
    public static final String varify = "http://52.77.98.134/api/user/verify/phone?mobile_no=";
    public static final String createUser = "http://52.77.98.134/api/user/update?user_id=%s&name=%s&email=%s&gcm_id=%s&gender=%s&dob=%s";
    public static final String SHOP_LIST_URL = "http://52.77.98.134/api/get/product/user?";
    public static final String CHECKOUT_URL = "http://52.77.98.134/api/user/checkoutCart";
    public static final String InvoiceList = "http://52.77.98.134/api/user/getInvoiceHistory?";
    public static final String dispatchRequest = "http://52.77.98.134/api/user/invoice/statuschange?";
    public static final String InvoiceDetail = "http://52.77.98.134/api/get/user/invoice/items?";
    //public static final String RETAILER_LIST_URL = "http://52.77.98.134/api/get/retailer/list";

    //public static final String varify = "http://52.37.31.105:8080/dellbird/user/verify/phone?mobile_no=";

   // public static final String CategoryList = "http://192.168.1.67:8080/dellbird/get/categories/icon";

    public static final String DEAL_LIST_URL_BY_LAT_LON = "http://52.37.31.105:8080/dellbird/nearby/shop?lat=";

    public static final String PRODUCT_MODEL = "shop_model";

    public static final String SHOP_MODEL = "shop_model";
    public static final String USERID = "user_id";
    public static final String ISNEWUSER = "is_new_user";
    public static final String MY_LOCATION_NAME = "my_location";
    public static final String PRODUCT_DETAIL_MODEL = "product_details";
    public static final String RETAILER_IDS = "retailer_ids";
    public static String SHOP_LIST_MODEL = "Shop List Model";
    public static String SHAREDPREFERENCES_NAME = "details_file";
    public static final String delbird3Konotorid = "322cb3b4-5100-4766-84c4-699e9f705821";
    public static final String getDelbird3Konotorkey = "a5b4667a-747c-43fd-b1ad-69074b258324";
    public static final String PRODUCT_LIST_IP_URL = "http://52.37.31.105:8080/dellbird/get/product/list?shop_id=";


    // New 52.37.31.105:8080
    // Old 52.2.132.71:8080
    public static int count = 0;
    public static String USER_ID = "user_id";
    public static String USER_NAME = "user_name";
    public static String EMAIL_ID = "email_id";
    public static String IS_ALREADY_LOGGED_IN = "is_already_logged_in";
    // public static String SHARED_PREFERENCES = "shared_prefs";
    public static String MOBILE_NUMBER = "mobile_number";
    public static String OTP_CODE = "otpcode";
    public static String OTP_STATUS = "otpstatus";
    public static String FLAG = "flage1";
    public static String OTP_MOBILENUM = "Mob";
    public static String MOBILE_NUM_SEND = "ContactNum";
    public static String CATEGORY = "category_type";
    public static String UPDATE_USER_URL = "http://52.37.31.105:8080/dellbird/create/user";
    //public static String SHOP_LIST_URL = "http://52.37.31.105:8080/dellbird/place/list?";


    public static String CURRENT_LOCATION="current_location";
    public static boolean Desciption_FLAG=false;
    public static String GCM_ID="AIzaSyC8p5DOHMENdAA17uNF7iWmx7X8WfjjNYM";
    public static String prescriptionsUrl = "http://52.77.98.134/api/user/getPrescription?user_id=%s";
    public static String prescriptionsDetailUrl="http://52.77.98.134/api/get/user/prescription/items?prescriptionMaster_id=%s";
}
