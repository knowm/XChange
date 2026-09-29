package org.knowm.xchange.cryptocom.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositAddress;
import org.knowm.xchange.cryptocom.dto.account.CryptoComWithdrawalRecord;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.DepositAddressAmbiguousException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.NetworkWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class CryptoComAccountService extends CryptoComAccountServiceRaw implements AccountService {

  public CryptoComAccountService(
      CryptoComExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return CryptoComAdapters.adaptAccountInfo(getCryptoComBalances());
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (!(params instanceof DefaultWithdrawFundsParams)) {
      throw new NotAvailableFromExchangeException(
          "Crypto.com requires currency, amount and address: use DefaultWithdrawFundsParams"
              + " (or NetworkWithdrawFundsParams to specify a network)");
    }
    DefaultWithdrawFundsParams withdrawParams = (DefaultWithdrawFundsParams) params;
    // network_id is optional in the raw API call, so a plain DefaultWithdrawFundsParams (no
    // network) is accepted too; only NetworkWithdrawFundsParams carries a network to send.
    String network =
        params instanceof NetworkWithdrawFundsParams
            ? ((NetworkWithdrawFundsParams) params).getNetwork()
            : null;
    CryptoComWithdrawalRecord record =
        createCryptoComWithdrawal(
            withdrawParams.getCurrency().getCurrencyCode(),
            withdrawParams.getAmount().toPlainString(),
            withdrawParams.getAddress(),
            network,
            withdrawParams.getAddressTag(),
            null);
    return record.getId();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    List<CryptoComDepositAddress> addresses =
        getCryptoComDepositAddresses(currency.getCurrencyCode());
    if (addresses.isEmpty()) {
      throw new NotAvailableFromExchangeException("No deposit address found for " + currency);
    }
    if (addresses.size() == 1) {
      return addresses.get(0).getAddress();
    }
    String network = args.length > 0 ? args[0] : null;
    if (network == null) {
      throw new DepositAddressAmbiguousException(
          addresses.stream().map(CryptoComDepositAddress::getNetworkId).collect(Collectors.toList()),
          "Crypto.com has multiple deposit addresses for "
              + currency
              + "; specify the network id as the first argument");
    }
    return addresses.stream()
        .filter(a -> network.equals(a.getNetworkId()))
        .findFirst()
        .orElseThrow(
            () ->
                new NotAvailableFromExchangeException(
                    "No deposit address found for " + currency + " on network " + network))
        .getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CryptoComFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    Currency currencyParam =
        params instanceof TradeHistoryParamCurrency
            ? ((TradeHistoryParamCurrency) params).getCurrency()
            : null;
    String currency = currencyParam == null ? null : currencyParam.getCurrencyCode();
    Long startTime = null;
    Long endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
      startTime = CryptoComAdapters.toEpochMillis(timeSpan.getStartTime());
      endTime = CryptoComAdapters.toEpochMillis(timeSpan.getEndTime());
    }

    List<FundingRecord> records = new ArrayList<>();
    records.addAll(
        CryptoComAdapters.adaptDepositRecords(
            getCryptoComDepositHistory(currency, startTime, endTime)));
    records.addAll(
        CryptoComAdapters.adaptWithdrawalRecords(
            getCryptoComWithdrawalHistory(currency, startTime, endTime)));
    return records;
  }
}
