package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.*;

import static java.util.concurrent.TimeUnit.HOURS;

public class GateioMarketDataServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> getBTERMarketInfo()
      throws IOException {

    GateioMarketInfoWrapper bterMarketInfo = bter.getMarketInfo();

    return bterMarketInfo.getMarketInfoMap();
  }

  public Map<CurrencyPair, Ticker> getGateioTickers() throws IOException {

    Map<String, GateioTicker> gateioTickers = bter.getTickers();
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
    Map<String, GateioDepth> depths = bter.getDepths();
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
        bter.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioTicker);
  }

  public GateioDepth getBTEROrderBook(String tradeableIdentifier, String currency)
      throws IOException {

    GateioDepth gateioDepth =
        bter.getFullDepth(tradeableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioDepth);
  }

  public GateioTradeHistory getBTERTradeHistory(String tradeableIdentifier, String currency)
      throws IOException {

    GateioTradeHistory tradeHistory = bter.getTradeHistory(tradeableIdentifier, currency);

    return handleResponse(tradeHistory);
  }

  public GateioTradeHistory getBTERTradeHistorySince(
      String tradeableIdentifier, String currency, String tradeId) throws IOException {

    GateioTradeHistory tradeHistory =
        bter.getTradeHistorySince(tradeableIdentifier, currency, tradeId);

    return handleResponse(tradeHistory);
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {

    List<CurrencyPair> currencyPairs = new ArrayList<>(bter.getPairs().getPairs());
    return currencyPairs;
  }

  public List<GateioKline> getKlines(CurrencyPair pair, GateioKlineInterval interval, int limit) throws IOException {

    limit = limit < 0 ? 1 : limit;

    long limitHours = (interval.getSeconds() * limit) / HOURS.toSeconds(1);

    GateioCandlestickHistory candlestickHistory = handleResponse(
            bter.getKlinesGate(
                    pair.toString().replace('/', '_').toLowerCase(),
                    limitHours > 1 ? limitHours : 1,
                    interval.getSeconds()
            )
    );

    List<GateioKline> result = candlestickHistory.getCandlesticks().stream()
            .map(data -> new GateioKline(
                    Long.parseLong(data.get(0)),
                    new BigDecimal(data.get(1)),
                    new BigDecimal(data.get(2)),
                    new BigDecimal(data.get(3)),
                    new BigDecimal(data.get(4)),
                    new BigDecimal(data.get(5))
            ))
            .collect(Collectors.toList());

    if (limit > result.size())
      return result;
    else
      return result.subList(result.size() - limit, result.size());
  }
}
