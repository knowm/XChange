package org.knowm.xchange.okcoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoin;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinFutureComment;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinFutureHoldAmount;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinFutureKline;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinKline;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinKlineType;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTickerResponse;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTrade;

public class OkCoinMarketDataServiceRaw extends OkCoinBaseService {

  private final OkCoin okCoin;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    okCoin =
        ExchangeRestProxyBuilder.forInterface(OkCoin.class, exchange.getExchangeSpecification())
            .build();
  }

  /**
   * 获取OKEx币币行情
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public OkCoinTickerResponse getTicker(CurrencyPair currencyPair) throws IOException {
    return okCoin.getTicker("1", OkCoinAdapters.adaptSymbol(currencyPair));
  }

  /**
   * 获取OKEx合约行情
   *
   * @param currencyPair
   * @param prompt
   * @return
   * @throws IOException
   */
  public OkCoinTickerResponse getFuturesTicker(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {
    return okCoin.getFuturesTicker(OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName());
  }

  /**
   * 获取OKEx币币市场深度
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public OkCoinDepth getDepth(CurrencyPair currencyPair) throws IOException {
    return getDepth(currencyPair, null);
  }

  /**
   * 获取OKEx币币市场深度
   *
   * @param currencyPair
   * @param size 设置查询数据条数，[1,200]
   * @return
   * @throws IOException
   */
  public OkCoinDepth getDepth(CurrencyPair currencyPair, Integer size) throws IOException {
    size = (size == null || size < 1 || size > 200) ? 200 : size;
    return okCoin.getDepth("1", OkCoinAdapters.adaptSymbol(currencyPair), size);
  }

  /**
   * 获取OKEx合约深度信息
   *
   * @param currencyPair
   * @param prompt
   * @return
   * @throws IOException
   */
  public OkCoinDepth getFuturesDepth(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {
    return okCoin.getFuturesDepth(
        "1", OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName().toLowerCase());
  }

  /**
   * 获取OKEx币币交易信息(60条)
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public OkCoinTrade[] getTrades(CurrencyPair currencyPair) throws IOException {
    return getTrades(currencyPair, null);
  }

  /**
   * 获取OKEx币币交易信息(60条)
   *
   * @param currencyPair
   * @param since tid:交易记录ID(返回数据为：最新交易信息tid值--当前tid值之间的交易信息 ,但最多返回60条数据)
   * @return
   * @throws IOException
   */
  public OkCoinTrade[] getTrades(CurrencyPair currencyPair, Long since) throws IOException {
    return okCoin.getTrades("1", OkCoinAdapters.adaptSymbol(currencyPair), since);
  }

  /**
   * 获取OKEx合约交易记录信息
   *
   * @param currencyPair
   * @param prompt
   * @return
   * @throws IOException
   */
  public OkCoinTrade[] getFuturesTrades(CurrencyPair currencyPair, FuturesContract prompt)
      throws IOException {
    return okCoin.getFuturesTrades(
        "1", OkCoinAdapters.adaptSymbol(currencyPair), prompt.getName().toLowerCase());
  }

  /**
   * 获取OKEx合约指数信息
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public OkCoinFutureComment getFuturesIndex(CurrencyPair currencyPair) throws IOException {
    return okCoin.getFuturesIndex("1", OkCoinAdapters.adaptSymbol(currencyPair));
  }

  /**
   * @param symbol btc_usdt,ltc_usdt,eth_usdt 等
   * @return
   * @throws IOException
   */
  public OkCoinFutureComment getFuturesIndex(String symbol) throws IOException {
    return okCoin.getFuturesIndex("1", symbol);
  }

  /**
   * 获取美元人民币汇率
   *
   * @return
   * @throws IOException
   */
  public OkCoinFutureComment getExchangRate_US_CH() throws IOException {
    return okCoin.getExchangRate_US_CH();
  }

  /**
   * 获取交割预估价
   *
   * @param currencyPair
   * @return
   * @throws IOException
   */
  public OkCoinFutureComment getFutureEstimatedPrice(CurrencyPair currencyPair) throws IOException {
    return okCoin.getFutureEstimatedPrice("1", OkCoinAdapters.adaptSymbol(currencyPair));
  }

  /**
   * 获取OKEx合约K线信息
   *
   * @param currencyPair
   * @param type
   * @param contractType
   * @param size
   * @param since
   * @return
   * @throws IOException
   */
  public List<OkCoinFutureKline> getFutureKline(
      CurrencyPair currencyPair,
      OkCoinKlineType type,
      FuturesContract contractType,
      Integer size,
      Long since)
      throws IOException {
    List<Object[]> list =
        okCoin.getFutureKline(
            "1",
            OkCoinAdapters.adaptSymbol(currencyPair),
            type.getType(),
            contractType.getName(),
            size,
            since);
    List<OkCoinFutureKline> klineList = new ArrayList<>();
    list.stream().forEach(kline -> klineList.add(new OkCoinFutureKline(kline)));
    return klineList;
  }

  public List<OkCoinFutureKline> getFutureKline(
      CurrencyPair currencyPair, OkCoinKlineType type, FuturesContract contractType)
      throws IOException {
    return getFutureKline(currencyPair, type, contractType, 0, 0L);
  }

  public List<OkCoinFutureKline> getFutureKline(
      CurrencyPair currencyPair, OkCoinKlineType type, FuturesContract contractType, Integer size)
      throws IOException {
    return getFutureKline(currencyPair, type, contractType, size, 0L);
  }

  public List<OkCoinFutureKline> getFutureKline(
      CurrencyPair currencyPair, OkCoinKlineType type, FuturesContract contractType, Long since)
      throws IOException {
    return getFutureKline(currencyPair, type, contractType, 0, since);
  }

  /**
   * 获取当前可用合约总持仓量
   *
   * @param currencyPair
   * @param contractType
   * @return
   * @throws IOException
   */
  public OkCoinFutureHoldAmount[] getFutureHoldAmount(
      CurrencyPair currencyPair, FuturesContract contractType) throws IOException {
    return okCoin.getFutureHoldAmount(
        "1", OkCoinAdapters.adaptSymbol(currencyPair), contractType.getName());
  }

  /**
   * 获取合约最高限价和最低限价
   *
   * @param currencyPair
   * @param contractType
   * @return
   * @throws IOException
   */
  public OkCoinFutureComment getFuturePriceLimit(
      CurrencyPair currencyPair, FuturesContract contractType) throws IOException {
    return okCoin.getFuturePriceLimit(
        "1", OkCoinAdapters.adaptSymbol(currencyPair), contractType.getName());
  }

  /**
   * 获取OKEx币币K线数据(每个周期数据条数2000左右)
   *
   * @param currencyPair
   * @param type
   * @return
   * @throws IOException
   */
  public List<OkCoinKline> getKlines(CurrencyPair currencyPair, OkCoinKlineType type)
      throws IOException {
    return getKlines(currencyPair, type, null, null);
  }

  /**
   * 获取OKEx币币K线数据
   *
   * @param currencyPair
   * @param type
   * @param size 指定获取数据的条数
   * @return
   * @throws IOException
   */
  public List<OkCoinKline> getKlines(CurrencyPair currencyPair, OkCoinKlineType type, Integer size)
      throws IOException {
    return getKlines(currencyPair, type, size, null);
  }

  /**
   * 获取OKEx币币K线数据
   *
   * @param currencyPair
   * @param type
   * @param timestamp 指定获取数据的条数
   * @return
   * @throws IOException
   */
  public List<OkCoinKline> getKlines(
      CurrencyPair currencyPair, OkCoinKlineType type, Long timestamp) throws IOException {
    return getKlines(currencyPair, type, null, timestamp);
  }

  /**
   * 获取OKEx币币K线数据
   *
   * @param currencyPair
   * @param type
   * @param size
   * @param timestamp 返回该时间戳以后的数据
   * @throws IOException
   */
  public List<OkCoinKline> getKlines(
      CurrencyPair currencyPair, OkCoinKlineType type, Integer size, Long timestamp)
      throws IOException {
    List<OkCoinKline> klineList = new ArrayList<>();
    List<Object[]> list =
        okCoin.getKlines(OkCoinAdapters.adaptSymbol(currencyPair), type.getType(), size, timestamp);
    list.stream().forEach(kline -> klineList.add(new OkCoinKline(kline)));
    return klineList;
  }
}
