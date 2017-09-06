package org.knowm.xchange.bittrex.service;

import java.io.IOException;
import java.util.ArrayList;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.BittrexUtils;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexChartData;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexChartDataResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrenciesResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrency;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepth;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexDepthResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummariesResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummaryResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbolsResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTickerResponse;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTradesResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

public class BittrexMarketDataServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BittrexCurrency[] getBittrexCurrencies() throws IOException {

    BittrexCurrenciesResponse response = bittrexAuthenticated.getCurrencies();

    if (response.isSuccess()) {
      return response.getCurrencies();
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public BittrexTicker getBittrexTicker(CurrencyPair currencyPair) throws IOException {
    BittrexTickerResponse response = bittrexAuthenticated.getTicker(BittrexUtils.toPairString(currencyPair));

    if (response.getSuccess()) {
      return response.getTicker();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public ArrayList<BittrexSymbol> getBittrexSymbols() throws IOException {

    BittrexSymbolsResponse response = bittrexAuthenticated.getSymbols();

    if (response.isSuccess()) {
      return response.getSymbols();
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public BittrexMarketSummary getBittrexMarketSummary(String pair) throws IOException {

    BittrexMarketSummaryResponse response = bittrexAuthenticated.getMarketSummary(pair);

    if (response.getSuccess()) {
      return response.getMarketSummary();
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public ArrayList<BittrexMarketSummary> getBittrexMarketSummaries() throws IOException {

    BittrexMarketSummariesResponse response = bittrexAuthenticated.getMarketSummaries();

    if (response.isSuccess()) {
      return response.getMarketSummaries();
    } else {
      throw new ExchangeException(response.getMessage());
    }

  }

  public BittrexDepth getBittrexOrderBook(String pair, int depth) throws IOException {

    BittrexDepthResponse response = bittrexAuthenticated.getBook(pair, "both", depth);

    if (response.getSuccess()) {

      BittrexDepth bittrexDepth = response.getDepth();
      return bittrexDepth;
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public BittrexTrade[] getBittrexTrades(String pair, int count) throws IOException {

    BittrexTradesResponse response = bittrexAuthenticated.getTrades(pair, count);

    if (response.getSuccess()) {

      BittrexTrade[] bittrexTrades = response.getTrades();
      return bittrexTrades;
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public ArrayList<BittrexChartData> getBittrexChartData(CurrencyPair currencyPair, BittrexChartDataPeriodType periodType) throws IOException {

    BittrexChartDataResponse response = bittrexV2.getChartData(BittrexUtils.toPairString(currencyPair), periodType.getPeriod());

    if (response.getSuccess()) {
      return response.getChartData();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

}
