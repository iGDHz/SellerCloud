package com.hz.sellcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.lettuce.core.StrAlgoArgs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author hz
 * @since 2022-10-31
 */
@ApiModel(value = "Supermarkets对象", description = "")
public class Supermarkets implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商店id")
    @TableId(value = "supermark_id", type = IdType.AUTO)
    private Integer supermarkId;

    @ApiModelProperty("商店名称")
    private String supermarkName;

    @ApiModelProperty("商店位置id")
    private String supermarkRegionid;

    @ApiModelProperty("超市详细地址")
    private String supermarkDetail;

    @ApiModelProperty("超市所属公司id")
    private Integer supermarkBelonged;

    @ApiModelProperty("超市证件信息文件名")
    private String supermarkLicense;

    @ApiModelProperty("超市状态")
    private Byte supermarkState;

    @ApiModelProperty("创建人")
    private Integer createBy;

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        createBy = createBy;
    }

    public String getSupermarkDetail() {
        return supermarkDetail;
    }

    public void setSupermarkDetail(String supermarkDetail) {
        this.supermarkDetail = supermarkDetail;
    }

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


    public Integer getSupermarkBelonged() {
        return supermarkBelonged;
    }

    public void setSupermarkBelonged(Integer supermarkBelonged) {
        this.supermarkBelonged = supermarkBelonged;
    }

    public String getSupermarkLicense() {
        return supermarkLicense;
    }

    public void setSupermarkLicense(String supermarkLicense) {
        this.supermarkLicense = supermarkLicense;
    }

    public Byte getSupermarkState() {
        return supermarkState;
    }

    public void setSupermarkState(Byte supermarkState) {
        this.supermarkState = supermarkState;
    }

    @Override
    public String toString() {
        return "Supermarkets{" +
                "supermarkId=" + supermarkId +
                ", supermarkName='" + supermarkName + '\'' +
                ", supermarkRegionid='" + supermarkRegionid + '\'' +
                ", supermarkDeatil='" + supermarkDetail + '\'' +
                ", supermarkBelonged=" + supermarkBelonged +
                ", supermarkLicense='" + supermarkLicense + '\'' +
                ", supermarkState=" + supermarkState +
                '}';
    }
}
