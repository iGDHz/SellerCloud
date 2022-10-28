package com.hz.sellcloud.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@ApiModel(value = "Address对象", description = "")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private String addressAreaid;

    private String addressName;

    private String addressRegionid;

    public String getAddressAreaid() {
        return addressAreaid;
    }

    public void setAddressAreaid(String addressAreaid) {
        this.addressAreaid = addressAreaid;
    }
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
    public String getAddressRegionid() {
        return addressRegionid;
    }

    public void setAddressRegionid(String addressRegionid) {
        this.addressRegionid = addressRegionid;
    }

    @Override
    public String toString() {
        return "Address{" +
            "addressAreaid=" + addressAreaid +
            ", addressName=" + addressName +
            ", addressRegionid=" + addressRegionid +
        "}";
    }
}
