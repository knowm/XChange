package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dragonex.DragonexAdapters;
import org.knowm.xchange.dragonex.dto.account.Balance;
import org.knowm.xchange.dragonex.dto.account.Withdrawal;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class DragonexAccountService extends DragonexAccountServiceRaw implements AccountService {

  public DragonexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Balance> userCoins = userCoins(exchange.getOrCreateToken().token);
    List<org.knowm.xchange.dto.account.Balance> balances =
        userCoins.stream()
            .map(
                b ->
                    new org.knowm.xchange.dto.account.Balance(
                        Currency.getInstance(b.code.toUpperCase()),
                        b.volume,
                        b.volume.subtract(b.frozen),
                        b.frozen))
            .collect(Collectors.toList());
    return new AccountInfo(Wallet.Builder.from(balances).build());
  }

  public TradeHistoryParams createFundingHistoryParams() {
    return new DragonexTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    if (!(params instanceof TradeHistoryParamCurrency)) {
      throw new RuntimeException(
          "Parameter must implement the TradeHistoryParamCurrency interface in order to provide the currency parameter.");
    }
    Currency c = ((TradeHistoryParamCurrency) params).getCurrency();
    if (c == null) {
      throw new RuntimeException("Parameter must provide a valid currency parameter.");
    }
    String currency = c.getCurrencyCode();

    Long pageNum = null;
    Long pageSize = null;
    if (params instanceof TradeHistoryParamPaging) {
      TradeHistoryParamPaging p = (TradeHistoryParamPaging) params;
      if (p.getPageNumber() != null) {
        pageNum = new Long(p.getPageNumber());
      }
      if (p.getPageLength() != null) {
        pageSize = new Long(p.getPageLength());
      }
    }

    boolean withdrawals = true, deposits = true;
    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType p = (HistoryParamsFundingType) params;
      withdrawals = p.getType() == null || p.getType() == Type.WITHDRAWAL;
      deposits = p.getType() == null || p.getType() == Type.DEPOSIT;
    }

    List<FundingRecord> result = new ArrayList<>();
    if (withdrawals) {
      result.addAll(
          coinWithdrawHistory(exchange.getCoinId(currency), pageNum, pageSize).getList().stream()
              .map(cph -> DragonexAdapters.adaptFundingRecord(cph, exchange::getCurrency))
              .collect(Collectors.toList()));
    }
    if (deposits) {
      result.addAll(
          coinPrepayHistory(exchange.getCoinId(currency), pageNum, pageSize).getList().stream()
              .map(cph -> DragonexAdapters.adaptFundingRecord(cph, exchange::getCurrency))
              .collect(Collectors.toList()));
    }
    return result;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    // here the address must be the internal dragonex withdrawal_address_id
    long addressId;
    try {
      addressId = Long.valueOf(address);
    } catch (NumberFormatException e) {
      throw new RuntimeException(
          "Invalid address: "
              + address
              + ", it must be the internal dragonex withdrawal_address_id of type int");
    }
    Withdrawal w =
        coinWithdrawNew(exchange.getCoinId(currency.getCurrencyCode()), amount, addressId);
    return Long.toString(w.withdrawId);
  }
}
