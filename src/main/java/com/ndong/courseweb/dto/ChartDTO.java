package com.ndong.courseweb.dto;

import java.util.List;

public class ChartDTO extends AbstractDTO {
  private List<String> xlabels;
  private String ylabel;
  private List<Double> data;

  public List<String> getXlabels() {
    return xlabels;
  }
  public void setXlabels(List<String> xlabels) {
    this.xlabels = xlabels;
  }

  public String getYlabel() {
    return ylabel;
  }

  public void setYlabel(String ylabel) {
    this.ylabel = ylabel;
  }

  public List<Double> getData() {
    return data;
  }

  public void setData(List<Double> data) {
    this.data = data;
  }
  
}
