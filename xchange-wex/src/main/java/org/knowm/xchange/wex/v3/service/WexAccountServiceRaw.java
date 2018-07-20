package org.knowm.xchange.wex.v3.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.wex.v3.dto.account.WexAccountInfo;
import org.knowm.xchange.wex.v3.dto.account.WexAccountInfoReturn;
import org.knowm.xchange.wex.v3.dto.account.WexWithDrawInfoReturn;
import org.knowm.xchange.wex.v3.dto.trade.WexTransHistoryResult;
import org.knowm.xchange.wex.v3.dto.trade.WexTransHistoryReturn;

/** Author: brox */
public class WexAccountServiceRaw extends WexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public WexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * @return WexAccountInfo object: {funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0},
   *     rights={info=1, trade=1, withdraw=1}, transaction_count=1, open_orders=0,
   *     server_time=1357678428}
   */
  public WexAccountInfo getBTCEAccountInfo() throws IOException {

    WexAccountInfoReturn info = btce.getInfo(apiKey, signatureCreator, exchange.getNonceFactory());
    checkResult(info);
    return info.getReturnValue();
  }

  /**
   * Author: Ondřej Novtný
   *
   * @param currency Currency to withdraw
   * @param amount Amount of withdrawal
   * @param address Withdrawall address
   * @return Transactoin ID
   */
  public String withdraw(String currency, BigDecimal amount, String address) {
    WexWithDrawInfoReturn info =
        btce.WithdrawCoin(
            apiKey, signatureCreator, exchange.getNonceFactory(), currency, amount, address);
    checkResult(info);
    return String.valueOf(info.getReturnValue().gettId());
  }

  public Map<Long, WexTransHistoryResult> transactionsHistory(TradeHistoryParams params) throws IOException {
	Long since = null;
	Long from = null;
    
	if (params instanceof TradeHistoryParamsTimeSpan) {
        final TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
        since = timeSpanParam.getStartTime().toInstant().getEpochSecond();
    }
    
    if (params instanceof TradeHistoryParamOffset) {
    	    from = ((TradeHistoryParamOffset) params).getOffset();
    }

    WexTransHistoryReturn info = btce.TransHistory(
        apiKey, 
        signatureCreator, 
        exchange.getNonceFactory(), 
        from, 
        null, 
        null, 
        null, 
        null, 
        since, 
        null);

    checkResult(info);

    Map<Long, WexTransHistoryResult> map = info.getReturnValue();

    return map == null ? Collections.emptyMap() : map;
  }
}
