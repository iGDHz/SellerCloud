package com.hz.sellcloud.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户权限")
    private Integer userLevel;

    public Users() {
    }

    public Users(String userName, String userPassword, Integer userLevel) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userLevel = userLevel;
    }

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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Users){
            Users user = (Users) obj;
            if(this.getUserName() != user.getUserName()
            || this.userLevel != user.getUserLevel()) return false;
            return true;
        }
        return false;
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
