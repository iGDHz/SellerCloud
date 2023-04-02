package com.hz.sellcloud;

import com.hz.sellcloud.entity.Orders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class Generator {
    static class OrdersGenerator{

        Random random = new Random(47);

        Orders[] orders;
        public OrdersGenerator(int size){
            orders = new Orders[size];
            for (int i = 0; i < size; i++) {
                orders[i] = new Orders(random.nextInt(5),random.nextInt(5),
                        new BigDecimal(random.nextInt(1000)),new BigDecimal(random.nextInt(100000)),new Date());
            }
        }

        public Orders getOrders() {
            return orders[random.nextInt(orders.length)];
        }
    }
}
