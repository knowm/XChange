package org.knowm.xchange.bitz.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitz.BitZ;
import org.knowm.xchange.bitz.BitZAuthenticated;
import org.knowm.xchange.bitz.dto.account.result.BitZUserAssetsResult;
import org.knowm.xchange.bitz.dto.marketdata.BitZPublicOrder;
import org.knowm.xchange.bitz.dto.trade.result.BitZEntrustSheetInfoResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeAddResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeCancelListResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZTradeCancelResult;
import org.knowm.xchange.bitz.dto.trade.result.BitZUserHistoryResult;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitZTradeServiceRaw extends BitZBaseService {

  private BitZAuthenticated bitzAuthenticated;

  private String apiKey;
  private String secretKey;
  private String tradePwd;
  private BitZDigest signer;
  private SynchronizedValueFactory<Long> nonceFactory;
  private String nonce;

  public BitZTradeServiceRaw(Exchange exchange) {
    super(exchange);

    this.bitz =
        ExchangeRestProxyBuilder.forInterface(BitZ.class, exchange.getExchangeSpecification())
            .build();
    this.bitzAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitZAuthenticated.class, exchange.getExchangeSpecification())
            .build();

    // TODO: Implement Password
    this.tradePwd = "";

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.secretKey = exchange.getExchangeSpecification().getSecretKey();

    this.signer = BitZDigest.createInstance();
    this.nonceFactory = exchange.getNonceFactory();
    this.nonce = nonceFactory.toString().substring(nonceFactory.toString().length() - 6);
  }

  // TODO: Implement Method
  public boolean cancelBitZTrade(int orderId) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  // TODO: Implement Method
  public BitZTradeAddResult placeBitZTrade(
      CurrencyPair currencyPair, BitZPublicOrder limitOrder, Date time, boolean sell)
      throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  private String getTimeStamp() {
    return String.valueOf(System.currentTimeMillis() / 1000);
  }
  /**
   * 提交委托单(下订单)
   *
   * @param symbol
   * @param type 购买类型 1买进 2 卖出
   * @param price
   * @param number
   * @return
   * @throws IOException
   */
  public BitZTradeAddResult addEntrustSheet(
      String symbol, String type, BigDecimal price, BigDecimal number) throws IOException {
    return bitz.addEntrustSheet(
        apiKey, symbol, getTimeStamp(), nonce, signer, type, price, number, tradePwd);
  }

  /**
   * 取消委托单
   *
   * @param entrustSheetId
   * @return
   * @throws IOException
   */
  public BitZTradeCancelResult cancelEntrustSheet(String entrustSheetId) throws IOException {
    return bitz.cancelEntrustSheet(apiKey, getTimeStamp(), nonce, signer, entrustSheetId);
  }

  /**
   * 批量取消委托
   *
   * @param ids
   * @return
   * @throws IOException
   */
  public BitZTradeCancelListResult cancelAllEntrustSheet(String ids) throws IOException {
    return bitz.cancelAllEntrustSheet(apiKey, getTimeStamp(), nonce, signer, ids);
  }

  /**
   * 获取个人历史委托单列表
   *
   * @param coinFrom
   * @param coinTo
   * @param type
   * @param page
   * @param pageSize
   * @param startTime
   * @param endTime
   * @return
   * @throws IOException
   */
  public BitZUserHistoryResult getUserHistoryEntrustSheet(
      String coinFrom,
      String coinTo,
      Integer type,
      Integer page,
      Integer pageSize,
      String startTime,
      String endTime)
      throws IOException {
    page = page == null ? 1 : page;
    pageSize = pageSize == null ? 100 : pageSize;
    if (type == null) {
      return bitz.getUserHistoryEntrustSheet(
          apiKey,
          getTimeStamp(),
          nonce,
          signer,
          coinFrom,
          coinTo,
          page,
          pageSize,
          startTime,
          endTime);
    }
    return bitz.getUserHistoryEntrustSheet(
        apiKey,
        getTimeStamp(),
        nonce,
        signer,
        coinFrom,
        coinTo,
        type,
        page,
        pageSize,
        startTime,
        endTime);
  }

  public BitZUserHistoryResult getUserHistoryEntrustSheet() throws IOException {
    return getUserHistoryEntrustSheet(null, null, null, null, null, null, null);
  }

  /**
   * 获取委托单详情
   *
   * @param entrustSheetId
   * @return
   * @throws IOException
   */
  public BitZEntrustSheetInfoResult getEntrustSheetInfo(String entrustSheetId) throws IOException {
    return bitz.getEntrustSheetInfo(apiKey, getTimeStamp(), nonce, signer, entrustSheetId);
  }

  /**
   * 获取个人资产
   *
   * @return
   * @throws IOException
   */
  public BitZUserAssetsResult getUserAssets() throws IOException {
    return bitz.getUserAssets(apiKey, getTimeStamp(), nonce, signer);
  }
}
