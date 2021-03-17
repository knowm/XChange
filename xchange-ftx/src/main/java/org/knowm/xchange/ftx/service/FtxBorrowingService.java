package org.knowm.xchange.ftx.service;

import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.ftx.dto.account.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FtxBorrowingService extends FtxBaseService  {

  public FtxBorrowingService(FtxExchange exchange) {
    super(exchange);
  }

  public List<FtxBorrowingsDto> histories(String subaccount) {
    try {
      return ftx.getBorrowHistory(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult();
    } catch (IOException e) {
      throw new FtxLendingService.FtxLendingServiceException("Can't get lending infos subAccount: " + subaccount, e);
    }
  }

  public List<FtxBorrowingsDto> histories(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return histories(subaccount).stream()
        .filter(lendingHistory -> coins.contains(lendingHistory.getCoin()))
        .collect(Collectors.toList());
  }

  public List<FtxBorrowingsDto> histories(String subaccount, String... coins) {
    Objects.requireNonNull(coins);
    return histories(subaccount, Arrays.asList(coins));
  }

  public FtxBorrowingsDto history(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    return histories(subaccount).stream()
        .filter(lendingHistory -> lendingHistory.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxBorrowingInfoDto> infos(String subaccount) {
    try {
      return ftx.getBorrowingInfos(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult();
    } catch (IOException e) {
      throw new FtxLendingService.FtxLendingServiceException("Can't get lending infos subAccount: " + subaccount, e);
    }
  }

  public List<FtxBorrowingInfoDto> infos(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return infos(subaccount).stream()
        .filter(lendingInfo -> coins.contains(lendingInfo.getCoin()))
        .collect(Collectors.toList());
  }

  public List<FtxBorrowingInfoDto> infos(String subaccount, String... coins) {
    Objects.requireNonNull(coins);
    return infos(subaccount, Arrays.asList(coins));
  }

  public FtxBorrowingInfoDto info(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    return infos(subaccount).stream()
        .filter(lendingInfo -> lendingInfo.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxBorrowingRatesDto> rates(String subaccount) {
    try {
      return ftx.getBorrowRates(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult();
    } catch (IOException e) {
      throw new FtxLendingService.FtxLendingServiceException("Can't get lending rates", e);
    }
  }

  public List<FtxBorrowingRatesDto> rates(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return rates(subaccount).stream()
        .filter(lendingRates -> coins.contains(lendingRates.getCoin()))
        .collect(Collectors.toList());
  }

  public List<FtxBorrowingRatesDto> rates(String subaccount, String... coins) {
    Objects.requireNonNull(coins);
    return rates(subaccount, Arrays.asList(coins));
  }

  public FtxBorrowingRatesDto rate(String subaccount, String coin) {
    try {
      return ftx.getBorrowRates(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult().stream()
          .filter(lendingRates -> lendingRates.getCoin().equalsIgnoreCase(coin))
          .findFirst()
          .orElse(null);
    } catch (IOException e) {
      throw new FtxLendingService.FtxLendingServiceException("Can't get lending rate coin: " + coin, e);
    }
  }
}
