package com.hz.sellcloud.enums;

public enum ChartType {
    LineChart(0),
    BarChart(2),
    PieChart(1);

    private int code;

    ChartType(int code){
        this.code = code;
    }

    public static ChartType fromCode(int code) {
        for (ChartType ChartType : ChartType.values()) {
            if (ChartType.code == code) {
                return ChartType;
            }
        }
        throw new IllegalArgumentException("Invalid enum code: " + code);
    }
}
