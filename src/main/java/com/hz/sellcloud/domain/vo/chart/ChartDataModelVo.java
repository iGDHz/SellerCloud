package com.hz.sellcloud.domain.vo.chart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ChartDataModelVo {

    ChartDataModelVo generate(List<ChartDataVo> list,int scope);
    default List<String> generateDateList(int scope) {
        List<String> dateList = new LinkedList<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        switch(scope) {
            case 0: // 近7天
                for (int i = 0; i < 7; i++) {
                    dateList.add(0,currentDate.minusDays(i).format(formatter));
                }
                break;
            case 1: // 近1个月
                for (int i = 0; i < 4; i++) {
                    dateList.add(0,currentDate.minusWeeks(i).format(formatter));
                }
                break;
            case 2: // 近3个月
                for (int i = 0; i < 3; i++) {
                    dateList.add(0,currentDate.minusMonths(i).format(DateTimeFormatter.ofPattern("yyyy/MM")));
                }
                break;
            case 3:{ //近6个月
                for (int i = 0; i < 6; i++) {
                    dateList.add(0,currentDate.minusMonths(i).format(DateTimeFormatter.ofPattern("yyyy/MM")));
                }
                break;
            }
            case 4: // 近一年
                for (int i = 0; i < 12; i++) {
                    dateList.add(0,currentDate.minusMonths(i).format(DateTimeFormatter.ofPattern("yyyy/MM")));
                }
                break;
            case 5: // 近三年
                for (int i = 0; i < 3; i++) {
                    dateList.add(0,currentDate.minusYears(i).format(DateTimeFormatter.ofPattern("yyyy")));
                }
                break;
            default:
                System.out.println("Invalid range parameter.");
        }

        return dateList;
    }


}
