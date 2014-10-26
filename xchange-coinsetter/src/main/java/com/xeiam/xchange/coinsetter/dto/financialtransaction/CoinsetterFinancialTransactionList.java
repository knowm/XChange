package com.xeiam.xchange.coinsetter.dto.financialtransaction;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinsetterFinancialTransactionList {

  private final CoinsetterFinancialTransaction[] financialTransactionList;

  public CoinsetterFinancialTransactionList(@JsonProperty("financialTransactionList") CoinsetterFinancialTransaction[] financialTransactionList) {

    this.financialTransactionList = financialTransactionList;
  }

  public CoinsetterFinancialTransaction[] getFinancialTransactionList() {

    return financialTransactionList;
  }

  @Override
  public String toString() {

    return "CoinsetterFinancialTransactionList [financialTransactionList=" + Arrays.toString(financialTransactionList) + "]";
  }

}
