package com.xeiam.xchange.cryptonit.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Created by Yar.kh on 02/10/14.
 */
public class CryptonitFund {

    /*
{
  "4": {
     "balance": 4.03,
     "currency_code": "EUR",
     "uid": "X"
  },
  "1": {
    "balance": 0.98,
    "currency_code": "USD",
    "uid": "X"
  }
}
     */

    private BigDecimal balance;
    private String currencyCode;
    private String uid;

    public CryptonitFund(@JsonProperty("balance") BigDecimal balance, @JsonProperty("currency_code") String currencyCode, @JsonProperty("uid") String uid) {
        this.balance = balance;
        this.currencyCode = currencyCode;
        this.uid = uid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "CryptonitFund{" +
                "balance=" + balance +
                ", currencyCode='" + currencyCode + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
