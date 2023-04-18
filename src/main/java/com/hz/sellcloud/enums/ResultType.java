package com.hz.sellcloud.enums;

public enum ResultType {
    SalesData(0),
    SumData(1);

    private int code;

    ResultType(int code){
        this.code = code;
    }

    public static ResultType fromCode(int code) {
        for (ResultType ResultType : ResultType.values()) {
            if (ResultType.code == code) {
                return ResultType;
            }
        }
        throw new IllegalArgumentException("Invalid enum code: " + code);
    }
}
