package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.ftx.dto.account.FtxLendDataDto;
import org.knowm.xchange.ftx.dto.account.FtxLendingHistoryDto;
import org.knowm.xchange.ftx.dto.account.FtxLendingInfoDto;
import org.knowm.xchange.ftx.dto.account.FtxLendingRatesDto;
import org.knowm.xchange.ftx.dto.account.FtxSubmitLendingOfferParams;

public class FtxLendingServiceRaw extends FtxBaseService {

  public FtxLendingServiceRaw(FtxExchange exchange) {
    super(exchange);
  }

  public FtxLendDataDto stopLending(String subaccount, String coin) {
    return lend(subaccount, coin, 0, 0);
  }

  public List<FtxLendDataDto> stopLending(String subaccount, List<String> coins) {
    return coins.stream().map(coin -> stopLending(subaccount, coin)).collect(Collectors.toList());
  }

  public FtxLendDataDto lend(String subaccount, String coin, double size, double rate) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");
    if (rate < 0)
      throw new FtxLendingServiceException(
          "Rate must to be >= 0, subaccount: " + subaccount + ", coin: " + coin);
    if (size < 0)
      throw new FtxLendingServiceException(
          "Size must to be >= 0, subaccount: "
              + subaccount
              + ", coin: "
              + coin
              + ", rate: "
              + rate);

    try {
      FtxLendingInfoDto info = info(subaccount, coin);
      double sizeToLend = FtxAdapters.lendingRounding(BigDecimal.valueOf(size)).doubleValue();

      if (Double.compare(sizeToLend, info.getLendable()) == 1) {
        throw new FtxLendingServiceException(
            "Can't lend sizeToLend > to lendable, subaccount: "
                + subaccount
                + ", coin: "
                + coin
                + ", size: "
                + size
                + ", sizeToLend: "
                + sizeToLend
                + ", rate: "
                + rate);
      }
      ftx.submitLendingOffer(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          new FtxSubmitLendingOfferParams(
              coin, FtxAdapters.lendingRounding(new BigDecimal(sizeToLend)).doubleValue(), rate));
      return new FtxLendDataDto(coin, info.getLocked(), info.getOffered(), sizeToLend, rate);
    } catch (IOException e) {
      throw new FtxLendingServiceException(
          "Can't lend subaccount: "
              + subaccount
              + ", coin: "
              + coin
              + ", size: "
              + size
              + ", rate: "
              + rate,
          e);
    }
  }

  public List<FtxLendingHistoryDto> histories(String subaccount) {
    try {
      return ftx.getlendingHistories(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't get lending infos subAccount: " + subaccount, e);
    }
  }

  public List<FtxLendingHistoryDto> histories(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return histories(subaccount).stream()
        .filter(lendingHistory -> coins.contains(lendingHistory.getCoin()))
        .collect(Collectors.toList());
  }

  public FtxLendingHistoryDto history(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");
    return histories(subaccount).stream()
        .filter(lendingHistory -> lendingHistory.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxLendingInfoDto> infos(String subaccount) {
    try {
      return ftx.getLendingInfos(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't get lending infos subAccount: " + subaccount, e);
    }
  }

  public List<FtxLendingInfoDto> infos(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return infos(subaccount).stream()
        .filter(lendingInfo -> coins.contains(lendingInfo.getCoin()))
        .collect(Collectors.toList());
  }

  public FtxLendingInfoDto info(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");
    return infos(subaccount).stream()
        .filter(lendingInfo -> lendingInfo.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxLendingRatesDto> rates() {
    try {
      return ftx.getLendingRates(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator)
          .getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't get lending rates", e);
    }
  }

  public List<FtxLendingRatesDto> rates(List<String> coins) {
    Objects.requireNonNull(coins);
    return rates().stream()
        .filter(lendingRates -> coins.contains(lendingRates.getCoin()))
        .collect(Collectors.toList());
  }

  public FtxLendingRatesDto rate(String coin) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");
    try {
      return ftx
          .getLendingRates(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator)
          .getResult()
          .stream()
          .filter(lendingRates -> lendingRates.getCoin().equalsIgnoreCase(coin))
          .findFirst()
          .orElse(null);
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't get lending rate coin: " + coin, e);
    }
  }

  public static class FtxLendingServiceException extends RuntimeException {
    public FtxLendingServiceException(String message, Throwable cause) {
      super(message, cause);
    }

    public FtxLendingServiceException(String message) {
      super(message);
    }
  }
}
