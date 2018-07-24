package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil on 2/4/16.
 */
public class ProductManufacturerModel {

    @SerializedName("manufacturer_id")
    @Expose
    private Long manufacturerId;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return The manufacturerId
     */
    public Long getManufacturerId() {
        return manufacturerId;
    }

    /**
     * @param manufacturerId The manufacturer_id
     */
    public void setManufacturerId(Long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }
}
