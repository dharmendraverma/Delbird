package in.delbird.chemist.models;

import java.io.Serializable;

public class DealModel implements Serializable {

    private Long shop_id;
    private String shop_name;
    private String shop_mobile_no;
    private String shop_address;
    private String pic_url;
    private Double lat;
    private Double lon;
    private Double distance;
    private String shop_type;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public Long getShop_id() {
        return shop_id;
    }

    public void setShop_id(Long shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_mobile_no() {
        return shop_mobile_no;
    }

    public void setShop_mobile_no(String shop_mobile_no) {
        this.shop_mobile_no = shop_mobile_no;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_type() {
        return shop_type;
    }

    public void setShop_type(String shop_type) {
        this.shop_type = shop_type;
    }



}

