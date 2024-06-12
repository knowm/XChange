package org.knowm.xchange.upbit.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;
import org.knowm.xchange.upbit.UpbitUtils;
import org.knowm.xchange.upbit.dto.marketdata.*;
import org.knowm.xchange.utils.DateUtils;

/**
 * Implementation of the market data service for Korbit
 *
 * <p>
 *
 * <p>
 *
 * <p>
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class UpbitMarketDataServiceRaw extends UpbitBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public UpbitTickers getTickers(List<Instrument> currencyPair) throws IOException {
    return upbit.getTicker(
        currencyPair.stream().map(UpbitUtils::toPairString).collect(Collectors.joining(",")));
  }

  public List<UpbitMarket> getMarketAll() throws IOException {
    return upbit.getMarketAll();
  }

  public UpbitTickers getTicker(CurrencyPair currencyPair) throws IOException {
    return upbit.getTicker(UpbitUtils.toPairString(currencyPair));
  }

  public UpbitOrderBooks getUpbitOrderBook(CurrencyPair currencyPair) throws IOException {
    return upbit.getOrderBook(UpbitUtils.toPairString(currencyPair));
  }

  public UpbitTrades getTrades(CurrencyPair currencyPair) throws IOException {
    return upbit.getTrades(UpbitUtils.toPairString(currencyPair), 100);
  }

  public List<UpbitCandleStickData> getUpbitCandleStickData(
      CurrencyPair currencyPair, CandleStickDataParams params) throws IOException {

    if (!(params instanceof DefaultCandleStickParam)) {
      throw new NotYetImplementedForExchangeException("Only DefaultCandleStickParam is supported");
    }
    final DefaultCandleStickParam defaultCandleStickParam = (DefaultCandleStickParam) params;
    final UpbitCandleStickPeriodType periodType =
        UpbitCandleStickPeriodType.getPeriodTypeFromSecs(defaultCandleStickParam.getPeriodInSecs());
    if (periodType == null) {
      throw new NotYetImplementedForExchangeException(
          "Only discrete period values are supported;"
              + Arrays.toString(UpbitCandleStickPeriodType.getSupportedPeriodsInSecs()));
    }

    Integer limit = null;
    if (params instanceof DefaultCandleStickParamWithLimit) {
      limit = ((DefaultCandleStickParamWithLimit) params).getLimit();
    }
    final String pathName = periodType.getPathName();
    final String pairString = UpbitUtils.toPairString(currencyPair);
    final String utcString = DateUtils.toISO8601DateString(defaultCandleStickParam.getEndDate());
    final long unitCount = periodType.getUnitCount(defaultCandleStickParam.getPeriodInSecs());
    return upbit.getCandleStick(pathName, unitCount, pairString, utcString, limit);
  }
}
