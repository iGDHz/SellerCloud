package com.hz.sellcloud.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value = "User对象", description = "")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("用户邮箱")
    private String userMail;

    @ApiModelProperty("用户权限")
    private String userRole;

    @ApiModelProperty("用户个性签名")
    private String userIntroduction;

    @ApiModelProperty("用户头像地址")
    private String userAvatar;

    @ApiModelProperty("用户状态 (0: 等待验证, 1: 已注册)")
    private Byte userState;

    @TableField(exist = false)
    private Date createTime;

    @TableField(exist = false)
    private Date updateTime;

    @TableField(exist = false)
    private String createBy;
    public Users() {
    }

    public Users(Integer userId, String userName, String userPassword, String userRole, String userIntroduction, String userAvatar) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userIntroduction = userIntroduction;
        this.userAvatar = userAvatar;
    }

    public Users(String userName, String userPassword, String userRole, String userIntroduction, String userAvatar) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userIntroduction = userIntroduction;
        this.userAvatar = userAvatar;
    }


    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserIntroduction() {
        return userIntroduction;
    }

    public void setUserIntroduction(String userIntroduction) {
        this.userIntroduction = userIntroduction;
    }

    public Byte getuserState() {
        return userState;
    }

    public void setuserState(Byte userState) {
        this.userState = userState;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }


    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userIntroduction='" + userIntroduction + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }
}
