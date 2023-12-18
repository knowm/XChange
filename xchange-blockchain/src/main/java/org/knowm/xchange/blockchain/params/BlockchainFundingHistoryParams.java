package org.knowm.xchange.blockchain.params;

import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@Builder
public class BlockchainFundingHistoryParams
    implements TradeHistoryParamCurrency, TradeHistoryParamsTimeSpan, HistoryParamsFundingType {

  private Currency currency;
  private Type type;
  private Date startTime;
  private Date endTime;
}
