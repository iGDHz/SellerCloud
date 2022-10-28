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
@ApiModel(value = "Companies对象", description = "")
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("公司id")
    private Integer companyId;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("公司信息")
    private String companyMessage;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyMessage() {
        return companyMessage;
    }

    public void setCompanyMessage(String companyMessage) {
        this.companyMessage = companyMessage;
    }

    @Override
    public String toString() {
        return "Companies{" +
            "companyId=" + companyId +
            ", companyName=" + companyName +
            ", companyMessage=" + companyMessage +
        "}";
    }
}
