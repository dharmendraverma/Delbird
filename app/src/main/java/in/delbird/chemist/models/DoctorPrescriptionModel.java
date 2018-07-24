package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahil on 10/4/16.
 */
public class DoctorPrescriptionModel implements Serializable {

    @SerializedName("doctor_id")
    @Expose
    private Long doctorId;
    @SerializedName("prescriptionMaster_id")
    @Expose
    private Long prescriptionMasterId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("doctor_name")
    @Expose
    private String doctorName;

    /**
     * @return The doctorId
     */
    public Long getDoctorId() {
        return doctorId;
    }

    /**
     * @param doctorId The doctor_id
     */
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * @return The prescriptionMasterId
     */
    public Long getPrescriptionMasterId() {
        return prescriptionMasterId;
    }

    /**
     * @param prescriptionMasterId The prescriptionMaster_id
     */
    public void setPrescriptionMasterId(Long prescriptionMasterId) {
        this.prescriptionMasterId = prescriptionMasterId;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @param doctorName The doctor_name
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
