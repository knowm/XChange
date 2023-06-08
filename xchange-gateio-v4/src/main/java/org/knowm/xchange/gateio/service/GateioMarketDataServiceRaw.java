package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyChain;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyInfo;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairDetails;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper;
import org.knowm.xchange.gateio.dto.marketdata.GateioOrderBook;
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

  public Map<CurrencyPair, GateioMarketInfoWrapper.GateioMarketInfo> getMarketInfo()
      throws IOException {

    GateioMarketInfoWrapper bterMarketInfo = gateio.getMarketInfo();

    return bterMarketInfo.getMarketInfoMap();
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

  public GateioTicker getBTERTicker(String tradableIdentifier, String currency) throws IOException {

    GateioTicker gateioTicker =
        gateio.getTicker(tradableIdentifier.toLowerCase(), currency.toLowerCase());

    return handleResponse(gateioTicker);
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


  public List<GateioCurrencyInfo> getCurrencies() throws IOException {
    return gateio.getCurrencies();
  }


  public GateioOrderBook getGateioOrderBook(Instrument instrument) throws IOException {
    return gateio.getOrderBook(GateioAdapters.toString(instrument), false);
  }


  public List<GateioCurrencyChain> getCurrencyChains(Currency currency) throws IOException {
    return gateio.getCurrencyChains(currency.getCurrencyCode());
  }


  public List<GateioCurrencyPairDetails> getCurrencyPairDetails() throws IOException {
      return gateio.getCurrencyPairDetails();
  }


  public GateioCurrencyPairDetails getCurrencyPairDetails(Instrument instrument) throws IOException {
    return gateio.getCurrencyPairDetails(GateioAdapters.toString(instrument));
  }


}
