package in.delbird.chemist.models;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sahil on 3/4/16.
 */
public class CartModel implements Serializable {

    private static CartModel cartModel;
    HashMap<Long, ShopModel> shopModelsList;
    HashMap<Long, Integer> productQuantityModel;
    HashMap<String,String> images;

    int quantity;
    private AddressModel addressModel;

    private CartModel() {
        shopModelsList = new HashMap<>();
        productQuantityModel = new HashMap<>();
        addressModel = new AddressModel();
        quantity = 0;
        images = new HashMap<>();
    }

    public static CartModel getCartModelInstance() {
        if (cartModel == null) {
            cartModel = new CartModel();
        }
        return cartModel;
    }

    public int getCartItemsCount() {
        if (cartModel == null)
            return 0;
        return cartModel.quantity;
    }

    public void removeProduct(long productId) {
        if (cartModel == null)
            return;
        cartModel.shopModelsList.remove(productId);
        Integer thisproductQuantity=cartModel.productQuantityModel.get(productId);
        thisproductQuantity=thisproductQuantity==null?0:thisproductQuantity;
        cartModel.quantity -= thisproductQuantity;
        cartModel.productQuantityModel.remove(productId);
    }

    public void removeProductQuantity(long productId){
        Integer thisproductQuantity = cartModel.productQuantityModel.get(productId);
        cartModel.productQuantityModel.put(productId, 0);

    }

    public int decrementProductQuantity(long productId) {
        if (cartModel == null) {
            return 0;
        }
        Integer thisproductQuantity = cartModel.productQuantityModel.get(productId);
        thisproductQuantity=thisproductQuantity==null?0:thisproductQuantity;
        thisproductQuantity--;
        if (thisproductQuantity == 0) {
            removeProduct(productId);
            return thisproductQuantity;
        } else if (thisproductQuantity > 0) {
            cartModel.quantity--;
            cartModel.productQuantityModel.put(productId, thisproductQuantity);
            return thisproductQuantity;
        } else {
            cartModel.shopModelsList.remove(productId);
            cartModel.productQuantityModel.remove(productId);
            return 0;
        }
    }

    public int getThisProductQuantity(long productId) {
        if (cartModel == null) {
            return 0;
        }
        Integer thisproductQuantity = cartModel.productQuantityModel.get(productId);

        return thisproductQuantity==null?0:thisproductQuantity;
    }

    public int addProduct(ShopModel shopModel) {
        long productId = shopModel.getProductId();
        Integer thisproductQuantity = cartModel.productQuantityModel.get(productId);
        thisproductQuantity=thisproductQuantity==null?0:thisproductQuantity;
        thisproductQuantity++;
        cartModel.productQuantityModel.put(productId, thisproductQuantity);
        cartModel.quantity++;
        if (thisproductQuantity == 1) {
            cartModel.shopModelsList.put(productId, shopModel);
        }
        return thisproductQuantity;
    }

    public void clearCart() {
        if (cartModel == null) {
            return;
        }
        cartModel = new CartModel();
    }

    public ArrayList<ShopModel> getProductsList() {
        if(cartModel==null){
            return new ArrayList<>();
        }
        return new ArrayList<>(shopModelsList.values());
    }

    public double getTotalAmount(){
        if(cartModel==null)
            return 0;
        Double amount = 0d;
        ArrayList<ShopModel> shop =getProductsList();

        for (ShopModel shopModel : shop) {
            amount+=(shopModel.getPrice()*(1-(shopModel.getRetailer().getPriceDiscounted()/100.0)))
                    *Long.valueOf(cartModel.productQuantityModel.get(shopModel.getProductId()));
        }
        return amount;
    }

   /* public void setAddressModel(AddressModel addressModel) {
        if(cartModel==null)
            return;
        cartModel.addressModel=addressModel;
    }*/

    public AddressModel getAddressModel(){
        if (cartModel==null)
            return null;
        else
            return cartModel.addressModel;
    }

    public JSONObject buildRequestBody(String paymentType, String userId){
        if(cartModel==null)
            return null;
        else{
            try {
                JSONObject jsonObject = new JSONObject();

                JSONArray items = new JSONArray();

                ArrayList<ShopModel> shopModels = getProductsList();

                for (ShopModel shopModel : shopModels) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("product_id", shopModel.getProductId());
                    jsonObject1.put("retailer_id", shopModel.getRetailer().getRetailerId());
                    jsonObject1.put("quantity",cartModel.getThisProductQuantity(shopModel.getProductId()));
                    items.put(jsonObject1);
                }

                jsonObject.put("items",items);

                JSONObject payment = new JSONObject();
                payment.put("payment_mode",paymentType);
                payment.put("payment_amount",String.valueOf(cartModel.getTotalAmount()));

                jsonObject.put("payment",payment);
                jsonObject.put("delivery_type",addressModel.deliveryType);
                jsonObject.put("user_id",userId);

                JSONObject address = new JSONObject();

                AddressModel addressModel = cartModel.getAddressModel();
                if(addressModel!=null) {
                    address.put("addressLine1", addressModel.address1);
                    address.put("city",addressModel.city);
                    address.put("state",addressModel.state);
                    address.put("zip",addressModel.pin);
                    address.put("deliveryType",addressModel.deliveryType);
                }

                jsonObject.put("address",address);

                return jsonObject;
            }catch (Exception e){

            }
            return null;
        }
    }

    public HashMap<String, String> getImages() {
        if (cartModel==null)
            return null;
        else
            return cartModel.images;
    }

}
