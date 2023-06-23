package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.GateioCandlestickHistory;
import org.knowm.xchange.gateio.dto.marketdata.GateioCoinInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioDepth;
import org.knowm.xchange.gateio.dto.marketdata.GateioFeeInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioKline;
import org.knowm.xchange.gateio.dto.marketdata.GateioKlineInterval;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioTicker;
import org.knowm.xchange.gateio.dto.marketdata.GateioTradeHistory;
import org.knowm.xchange.instrument.Instrument;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataServiceRaw(GateioExchange exchange) {

    super(exchange);
  }

  public Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> getGateioMarketInfo()
      throws IOException {

    GateioMarketInfoWrapper bterMarketInfo = gateio.getMarketInfo();

    return bterMarketInfo.getMarketInfoMap();
  }

  public GateioCoinInfoWrapper getGateioCoinInfo() throws IOException {
    return gateio.getCoinInfo();
  }

  public Map<String, GateioFeeInfo> getGateioFees() throws IOException {
    return gateioAuthenticated.getFeeList(apiKey, signatureCreator);
  }

  public Map<CurrencyPair, Ticker> getGateioTickers() throws IOException {

    Map<String, GateioTicker> gateioTickers = gateio.getTickers();
    Map<CurrencyPair, Ticker> adaptedTickers = new HashMap<>(gateioTickers.size());
    gateioTickers.forEach(
        (currencyPairString, gateioTicker) -> {
          String[] currencyPairStringSplit = currencyPairString.split("_");
          CurrencyPair currencyPair =
              new CurrencyPair(
                  Currency.getInstance(currencyPairStringSplit[0].toUpperCase()),
                  Currency.getInstance(currencyPairStringSplit[1].toUpperCase()));
          adaptedTickers.put(currencyPair, GateioAdapters.adaptTicker(currencyPair, gateioTicker));
        });

    return adaptedTickers;
  }

  public Map<CurrencyPair, GateioDepth> getGateioDepths() throws IOException {
    Map<String, GateioDepth> depths = gateio.getDepths();
    Map<CurrencyPair, GateioDepth> adaptedDepths = new HashMap<>(depths.size());
    depths.forEach(
        (currencyPairString, gateioDepth) -> {
          String[] currencyPairStringSplit = currencyPairString.split("_");
          CurrencyPair currencyPair =
              new CurrencyPair(
                  Currency.getInstance(currencyPairStringSplit[0].toUpperCase()),
                  Currency.getInstance(currencyPairStringSplit[1].toUpperCase()));
          adaptedDepths.put(currencyPair, gateioDepth);
        });
    return adaptedDepths;
  }

  public GateioTicker getBTERTicker(String tradableIdentifier, String currency) throws IOException {

    GateioTicker gateioTicker =
        gateio.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioTicker);
  }

  public GateioDepth getBTEROrderBook(String tradeableIdentifier, String currency)
      throws IOException {

    GateioDepth gateioDepth =
        gateio.getFullDepth(tradeableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioDepth);
  }

  public GateioTradeHistory getBTERTradeHistory(String tradeableIdentifier, String currency)
      throws IOException {

    GateioTradeHistory tradeHistory = gateio.getTradeHistory(tradeableIdentifier, currency);

    return handleResponse(tradeHistory);
  }

  public GateioTradeHistory getBTERTradeHistorySince(
      String tradeableIdentifier, String currency, String tradeId) throws IOException {

    GateioTradeHistory tradeHistory =
        gateio.getTradeHistorySince(tradeableIdentifier, currency, tradeId);

    return handleResponse(tradeHistory);
  }

  public List<Instrument> getExchangeSymbols() throws IOException {

    return new ArrayList<>(gateio.getPairs().getPairs());
  }

  public List<GateioKline> getKlines(CurrencyPair pair, GateioKlineInterval interval, Integer hours)
      throws IOException {

    if (hours != null && hours < 1)
      throw new ExchangeException("Variable 'hours' should be more than 0!");

    GateioCandlestickHistory candlestickHistory =
        handleResponse(
            gateio.getKlinesGate(
                pair.toString().replace('/', '_').toLowerCase(), hours, interval.getSeconds()));

    return candlestickHistory.getCandlesticks().stream()
        .map(
            data ->
                new GateioKline(
                    Long.parseLong(data.get(0)),
                    new BigDecimal(data.get(1)),
                    new BigDecimal(data.get(2)),
                    new BigDecimal(data.get(3)),
                    new BigDecimal(data.get(4)),
                    new BigDecimal(data.get(5))))
        .collect(Collectors.toList());
  }
}
