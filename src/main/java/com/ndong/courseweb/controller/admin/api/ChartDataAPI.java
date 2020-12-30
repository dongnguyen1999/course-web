package com.ndong.courseweb.controller.admin.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.dto.ChartDTO;
import com.ndong.courseweb.service.impl.PurchaseDetailService;
import com.ndong.courseweb.utils.SessionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartDataAPI {
  @Autowired
  private PurchaseDetailService purchaseDetailService;

  @Autowired
  private SessionUtils sessionUtils;

  @RequestMapping(path = "/admin/api/chart/revenue", method = RequestMethod.GET)
  public ResponseEntity<ChartDTO> getRevenueChartData() {
    ChartDTO chart = new ChartDTO();
    List<String> xlabels = new ArrayList<>();
    List<Double> data = new ArrayList<>();
    Date date = new Date(System.currentTimeMillis());
    LocalDate localDate = date.toLocalDate();
    for (int i = 0; i < SystemConstant.REVENUE_CHART_NB_MONTH; i++) {
      Date start = Date.valueOf(localDate.withDayOfMonth(1));
      Date end = Date.valueOf(localDate.withDayOfMonth(localDate.lengthOfMonth()));
      data.add(purchaseDetailService.computeRevenue(purchaseDetailService.countPurchase(start, end)));
      xlabels.add(localDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
      localDate = localDate.minusMonths(1);
    }
    Collections.reverse(xlabels);
    Collections.reverse(data);
    chart.setXlabels(xlabels);
    chart.setYlabel("Doanh thu");
    chart.setData(data);
    return new ResponseEntity<>(chart, HttpStatus.OK);
  }

  @RequestMapping(path = "/admin/api/chart/this-revenue", method = RequestMethod.GET)
  public ResponseEntity<Double> getThisMonthRevenue() {
    Date date = new Date(System.currentTimeMillis());
    LocalDate localDate = date.toLocalDate();
    Date start = Date.valueOf(localDate.withDayOfMonth(1));
    Date end = Date.valueOf(localDate.withDayOfMonth(localDate.lengthOfMonth()));
    return new ResponseEntity<>(
      purchaseDetailService.computeRevenue(
        purchaseDetailService.countPurchase(start, end)),
        HttpStatus.OK);
  }

  @RequestMapping(path = "/admin/api/chart/average-revenue", method = RequestMethod.GET)
  public ResponseEntity<Long> getAverageRevenue() {
    Date date = new Date(System.currentTimeMillis());
    LocalDate localDate = date.toLocalDate();
    Double average = 0D;
    for (int i = 0; i < SystemConstant.REVENUE_CHART_NB_MONTH; i++) {
      Date start = Date.valueOf(localDate.withDayOfMonth(1));
      Date end = Date.valueOf(localDate.withDayOfMonth(localDate.lengthOfMonth()));
      average += purchaseDetailService.computeRevenue(purchaseDetailService.countPurchase(start, end));
      localDate = localDate.minusMonths(1);
    }
    return new ResponseEntity<>(Math.round(average/SystemConstant.REVENUE_CHART_NB_MONTH), HttpStatus.OK);
  }

  @RequestMapping(path = "/admin/api/chart/access", method = RequestMethod.GET)
  public ResponseEntity<ChartDTO> getAccessChartData() {
    ChartDTO chart = new ChartDTO();
    List<String> xlabels = new ArrayList<>();
    List<Double> data = new ArrayList<>();
    Date date = new Date(System.currentTimeMillis());
    LocalDate localDate = date.toLocalDate();
    for (int i = 0; i < SystemConstant.ACCESS_CHART_NB_MONTH; i++) {
      Date start = Date.valueOf(localDate.withDayOfMonth(1));
      Date end = Date.valueOf(localDate.withDayOfMonth(localDate.lengthOfMonth()));
      data.add((double) sessionUtils.countNumberSessionCreated(start, end));
      xlabels.add(localDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
      localDate = localDate.minusMonths(1);
    }
    Collections.reverse(xlabels);
    Collections.reverse(data);
    chart.setXlabels(xlabels);
    chart.setYlabel("Số lượt truy cập");
    chart.setData(data);
    return new ResponseEntity<>(chart, HttpStatus.OK);
  }

  @RequestMapping(path = "/admin/api/chart/this-access", method = RequestMethod.GET)
  public ResponseEntity<Long> getThisMonthAccess() {
    Date date = new Date(System.currentTimeMillis());
    LocalDate localDate = date.toLocalDate();
    Date start = Date.valueOf(localDate.withDayOfMonth(1));
    Date end = Date.valueOf(localDate.withDayOfMonth(localDate.lengthOfMonth()));
    return new ResponseEntity<>(
      sessionUtils.countNumberSessionCreated(start, end),
        HttpStatus.OK);
  }

  @RequestMapping(path = "/admin/api/chart/average-access", method = RequestMethod.GET)
  public ResponseEntity<Long> getAverageAccess() {
    Date date = new Date(System.currentTimeMillis());
    LocalDate localDate = date.toLocalDate();
    Long average = 0L;
    for (int i = 0; i < SystemConstant.ACCESS_CHART_NB_MONTH; i++) {
      Date start = Date.valueOf(localDate.withDayOfMonth(1));
      Date end = Date.valueOf(localDate.withDayOfMonth(localDate.lengthOfMonth()));
      average += sessionUtils.countNumberSessionCreated(start, end);
      localDate = localDate.minusMonths(1); 
    }
    return new ResponseEntity<>(average/SystemConstant.ACCESS_CHART_NB_MONTH, HttpStatus.OK);
  }
}
