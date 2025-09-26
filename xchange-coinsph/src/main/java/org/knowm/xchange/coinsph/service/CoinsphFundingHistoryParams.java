package org.knowm.xchange.coinsph.service;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.*;

/** Funding history parameters for Coins.ph */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CoinsphFundingHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamCurrency,
        TradeHistoryParamLimit,
        HistoryParamsFundingType {

  private Currency currency;
  private Date startTime;
  private Date endTime;
  private Integer limit;
  private FundingRecord.Type type;

  @Builder.Default private boolean includeDeposits = true;

  @Builder.Default private boolean includeWithdrawals = true;

  @Override
  public FundingRecord.Type getType() {
    if (type != null) {
      return type;
    }
    return includeDeposits
        ? FundingRecord.Type.DEPOSIT
        : includeWithdrawals ? FundingRecord.Type.WITHDRAWAL : null;
  }

  @Override
  public void setType(FundingRecord.Type type) {
    this.type = type;
  }
}
