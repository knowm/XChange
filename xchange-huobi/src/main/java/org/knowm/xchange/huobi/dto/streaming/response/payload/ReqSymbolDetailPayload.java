package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.service.ReqSymbolDetailResponse;

/**
 * Payload of {@link ReqSymbolDetailResponse}.
 */
public class ReqSymbolDetailPayload extends ReqSymbolListPayload {

  private final BigDecimal[] total;
  private final BigDecimal[] suply;
  private final String[] introduction;

  /**
   * 获取交易代码详细信息
   *
   * @param symbolId 交易代码
   * @param symbolName 虚拟货币名称
   * @param cryptoId 数字货币id
   * @param cryptoName 数字货币名称
   * @param exchangeId 交易所Id
   * @param exchangeName 交易所名称
   * @param currencyId 现金Id
   * @param currencyName 现金名称
   * @param total 总量
   * @param suply 流通量
   * @param introduction 中文名
   */
  public ReqSymbolDetailPayload(String[] symbolId, String[] symbolName, String[] cryptoId, String[] cryptoName, String[] exchangeId,
      String[] exchangeName, String[] currencyId, String[] currencyName, BigDecimal[] total, BigDecimal[] suply, String[] introduction) {
    super(symbolId, symbolName, cryptoId, cryptoName, exchangeId, exchangeName, currencyId, currencyName);
    this.total = total;
    this.suply = suply;
    this.introduction = introduction;
  }

  public BigDecimal[] getTotal() {
    return total;
  }

  public BigDecimal[] getSuply() {
    return suply;
  }

  public String[] getIntroduction() {
    return introduction;
  }

}
