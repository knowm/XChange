package org.knowm.xchange.coinsph;

import org.knowm.xchange.coinsph.dto.CoinsphResponse;
import org.knowm.xchange.coinsph.dto.account.CoinsphFiatResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

public class CoinsphErrorAdapter {
  public static final int FUNDS_EXCEEDED = -10112;
  public static final int TRADE_LIMIT_EXCEEDED = -1131;
  public static final int RATE_LIMIT_EXCEEDED = -1003;

  public static final int FIAT_FUNDS_EXCEEDED = 88010013;
  public static final int FIAT_RATE_LIMIT_EXCEEDED = 88010000;
  public static final int FIAT_ACCOUNT_DISABLED = 88010002;
  public static final int FIAT_ACCOUNT_NOT_VERIFIED = 88010017;
  public static final int FIAT_ORDER_AMOUNT_TOO_LOW = 88010003;
  public static final int FIAT_ORDER_AMOUNT_TOO_HIGH = 88010004;
  public static final int FIAT_CASH_OUT_LIMIT_DAILY_EXCEEDED = 88010005;
  public static final int FIAT_CASH_OUT_LIMIT_MONTHLY_EXCEEDED = 88010006;
  public static final int FIAT_CASH_OUT_LIMIT_ANNUAL_EXCEEDED = 88010007;
  public static final int FIAT_CASH_OUT_METHOD_UNAVAILABLE = 88010008;
  public static final int FIAT_CASH_OUT_IN_PROGRESS = 88010012;

  public static ExchangeException adaptError(CoinsphResponse response) {
    switch (response.getCode()) {
      case FUNDS_EXCEEDED:
      case TRADE_LIMIT_EXCEEDED:
        return new FundsExceededException(response.getMessage());
      case RATE_LIMIT_EXCEEDED:
        return new RateLimitExceededException(response.getMessage());
    }
    return new ExchangeException(
        String.format("Coinsph code: %d error: %s", response.getCode(), response.getMessage()));
  }

  public static ExchangeException adaptError(CoinsphFiatResponse<?> response) {
    switch (response.getStatus()) {
      case FIAT_FUNDS_EXCEEDED:
        return new FundsExceededException(response.getError());
      case FIAT_RATE_LIMIT_EXCEEDED:
        return new RateLimitExceededException(response.getError());
      case FIAT_ACCOUNT_DISABLED:
        return new ExchangeException("Fiat account is disabled: " + response.getError());
      case FIAT_ACCOUNT_NOT_VERIFIED:
        return new ExchangeException("Fiat account not verified: " + response.getError());
      case FIAT_ORDER_AMOUNT_TOO_LOW:
        return new ExchangeException("Order amount too low: " + response.getError());
      case FIAT_ORDER_AMOUNT_TOO_HIGH:
        return new ExchangeException("Order amount too high: " + response.getError());
      case FIAT_CASH_OUT_LIMIT_DAILY_EXCEEDED:
        return new FundsExceededException("Cash out limit daily exceeded: " + response.getError());
      case FIAT_CASH_OUT_LIMIT_MONTHLY_EXCEEDED:
        return new FundsExceededException(
            "Cash out limit monthly exceeded: " + response.getError());
      case FIAT_CASH_OUT_LIMIT_ANNUAL_EXCEEDED:
        return new FundsExceededException("Cash out limit annual exceeded: " + response.getError());
      case FIAT_CASH_OUT_METHOD_UNAVAILABLE:
        return new ExchangeException("Cash out method unavailable: " + response.getError());
      case FIAT_CASH_OUT_IN_PROGRESS:
        return new ExchangeException("Cash out in progress: " + response.getError());
    }
    return new ExchangeException(
        String.format("Coinsph code: %d error: %s", response.getStatus(), response.getError()));
  }
}
