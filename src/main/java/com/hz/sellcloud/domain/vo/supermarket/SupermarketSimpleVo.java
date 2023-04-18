package com.hz.sellcloud.domain.vo.supermarket;

import lombok.Data;

@Data
public class SupermarketSimpleVo {
    long sid;
    String sname;

    public SupermarketSimpleVo(long sid, String sname) {
        this.sid = sid;
        this.sname = sname;
    }

    public SupermarketSimpleVo(){

    }
}
