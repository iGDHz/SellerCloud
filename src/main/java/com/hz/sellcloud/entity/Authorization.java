package com.hz.sellcloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value = "Authorization对象", description = "")
public class Authorization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("授权id")
    @TableId(value = "author_id", type = IdType.AUTO)
    private Integer authorId;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("超市id")
    private Integer supermarkId;

    public Authorization() {
    }

    public Authorization(Integer userId, Integer supermarkId) {
        this.userId = userId;
        this.supermarkId = supermarkId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getSupermarkId() {
        return supermarkId;
    }

    public void setSupermarkId(Integer supermarkId) {
        this.supermarkId = supermarkId;
    }

    @Override
    public String toString() {
        return "Authorization{" +
            "authorId=" + authorId +
            ", userId=" + userId +
            ", supermarkId=" + supermarkId +
        "}";
    }
}
