package com.hz.sellcloud.controller.maincontroller;


import com.hz.sellcloud.controller.BaseController;
import com.hz.sellcloud.domain.request.chart.ChartDataRequest;
import com.hz.sellcloud.domain.response.CommonResponse;
import com.hz.sellcloud.domain.vo.chart.*;
import com.hz.sellcloud.entity.Supermarkets;
import com.hz.sellcloud.entity.Users;
import com.hz.sellcloud.enums.ChartType;
import com.hz.sellcloud.enums.ResultType;
import com.hz.sellcloud.service.ChartDataService;
import com.hz.sellcloud.service.IProductSalesService;
import com.hz.sellcloud.service.IProductSumService;
import com.hz.sellcloud.service.ISupermarketsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.hadoop.yarn.webapp.hamlet2.HamletSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Api(tags = "图表请求")
@RequestMapping("/chart")
public class ChartTableController extends BaseController {

    @Autowired
    IProductSalesService productSalesService;

    @Autowired
    IProductSumService productSumService;

    @Autowired
    ChartDataService chartDataService;

    @Autowired
    ISupermarketsService supermarketsService;

    @RequestMapping(value = "/getChart",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取图表信息")
    public CommonResponse getChartData(@RequestBody ChartDataRequest request){
        String token = request.getToken();
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        List<Integer> supermarkets = request.getSupermarkets();
        if (supermarkets == null || supermarkets.size() == 0){
            List<Supermarkets> supermarketsList = supermarketsService.searchByUserId(user.getUserId());
            supermarkets = supermarketsList.parallelStream().map(Supermarkets::getSupermarkId).collect(Collectors.toList());
        }
        List<ChartDataVo> chartData = chartDataService.getChartData(request.getCategorys(),
                request.getChartType(),
                request.getProducts(),
                request.getResultType(),
                request.getScope(),
                supermarkets);
        ChartDataModelVo data = null;
        switch (ChartType.fromCode(request.getChartType())){
            case LineChart:{
                data = new LineChartDataVo();
                break;
            }
            case BarChart:{
                data = new BarChartDataVo();
                break;
            }
            case PieChart: {
                data = new PieChartDataVo();
                break;
            }
            default: throw new IllegalArgumentException("ChartType Error");
        }
        data.generate(chartData, request.getScope());
        return new CommonResponse(data).sucess();
    }

    @RequestMapping(value = "/chartsum",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取销量总数")
    public CommonResponse sumChartData(@RequestBody ChartDataRequest request){
        String token = request.getToken();
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        List<Integer> supermarkets = request.getSupermarkets();
        if (supermarkets == null || supermarkets.size() == 0){
            List<Supermarkets> supermarketsList = supermarketsService.searchByUserId(user.getUserId());
            supermarkets = supermarketsList.parallelStream().map(Supermarkets::getSupermarkId).collect(Collectors.toList());
        }
        BigDecimal chartSum = chartDataService.getChartSum(request.getCategorys(),
                request.getChartType(),
                request.getProducts(),
                request.getResultType(),
                request.getScope(),
                supermarkets);
        return new CommonResponse(chartSum).sucess();
    }

    @RequestMapping(value = "/chartsales",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取销售额总数")
    public CommonResponse salesChartData(@RequestBody ChartDataRequest request){
        String token = request.getToken();
        Users user = TokenToUsers(token);
        if(user == null){
            return new CommonResponse().error(403,"token失效");
        }
        List<Integer> supermarkets = request.getSupermarkets();
        if (supermarkets == null || supermarkets.size() == 0){
            List<Supermarkets> supermarketsList = supermarketsService.searchByUserId(user.getUserId());
            supermarkets = supermarketsList.parallelStream().map(Supermarkets::getSupermarkId).collect(Collectors.toList());
        }
        BigDecimal chartSum = chartDataService.getChartSales(request.getCategorys(),
                request.getChartType(),
                request.getProducts(),
                request.getResultType(),
                request.getScope(),
                supermarkets);
        return new CommonResponse(chartSum).sucess();
    }
}
