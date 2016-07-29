package org.knowm.xchange.coinsetter.dto.financialtransaction;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A list of financial transactions for a specified account ID
 */
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
