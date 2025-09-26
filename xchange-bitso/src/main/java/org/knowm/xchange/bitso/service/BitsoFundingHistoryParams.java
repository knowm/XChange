package org.knowm.xchange.bitso.service;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.*;

/**
 * Funding history parameters for Bitso Supports deposits, withdrawals, and various filtering
 * options
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BitsoFundingHistoryParams
    implements TradeHistoryParams,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamCurrency,
        TradeHistoryParamLimit,
        HistoryParamsFundingType {

  /** Currency filter for funding records */
  private Currency currency;

  /** Start time for filtering */
  private Date startTime;

  /** End time for filtering */
  private Date endTime;

  /** Maximum number of records to return */
  private Integer limit;

  /** Type of funding records to include (DEPOSIT, WITHDRAWAL, or null for both) */
  private FundingRecord.Type type;

  /** Include deposit records */
  @Builder.Default private boolean includeDeposits = true;

  /** Include withdrawal records */
  @Builder.Default private boolean includeWithdrawals = true;

  /** Include pending transactions */
  @Builder.Default private boolean includePending = true;

  /** Include completed transactions */
  @Builder.Default private boolean includeCompleted = true;

  /** Include failed/cancelled transactions */
  @Builder.Default private boolean includeFailed = false;

  /** Bitso-specific transaction status filter */
  private String statusFilter;

  /** Page number for pagination */
  private Integer page;

  /** Network filter for crypto transactions */
  private String network;
}
