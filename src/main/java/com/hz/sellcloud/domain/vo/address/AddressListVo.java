package com.hz.sellcloud.domain.vo.address;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressListVo {
    @Data
    public static class address{
        String code;
        String name;
        List<address> children;
    }

    List<address> address;
}
