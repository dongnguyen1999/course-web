package com.ndong.courseweb.service.impl;

import java.sql.Date;
import java.util.List;

import com.ndong.courseweb.constant.SystemConstant;
import com.ndong.courseweb.entity.PurchaseDetailEntity;
import com.ndong.courseweb.repository.PurchaseDetailRepository;
import com.ndong.courseweb.service.IPurchaseDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDetailService implements IPurchaseDetailService {

  @Autowired
  private PurchaseDetailRepository purchaseDetailRepository;

  @Override
  public List<PurchaseDetailEntity> countPurchase(Date date) {
    return purchaseDetailRepository.findByPurchaseDate(date);
  }

  @Override
  public List<PurchaseDetailEntity> countPurchase(Date from, Date to) {
    return purchaseDetailRepository.findByPurchaseDateGreaterThanEqualAndPurchaseDateLessThan(from, to);
  }

  @Override
  public List<PurchaseDetailEntity> countPurchaseBefore(Date to) {
    return purchaseDetailRepository.findByPurchaseDateLessThan(to);
  }

  @Override
  public List<PurchaseDetailEntity> countPurcaseAfter(Date from) {
    return purchaseDetailRepository.findByPurchaseDateGreaterThanEqual(from);
  }

  @Override
  public double computeRevenue(List<PurchaseDetailEntity> details) {
    double revenue = 0;
    for (PurchaseDetailEntity detail: details) {
      revenue += detail.getPrice() * SystemConstant.COMMISSION_TRANSACTION_FACTOR;
    }
    return revenue;
  }
  
}
