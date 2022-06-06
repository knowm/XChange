package org.knowm.xchange.bitz.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitz.BitZUtils;
import org.knowm.xchange.bitz.dto.marketdata.BitZCurrencyRate;
import org.knowm.xchange.bitz.dto.marketdata.BitZKline;
import org.knowm.xchange.bitz.dto.marketdata.BitZKlineResolution;
import org.knowm.xchange.bitz.dto.marketdata.BitZOrders;
import org.knowm.xchange.bitz.dto.marketdata.BitZSymbolList;
import org.knowm.xchange.bitz.dto.marketdata.BitZTicker;
import org.knowm.xchange.bitz.dto.marketdata.BitZTickerAll;
import org.knowm.xchange.bitz.dto.marketdata.BitZTrades;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZOrdersResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTickerAllResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTickerResult;
import org.knowm.xchange.bitz.dto.marketdata.result.BitZTradesResult;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

public class BitZMarketDataServiceRaw extends BitZBaseService {

  public BitZMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  // TODO: Exception Handling - See Bitfinex
  public BitZTickerAllResult getBitZTickerAll() throws IOException {
    return bitz.getTickerAllResult();
  }

  // TODO: Exception Handling - See Bitfinex
  public BitZTicker getBitZTicker(String pair) throws IOException {
    return bitz.getTickerResult(pair).getData();
  }

  // TODO: Exception Handling - See Bitfinex
  public BitZOrders getBitZOrders(String pair) throws IOException {
    return bitz.getOrdersResult(pair).getData();
  }

  // TODO: Exception Handling - See Bitfinex
  public BitZTrades getBitZTrades(String pair) throws IOException {
    return bitz.getTradesResult(pair).getData();
  }

  // TODO: Exception Handling - See Bitfinex
  public BitZKline getBitZKline(String pair, String type) throws IOException {
    return bitz.getKlineResult(pair, type).getData();
  }

  /**
   * @param currencyPairs
   * @return
   * @throws IOException
   */
  public Map<Currency, Map<Currency, BigDecimal>> getCoinRate(CurrencyPair... currencyPairs)
      throws IOException {
    List<String> coinList = new ArrayList<>(currencyPairs.length);
    Arrays.stream(currencyPairs)
        .forEach(
            currencyPair -> coinList.add(currencyPair.counter.getCurrencyCode().toLowerCase()));
    String coins = coinList.stream().collect(Collectors.joining(","));
    return bitz.getCurrencyCoinRate(coins).getData();
  }

  /**
   * Get Kline data
   *
   * @param currencyPair
   * @param resolution
   * @param size
   * @param microsecond
   * @return
   * @throws IOException
   */
  public BitZKline getKline(
      CurrencyPair currencyPair, BitZKlineResolution resolution, Integer size, String microsecond)
      throws IOException {
    return bitz.getKline(BitZUtils.toPairString(currencyPair), resolution.code(), size, microsecond)
        .getData();
  }

  /**
   * Get the Ticker data
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public BitZTicker getTicker(CurrencyPair currencyPair) throws IOException {
    BitZTickerResult result = bitz.getTicker(BitZUtils.toPairString(currencyPair));
    return result.getData();
  }

  /**
   * Get the price of all symbol
   *
   * @param currencyPairs
   * @return
   * @throws IOException
   */
  public BitZTickerAll getTickerAll(CurrencyPair... currencyPairs) throws IOException {
    List<String> symbolList = new ArrayList<>(currencyPairs.length);
    Arrays.stream(currencyPairs)
        .forEach(currencyPair -> symbolList.add(BitZUtils.toPairString(currencyPair)));
    String symbols = symbolList.stream().collect(Collectors.joining(","));
    BitZTickerAllResult result = bitz.getTickerAll(symbols);
    return result.getData();
  }

  /**
   * Get depth data
   *
   * @param currencyPair
   * @throws IOException
   */
  public BitZOrders getDepth(CurrencyPair currencyPair) throws IOException {
    BitZOrdersResult result = bitz.getDepth(BitZUtils.toPairString(currencyPair));
    return result.getData();
  }

  /**
   * Get the order
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public BitZTrades getOrder(CurrencyPair currencyPair) throws IOException {
    BitZTradesResult result = bitz.getOrder(BitZUtils.toPairString(currencyPair));
    return result.getData();
  }

  /**
   * 获取每种交易对的信息
   *
   * @param currencyPairs
   * @throws IOException
   */
  public BitZSymbolList getSymbolList(CurrencyPair... currencyPairs) throws IOException {
    List<String> symbolList = new ArrayList<>(currencyPairs.length);
    Arrays.stream(currencyPairs)
        .forEach(currencyPair -> symbolList.add(BitZUtils.toPairString(currencyPair)));
    String symbols = symbolList.stream().collect(Collectors.joining(","));
    return bitz.getSymbolList(symbols).getData();
  }

  /**
   * 获取当前法币汇率信息
   *
   * @param currencyPairs
   * @return
   * @throws IOException
   */
  public Map<String, BitZCurrencyRate> getCurrencyRate(CurrencyPair... currencyPairs)
      throws IOException {
    List<String> symbolList = new ArrayList<>(currencyPairs.length);
    Arrays.stream(currencyPairs)
        .forEach(currencyPair -> symbolList.add(BitZUtils.toPairString(currencyPair)));
    String symbols = symbolList.stream().collect(Collectors.joining(","));
    return bitz.getCurrencyRate(symbols).getData();
  }

  /**
   * 获取虚拟货币法币汇率信息
   *
   * @param currencyPairs
   * @return
   * @throws IOException
   */
  public Map<Currency, Map<Currency, BigDecimal>> getCurrencyCoinRate(CurrencyPair... currencyPairs)
      throws IOException {
    List<String> coinList = new ArrayList<>(currencyPairs.length);
    Arrays.stream(currencyPairs)
        .forEach(currencyPair -> coinList.add(currencyPair.base.getCurrencyCode().toLowerCase()));
    String coins = coinList.stream().collect(Collectors.joining(","));
    return bitz.getCurrencyCoinRate(coins).getData();
  }
}
