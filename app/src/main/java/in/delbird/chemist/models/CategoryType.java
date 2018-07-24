package in.delbird.chemist.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public enum CategoryType {
	/*food and entertainment*/
	ICE_CREAM,JUICE_SHAKES,RESTAURANT,BAKERY,BAR,WINE_SHOP,BOWLING_ALLEY,MOVIE_THEATER,CASINO,AMUSEMENT_PARK,
	GROCERY,MEAL_DELIVERY,NIGHT_CLUB,MEAL_TAKEAWAY,CAFE,LIQUOR_STORE,
	/* travel */
	BUS_STATION,METRO_STATION,TRAIN_STATION,AIRPORT,TAXI_STAND,TRAVEL_AGENCY,CAR_RENTAL,PARKING,CAR_DEALER,

	/* places of interest */
	AQUARIUM,ART_GALLERY,MUSEUM,ZOO,RELIGIOUS_PLACE,

	/*Outdoor/Wellness*/
	GARDEN,STADIUM,GYM,YOGA_MEDITATION,SPA,PHARMACY,HOSPITAL,DOCTOR,HAIR_CARE,DENTIST,BEAUTY_SALON,HEALTH,

	/*Repairs & daily needs */
	DAILY_NEEDS,FRUIT_VEGETABLE,PLUMBER,ELECTRICIAN,LAUNDRY,CAR_REPAIR,PAINTER,BIKE_REPAIR,CONSTRUCTION,
	DEPARTMENT_STORE,STORE,CAR_WASH,CONVENIENCE_STORE,HOME_GOODS_STORE,
	/* shopping */
	SHOPPING_MALL,CLOTH_STORE,FURNITURE_STORE,ELECTRONICS_STORE,JEWELLERY_STORE,PET_STORE,BOOK_STORE,HARDWARE_STORE,
	SHOE_STORE,BICYCLE_STORE,
	/* misc */
	POLICE_STATION,FIRE_STATION,POST_COURIER,MOVER_PACKER,ATM_BANK,PETROL_PUMP,FLORIST,AMBULANCE,VETERINARY,
	LAWYER,INSURANCE_AGENCY;
	public static CategoryType getCategoryType(String type){
		for(CategoryType t : CategoryType.values()){
			if(t.toString().toLowerCase().equals(type.toLowerCase()))
				return t;
		}
		return null;
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		CategoryType s=CategoryType.VETERINARY;//enum type is required to access WINTER
		System.out.println("enum = "+s);  // since the java enum constants are static and final implicitly.
		Integer lst = CategoryType.values().length;
		System.out.println("Length = "+ lst);
		List<CategoryType> lst2 = new ArrayList<CategoryType>();
		//System.out.println("list size = "+lst2.size());
		for (CategoryType ex : CategoryType.values()) {
		//for (EnumExample2 ex : lst2)
			//System.out.println(ex);
			lst2.add(ex);
		}
		System.out.println("Category Enum list = "+gson.toJson(lst2));
	}
}
