package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.dto.account.FundingRecord;

public interface HistoryParamsFundingType extends TradeHistoryParams {

  FundingRecord.Type getType();

  void setType(FundingRecord.Type type);
}
