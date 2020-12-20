package com.ndong.courseweb.service;

import java.sql.Date;
import java.util.List;

import com.ndong.courseweb.entity.PurchaseDetailEntity;

public interface IPurchaseDetailService {
  List<PurchaseDetailEntity> countPurchase(Date date);
  List<PurchaseDetailEntity> countPurchase(Date from, Date to);
  List<PurchaseDetailEntity> countPurchaseBefore(Date to);
  List<PurchaseDetailEntity> countPurcaseAfter(Date from);
  double computeRevenue(List<PurchaseDetailEntity> details);
}
