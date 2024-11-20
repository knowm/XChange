package org.knowm.xchange.service.trade.params;

/**
 * {@link TradeHistoryParams} with client order id as param
 */
public interface TradeHistoryParamClientOid extends TradeHistoryParams {

  String getClientOid();

  void setClientOid(String clientOid);

}
