package org.knowm.xchange.bitcoinde.v4.service;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BitcoindeFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
    implements TradeHistoryParamCurrency, HistoryParamsFundingType, TradeHistoryParamPaging {

  private Currency currency;
  private FundingRecord.Type type;
  private BitcoindeAccountLedgerType customType;
  private boolean leaveFeesSeperate = false;
  private Integer pageNumber;
  // This is used to report back the number of pages available
  private Integer lastPageNumber;

  public BitcoindeFundingHistoryParams(
      final Currency currency,
      final FundingRecord.Type type,
      final Date startTime,
      final Date endTime,
      final Integer pageNumber) {
    super(startTime, endTime);
    this.currency = currency;
    this.type = type;
    this.pageNumber = pageNumber;
  }

  public BitcoindeFundingHistoryParams(
      final Currency currency,
      final BitcoindeAccountLedgerType customType,
      final Date startTime,
      final Date endTime,
      final Integer pageNumber) {
    super(startTime, endTime);
    this.currency = currency;
    this.customType = customType;

    this.pageNumber = pageNumber;
  }

  /**
   * Copy constructor
   *
   * @param other
   */
  public BitcoindeFundingHistoryParams(BitcoindeFundingHistoryParams other) {
    super(other.getStartTime(), other.getEndTime());
    this.currency = other.currency;
    this.type = other.type;
    this.customType = other.customType;
    this.leaveFeesSeperate = other.leaveFeesSeperate;
    this.pageNumber = other.pageNumber;
  }

  // Only include to fulfill TradeHistoryParamPaging interface, can't be changed and isn't used
  @Override
  public Integer getPageLength() {
    return null;
  }

  @Override
  public void setPageLength(final Integer pageLength) {}
}
