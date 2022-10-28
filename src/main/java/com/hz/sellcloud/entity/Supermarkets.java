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
@ApiModel(value = "Supermarkets对象", description = "")
public class Supermarkets implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商店id")
    private Integer supermarkId;

    @ApiModelProperty("商店名称")
    private String supermarkName;

    @ApiModelProperty("商店位置id")
    private String supermarkRegionid;

    @ApiModelProperty("超市所属公司id")
    private Integer supermatkBelonged;

    public Integer getSupermarkId() {
        return supermarkId;
    }

    public void setSupermarkId(Integer supermarkId) {
        this.supermarkId = supermarkId;
    }
    public String getSupermarkName() {
        return supermarkName;
    }

    public void setSupermarkName(String supermarkName) {
        this.supermarkName = supermarkName;
    }
    public String getSupermarkRegionid() {
        return supermarkRegionid;
    }

    public void setSupermarkRegionid(String supermarkRegionid) {
        this.supermarkRegionid = supermarkRegionid;
    }
    public Integer getSupermatkBelonged() {
        return supermatkBelonged;
    }

    public void setSupermatkBelonged(Integer supermatkBelonged) {
        this.supermatkBelonged = supermatkBelonged;
    }

    @Override
    public String toString() {
        return "Supermarkets{" +
            "supermarkId=" + supermarkId +
            ", supermarkName=" + supermarkName +
            ", supermarkRegionid=" + supermarkRegionid +
            ", supermatkBelonged=" + supermatkBelonged +
        "}";
    }
}
