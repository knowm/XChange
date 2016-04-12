package org.knowm.xchange.huobi.dto.streaming.response.payload;

import org.knowm.xchange.huobi.dto.streaming.response.service.ReqSymbolListResponse;

/**
 * Payload of {@link ReqSymbolListResponse}.
 */
public class ReqSymbolListPayload extends AbstractPayload {

  private final String[] symbolId;
  private final String[] symbolName;
  private final String[] cryptoId;
  private final String[] cryptoName;
  private final String[] exchangeId;
  private final String[] exchangeName;
  private final String[] currencyId;
  private final String[] currencyName;

  /**
   * 获取交易代码列表
   *
   * @param symbolId 交易代码
   * @param symbolName 虚拟货币名称
   * @param cryptoId 数字货币id
   * @param cryptoName 数字货币名称
   * @param exchangeId 交易所Id
   * @param exchangeName 交易所名称
   * @param currencyId 现金Id
   * @param currencyName 现金名称
   */
  public ReqSymbolListPayload(String[] symbolId, String[] symbolName, String[] cryptoId, String[] cryptoName, String[] exchangeId,
      String[] exchangeName, String[] currencyId, String[] currencyName) {
    this.symbolId = symbolId;
    this.symbolName = symbolName;
    this.cryptoId = cryptoId;
    this.cryptoName = cryptoName;
    this.exchangeId = exchangeId;
    this.exchangeName = exchangeName;
    this.currencyId = currencyId;
    this.currencyName = currencyName;
  }

  public String[] getSymbolId() {
    return symbolId;
  }

  public String[] getSymbolName() {
    return symbolName;
  }

  public String[] getCryptoId() {
    return cryptoId;
  }

  public String[] getCryptoName() {
    return cryptoName;
  }

  public String[] getExchangeId() {
    return exchangeId;
  }

  public String[] getExchangeName() {
    return exchangeName;
  }

  public String[] getCurrencyId() {
    return currencyId;
  }

  public String[] getCurrencyName() {
    return currencyName;
  }

}
