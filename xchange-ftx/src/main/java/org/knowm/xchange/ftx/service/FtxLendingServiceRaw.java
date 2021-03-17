package org.knowm.xchange.ftx.service;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.ftx.dto.account.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FtxLendingServiceRaw extends FtxBaseService {

  public FtxLendingServiceRaw(FtxExchange exchange) {
    super(exchange);
  }

  public FtxLendDataDto stopLending(String subaccount, String coin) {
    return lend(subaccount, coin, 0, 0);
  }

  public List<FtxLendDataDto> stopLending(String subaccount, List<String> coins) {
    return coins.stream()
        .map(coin -> stopLending(subaccount, coin))
        .collect(Collectors.toList());
  }

  public List<FtxLendDataDto> stopLending(String subaccount, String... coins) {
    return stopLending(subaccount, Arrays.asList(coins));
  }

  public FtxLendDataDto lendAll(String subaccount, String coin, double rate) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");
    if (rate < 0)
      throw new FtxLendingServiceException("Rate must to be >= 0, subaccount: " + subaccount + ", coin: " + coin);

    try {
      FtxLendingInfoDto info = info(subaccount, coin);

      double sizeToLend = FtxAdapters.lendingRounding(new BigDecimal(info.getLendable())).doubleValue();
      if (Double.compare(sizeToLend, info.getOffered()) == 0)
        return new FtxLendDataDto(coin, info.getLocked(), sizeToLend, sizeToLend, rate);

      ftx.submitLendingOffer(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          new FtxSubmitLendingOfferParams(coin, sizeToLend, rate)
      );
      return new FtxLendDataDto(coin, info.getLocked(), info.getOffered(), sizeToLend, rate);
    } catch (IOException e) {
      throw new FtxLendingServiceException(e.getMessage());
    }
  }

  public List<FtxLendDataDto> lendAll(String subaccount, Map<String, Double> rateByCoins) {
    List<FtxLendDataDto> list = new ArrayList<>();
    rateByCoins.forEach((coin, rate) -> {


      List<FtxLendingInfoDto> infos = infos(subaccount);
      Optional<FtxLendingInfoDto> op = infos.stream()
          .filter(lendingInfo -> lendingInfo.getCoin().equalsIgnoreCase(coin))
          .findFirst();

      if (!op.isPresent()) throw new FtxLendingServiceException("Cant lend all, infos don't exist for coin: " + coin);

      double sizeToLend = FtxAdapters.lendingRounding(new BigDecimal(op.get().getLendable())).doubleValue();
      if (Double.compare(sizeToLend, op.get().getOffered()) == 0)
        list.add(new FtxLendDataDto(coin, op.get().getLocked(), sizeToLend, sizeToLend, rate));
      else {
        try {
          ftx.submitLendingOffer(
              exchange.getExchangeSpecification().getApiKey(),
              exchange.getNonceFactory().createValue(),
              signatureCreator,
              subaccount,
              new FtxSubmitLendingOfferParams(coin, sizeToLend, rate)
          );
          list.add(new FtxLendDataDto(coin, op.get().getLocked(), op.get().getOffered(), sizeToLend, rate));
        } catch (IOException e) {
          throw new FtxLendingServiceException("Can't lend all for subaccount: " + subaccount + ", coin: " + coin + ", rate: " + rate);
        }
      }
    });
    return new ArrayList<>();
  }

  public FtxLendDataDto lend(String subaccount, String coin, double size, double rate) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");
    if (rate < 0)
      throw new FtxLendingServiceException("Rate must to be >= 0, subaccount: " + subaccount + ", coin: " + coin);
    if (size < 0)
      throw new FtxLendingServiceException("Size must to be >= 0, subaccount: " + subaccount + ", coin: " + coin + ", rate: " + rate);

    try {
      FtxLendingInfoDto info = info(subaccount, coin);
      double sizeToLend = FtxAdapters.lendingRounding(new BigDecimal(size)).doubleValue();

      if (Double.compare(sizeToLend, info.getLendable()) == 1) {
        throw new FtxLendingServiceException("Cant't lend > to lendable, subaccount: " + subaccount + ", coin: " + coin + ", size: " + size + ", sizeToLend: " + sizeToLend + ", rate: " + rate);
      }
      ftx.submitLendingOffer(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          new FtxSubmitLendingOfferParams(coin, FtxAdapters.lendingRounding(new BigDecimal(sizeToLend)).doubleValue(), rate)
      );
      return new FtxLendDataDto(coin, info.getLocked(), info.getOffered(), sizeToLend, rate);
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't lend subaccount: " + subaccount + ", coin: " + coin + ", size: " + size + ", rate: " + rate, e);
    }
  }

  public FtxLendDataDto lend(String subaccount, String coin, FtxLendingInfoFunction functionSize, FtxLendingInfoFunction functionRate) {
    Objects.requireNonNull(coin);
    if (StringUtils.isNotBlank(coin))
      throw new FtxLendingServiceException("Coin are blank or empty");

    FtxLendingInfoDto info = info(subaccount, coin);
    if (info == null) {
      throw new FtxLendingServiceException("No info for coin: " + coin);
    }
    double sizeToLend = functionSize.apply(info);
    if (Double.compare(sizeToLend, info.getLendable()) == 1) {
      throw new FtxLendingServiceException("Cant't lend > to lendable, subaccount: " + subaccount + ", coin: " + coin + ", sizeLendable: " + info.getLendable() + ", sizeToLend: " + sizeToLend);
    }
    if (sizeToLend < 0)
      throw new FtxLendingServiceException("Cant't lend > to lendable, subaccount: " + subaccount + ", coin: " + coin + ", sizeLendable: " + info.getLendable() + ", sizeToLend: " + sizeToLend);

    double rate = functionRate.apply(info);
    if (rate < 0)
      throw new FtxLendingServiceException("Cant't lend > to lendable, subaccount: " + subaccount + ", coin: " + coin + ", sizeLendable: " + info.getLendable() + ", sizeToLend: " + sizeToLend + ", rate: " + rate);

    try {
      ftx.submitLendingOffer(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          new FtxSubmitLendingOfferParams(coin, FtxAdapters.lendingRounding(new BigDecimal(sizeToLend)).doubleValue(), rate)
      );
      return new FtxLendDataDto(coin, info.getLocked(), info.getOffered(), sizeToLend, rate);
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't lend subaccount: " + subaccount + ", coin: " + coin + ", sizeToLend: " + sizeToLend + ", rate: " + rate, e);
    }
  }

  public List<FtxLendingHistoryDto> histories(String subaccount) {
    try {
      return ftx.getlendingHistories(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult();
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

  public List<FtxLendingHistoryDto> histories(String subaccount, String... coins) {
    Objects.requireNonNull(coins);
    return histories(subaccount, Arrays.asList(coins));
  }

  public FtxLendingHistoryDto history(String subaccount, String coin) {
    Objects.requireNonNull(coin);
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
          subaccount
      ).getResult();
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

  public List<FtxLendingInfoDto> infos(String subaccount, String... coins) {
    Objects.requireNonNull(coins);
    return infos(subaccount, Arrays.asList(coins));
  }

  public FtxLendingInfoDto info(String subaccount, String coin) {
    Objects.requireNonNull(coin);
    return infos(subaccount).stream()
        .filter(lendingInfo -> lendingInfo.getCoin().equalsIgnoreCase(coin))
        .findFirst()
        .orElse(null);
  }

  public List<FtxLendingRatesDto> rates(String subaccount) {
    try {
      return ftx.getLendingRates(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult();
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't get lending rates", e);
    }
  }

  public List<FtxLendingRatesDto> rates(String subaccount, List<String> coins) {
    Objects.requireNonNull(coins);
    return rates(subaccount).stream()
        .filter(lendingRates -> coins.contains(lendingRates.getCoin()))
        .collect(Collectors.toList());
  }

  public List<FtxLendingRatesDto> rates(String subaccount, String... coins) {
    Objects.requireNonNull(coins);
    return rates(subaccount, Arrays.asList(coins));
  }

  public FtxLendingRatesDto rate(String subaccount, String coin) {
    try {
      return ftx.getLendingRates(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount
      ).getResult().stream()
          .filter(lendingRates -> lendingRates.getCoin().equalsIgnoreCase(coin))
          .findFirst()
          .orElse(null);
    } catch (IOException e) {
      throw new FtxLendingServiceException("Can't get lending rate coin: " + coin, e);
    }
  }

  @FunctionalInterface
  public interface FtxLendingInfoFunction {
    double apply(FtxLendingInfoDto value);
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
