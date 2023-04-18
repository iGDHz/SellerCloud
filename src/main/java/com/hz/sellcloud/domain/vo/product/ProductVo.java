package com.hz.sellcloud.domain.vo.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hz.sellcloud.domain.vo.supermarket.SupermarketSimpleVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVo {


    long id; //商品id
    long category_id; //分类id
    String name; //商品名称
    SupermarketSimpleVo supermarket;
    BigDecimal price; //商品价格
    String picture; //商品图片url

    public ProductVo(long id, long category_id, String name,long sid ,String sname, BigDecimal price, String picture) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.supermarket = new SupermarketSimpleVo(sid,sname);
        this.price = price;
        this.picture = picture;
    }
}
