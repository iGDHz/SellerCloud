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
@ApiModel(value = "Users对象", description = "")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户权限")
    private Integer userLevel;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    @Override
    public String toString() {
        return "Users{" +
            "userId=" + userId +
            ", userName=" + userName +
            ", userPassword=" + userPassword +
            ", userLevel=" + userLevel +
        "}";
    }
}
