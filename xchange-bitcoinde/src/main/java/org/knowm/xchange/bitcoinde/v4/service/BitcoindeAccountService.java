package org.knowm.xchange.bitcoinde.v4.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedger;
import org.knowm.xchange.bitcoinde.v4.dto.account.BitcoindeAccountLedgerWrapper;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.utils.Assert;

public class BitcoindeAccountService extends BitcoindeAccountServiceRaw implements AccountService {

  public BitcoindeAccountService(BitcoindeExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return BitcoindeAdapters.adaptAccountInfo(getBitcoindeAccount());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BitcoindeFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Assert.isTrue(
        params instanceof TradeHistoryParamCurrency,
        "You need to provide a currency to retrieve the funding history.");

    if (((TradeHistoryParamCurrency) params).getCurrency() == null) {
      throw new IllegalArgumentException("Currency has to be specified");
    }

    if (params instanceof BitcoindeFundingHistoryParams
        && ((BitcoindeFundingHistoryParams) params).getType() != null
        && ((BitcoindeFundingHistoryParams) params).getCustomType() != null) {
      throw new IllegalArgumentException("Only one type can be specified");
    }

    Currency currency = ((TradeHistoryParamCurrency) params).getCurrency();
    BitcoindeAccountLedgerType customType = null;
    boolean leaveFeesSeperate = false;
    Date start = null;
    Date end = null;
    Integer pageNumber = null;

    if (params instanceof HistoryParamsFundingType) {
      if (((HistoryParamsFundingType) params).getType() != null) {
        switch (((HistoryParamsFundingType) params).getType()) {
          case WITHDRAWAL:
            customType = BitcoindeAccountLedgerType.PAYOUT;
            break;
          case DEPOSIT:
            customType = BitcoindeAccountLedgerType.INPAYMENT;
            break;
          default:
            throw new IllegalArgumentException(
                "Unsupported FundingRecord.Type: "
                    + ((HistoryParamsFundingType) params).getType()
                    + ". For Bitcoin.de specific types use BitcoindeFundingHistoryParams#customType");
        }
      }
    }

    if (params instanceof BitcoindeFundingHistoryParams) {
      customType = ((BitcoindeFundingHistoryParams) params).getCustomType();
      leaveFeesSeperate = ((BitcoindeFundingHistoryParams) params).isLeaveFeesSeperate();
    }

    if (params instanceof TradeHistoryParamsTimeSpan) {
      start = ((TradeHistoryParamsTimeSpan) params).getStartTime();
      end = ((TradeHistoryParamsTimeSpan) params).getEndTime();
    }

    if (params instanceof TradeHistoryParamPaging) {
      pageNumber = ((TradeHistoryParamPaging) params).getPageNumber();
    }

    BitcoindeAccountLedgerWrapper result =
        getAccountLedger(currency, customType, start, end, pageNumber);

    List<BitcoindeAccountLedger> ledger = result.getAccountLedgers();
    if (!leaveFeesSeperate && !ledger.isEmpty()) {
      ledger.addAll(
          checkForAndQueryAdditionalFees(ledger, currency, customType, start, end, pageNumber));
    }

    // Report back paging information to user to enable efficient paging
    if (params instanceof BitcoindeFundingHistoryParams) {
      ((BitcoindeFundingHistoryParams) params).setPageNumber(result.getPage().getCurrent());
      ((BitcoindeFundingHistoryParams) params).setLastPageNumber(result.getPage().getLast());
    }

    return BitcoindeAdapters.adaptFundingHistory(currency, ledger, leaveFeesSeperate);
  }

  private List<BitcoindeAccountLedger> checkForAndQueryAdditionalFees(
      final List<BitcoindeAccountLedger> ledgers,
      final Currency currency,
      final BitcoindeAccountLedgerType customType,
      final Date start,
      final Date end,
      final Integer page)
      throws IOException {

    /*
        Only PAYOUTs can have fees, so there is not need for additional checking if there
        are no PAYOUTs in the ledger list
    */
    if (customType != null
        && BitcoindeAccountLedgerType.PAYOUT != customType
        && BitcoindeAccountLedgerType.ALL != customType) {
      return Collections.emptyList();
    }

    // If only PAYOUTs were requested, this get the same "page" of OUTGOING_FEE_VOLUNTARYs
    if (BitcoindeAccountLedgerType.PAYOUT == customType) {
      return getAccountLedger(
              currency, BitcoindeAccountLedgerType.OUTGOING_FEE_VOLUNTARY, start, end, page)
          .getAccountLedgers();
    }

    /*
        PAYOUTs and OUTGOING_FEE_VOLUNTARYs always have the same reference and date.
        If the ledger list ends on an uneven ratio between PAYOUTs and OUTGOING_FEE_VOLUNTARYs
        then it's missing OUTGOING_FEE_VOLUNTARYs.
        In that case the following page is requested and filtered for the starting
        OUTGOING_FEE_VOLUNTARY ledger entries.
    */
    int feeCount = 0;
    for (int i = ledgers.size() - 1; i >= 0; i--) {
      if (BitcoindeAccountLedgerType.OUTGOING_FEE_VOLUNTARY == ledgers.get(i).getType()) {
        feeCount++;
      } else if (BitcoindeAccountLedgerType.PAYOUT == ledgers.get(i).getType()) {
        feeCount--;
      } else {
        break;
      }
    }

    if (feeCount < 0) {
      List<BitcoindeAccountLedger> feeLedgers = new ArrayList<>();
      List<BitcoindeAccountLedger> feeResult =
          getAccountLedger(currency, customType, start, end, page + 1).getAccountLedgers();

      for (BitcoindeAccountLedger ledger : feeResult) {
        if (BitcoindeAccountLedgerType.OUTGOING_FEE_VOLUNTARY == ledger.getType()) {
          feeLedgers.add(ledger);
        } else {
          break;
        }
      }

      return feeLedgers;
    }

    return Collections.emptyList();
  }
}
