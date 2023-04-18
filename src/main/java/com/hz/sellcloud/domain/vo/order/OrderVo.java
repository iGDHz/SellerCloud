package com.hz.sellcloud.domain.vo.order;

import com.hz.sellcloud.domain.vo.supermarket.SupermarketSimpleVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVo {
    @Data
    public static class productVo{
        int product_id;
        int category_id;
        String name;
        SupermarketSimpleVo supermarket;
        BigDecimal signle;
        BigDecimal price;
        String picture;
        int quantity;


        public productVo(Integer product_id,
                         Integer category_id,
                         String name,
                         Integer sid,
                         String sname,
                         BigDecimal signle,
                         BigDecimal price,
                         String picture,
                         Integer quantity) {
            this.product_id = product_id;
            this.category_id = category_id;
            this.name = name;
            this.supermarket = new SupermarketSimpleVo(sid,sname);
            this.signle = signle;
            this.price = price;
            this.picture = picture;
            this.quantity = quantity;
        }

        public productVo(int product_id, int category_id, String name, SupermarketSimpleVo supermarket, BigDecimal signle, BigDecimal price, String picture, int quantity) {
            this.product_id = product_id;
            this.category_id = category_id;
            this.name = name;
            this.supermarket = supermarket;
            this.signle = signle;
            this.price = price;
            this.picture = picture;
            this.quantity = quantity;
            System.out.println("src create");
        }

        public productVo(){};
    }

    String order_id;
    List<productVo> products;
    int sid;
    String sname;
    BigDecimal price;
    String date;


}
