package in.delbird.chemist.utils;

import java.util.HashMap;

/**
 * Created by Dharmendra on 3/2/16.
 */
public class CategoryNamesUtil {
    HashMap<String,String> hashMap = new HashMap<String,String>();

    public String  getCategoryName(String key)
    {
        return hashMap.get(key);
    }

    public void initializeHashMap()
    {
//        hashMap.put("ICE_CREAM","Ice_cream");
//        hashMap.put("JUICE_SHAKES","Juice_Shake");
        hashMap.put("RESTAURANT","Restaurant");
        hashMap.put("BAKERY","Bakery");
        hashMap.put("BAR","Bar");
//        hashMap.put("WINE_SHOP","Wine Shop");
//        hashMap.put("BOWLING_ALLEY","Bowling Alley");
//        hashMap.put("MOVIE_THEATER","Movie Threater");
//        hashMap.put("CASINO","Casino");
//        hashMap.put("AMUSEMENT_PARK","Amusement Park");
        hashMap.put("GROCERY","Grocery");
        hashMap.put("MEAL_DELIVERY","Meal Delivery");
        hashMap.put("NIGHT_CLUB","Night Club");
        hashMap.put("MEAL_TAKEAWAY","Meal takeAway");
        hashMap.put("CAFE","Cake");
        hashMap.put("LIQUOR_STORE","Liquor Store");
//        hashMap.put("BUS_STATION","Bus Station");
//        hashMap.put("METRO_STATION","Metro Station");
//        hashMap.put("TRAIN_STATION","Train Station");
//        hashMap.put("AIRPORT","Airport");
//        hashMap.put("TAXI_STAND","Taxi Stand");
        hashMap.put("TRAVEL_AGENCY","Travel Agency");
//        hashMap.put("CAR_RENTAL","Car Rental");
        hashMap.put("PARKING","Parking");
        hashMap.put("CAR_DEALER","Car Dealer");
//        hashMap.put("AQUARIUM","Aquarium");
//        hashMap.put("ART_GALLERY","Art Gallery");
//        hashMap.put("MUSEUM","Museum");
//        hashMap.put("ZOO","Zoo");
//        hashMap.put("RELIGIOUS_PLACE","Religious");
//        hashMap.put("GARDEN","Garden");
//        hashMap.put("STADIUM","Stadium");
        hashMap.put("GYM","Gym");
//        hashMap.put("YOGA_MEDITATION","Meditation");
        hashMap.put("SPA","Spa");
        hashMap.put("PHARMACY","Pharmancy");
//        hashMap.put("HOSPITAL","Haspital");
        hashMap.put("DOCTOR","Doctor");
        hashMap.put("HAIR_CARE","hair Care");
        hashMap.put("DENTIST","Dentist");
        hashMap.put("BEAUTY_SALON","Beauty Salon");
        hashMap.put("HEALTH","Health");
//        hashMap.put("DAILY_NEEDS","Daily Need");
//        hashMap.put("FRUIT_VEGETABLE","Fruit Veg");
        hashMap.put("PLUMBER","Plumber");
        hashMap.put("ELECTRICIAN","Electrician");
        hashMap.put("LAUNDRY","Laundry");
        hashMap.put("CAR_REPAIR","Car Repair");
        hashMap.put("PAINTER","Painter");
//        hashMap.put("BIKE_REPAIR","Bike Repair");
//        hashMap.put("CONSTRUCTION","Construction");
        hashMap.put("DEPARTMENT_STORE","Department");
        hashMap.put("STORE","Store");
        hashMap.put("CAR_WASH","Car Wash");
        hashMap.put("CONVENIENCE_STORE","Convenience");
        hashMap.put("HOME_GOODS_STORE","Home Good");
        hashMap.put("SHOPPING_MALL","Shoping Mall");
        hashMap.put("CLOTH_STORE","Cloth Store");
        hashMap.put("FURNITURE_STORE","Furniture");
//        hashMap.put("ELECTRONICS_STORE","Electronic");
        hashMap.put("JEWELLERY_STORE","Jewellary");
        hashMap.put("PET_STORE","Pet Store");
        hashMap.put("BOOK_STORE","Book Store");
        hashMap.put("HARDWARE_STORE","Hardware Store");
        hashMap.put("SHOE_STORE","Shoe Store");
        hashMap.put("BICYCLE_STORE","Bicycle Store");
//        hashMap.put("POLICE_STATION","Police station");
//        hashMap.put("FIRE_STATION","Fire Station");
//        hashMap.put("POST_COURIER","Post Counter");
//        hashMap.put("MOVER_PACKER","Mover Packer");
//        hashMap.put("ATM_BANK","Atm Bank");
//        hashMap.put("PETROL_PUMP","Petrol Pump");
        hashMap.put("FLORIST","Florist");
//        hashMap.put("AMBULANCE","Ambulence");
//        hashMap.put("VETERINARY","Veterinary");
        hashMap.put("LAWYER","Lawyer");
        hashMap.put("INSURANCE_AGENCY","Insurence Agency");


    }
}
