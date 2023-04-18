package com.hz.sellcloud.domain.request.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hz.sellcloud.domain.vo.order.OrderVo;
import lombok.Data;
import scala.Tuple2;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderUpdateRequest {

    @Data
    public class orderRequest{
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT")
        Date date;
        String order_id;
        BigDecimal price;
        List<OrderVo.productVo> products;
        String sid;
        String sname;
    }

    String token;
    orderRequest order;
}
