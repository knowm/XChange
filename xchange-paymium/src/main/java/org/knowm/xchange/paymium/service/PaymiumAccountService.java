package org.knowm.xchange.paymium.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.paymium.PaymiumAdapters;
import org.knowm.xchange.paymium.dto.account.PaymiumOrder;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class PaymiumAccountService extends PaymiumAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public PaymiumAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new PaymiumHistoryParams();
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo(PaymiumAdapters.adaptWallet(getPaymiumBalances()));
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    List<FundingRecord> res = new ArrayList<>();

    Long offset = null;
    Integer limit = null;

    if (params instanceof TradeHistoryParamOffset) {
      final TradeHistoryParamOffset historyParamOffset = (TradeHistoryParamOffset) params;
      offset = historyParamOffset.getOffset();
    }

    if (params instanceof TradeHistoryParamLimit) {
      final TradeHistoryParamLimit historyParamLimit = (TradeHistoryParamLimit) params;
      limit = historyParamLimit.getLimit();
    }

    List<PaymiumOrder> orders = getPaymiumFundingOrders(offset, limit);

    for (PaymiumOrder order : orders) {

      FundingRecord.Type funding = null;

      switch (order.getType()) {
        case "WireDeposit":
        case "BitcoinDeposit":
          funding = funding.DEPOSIT;
          break;
        case "Transfer":
          funding = funding.WITHDRAWAL;
          break;
      }

      res.add(
          new FundingRecord(
              order.getBitcoinAddress(),
              order.getUpdatedAt(),
              Currency.getInstance(order.getCurrency()),
              order.getAmount(),
              String.valueOf(order.getUuid()),
              order.getUuid(),
              funding,
              FundingRecord.Status.COMPLETE,
              null,
              null,
              null));
    }

    return res;
  }
}
