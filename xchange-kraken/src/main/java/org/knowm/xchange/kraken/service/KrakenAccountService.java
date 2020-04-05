package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.account.KrakenDepositAddress;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.account.KrakenTradeBalanceInfo;
import org.knowm.xchange.kraken.dto.account.LedgerType;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencies;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class KrakenAccountService extends KrakenAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    KrakenTradeBalanceInfo krakenTradeBalanceInfo = getKrakenTradeBalance();
    Wallet tradingWallet = KrakenAdapters.adaptWallet(getKrakenBalance());

    Wallet marginWallet =
        Wallet.Builder.from(tradingWallet.getBalances().values())
            .id("margin")
            .features(EnumSet.of(Wallet.WalletFeature.FUNDING, Wallet.WalletFeature.MARGIN_TRADING))
            .maxLeverage(BigDecimal.valueOf(5))
            .currentLeverage(
                (BigDecimal.ZERO.compareTo(krakenTradeBalanceInfo.getTradeBalance()) == 0)
                    ? BigDecimal.ZERO
                    : krakenTradeBalanceInfo
                        .getCostBasis()
                        .divide(krakenTradeBalanceInfo.getTradeBalance(), MathContext.DECIMAL32))
            .build();

    return new AccountInfo(
        exchange.getExchangeSpecification().getUserName(), tradingWallet, marginWallet);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdraw(null, currency.toString(), address, amount).getRefid();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }
    throw new IllegalStateException("Don't know how to withdraw: " + params);
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    KrakenDepositAddress[] depositAddresses;
    if (Currency.BTC.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Bitcoin");
    } else if (Currency.LTC.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Litecoin");
    } else if (Currency.ETH.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Ether (Hex)");
    } else if (Currency.ZEC.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Zcash (Transparent)");
    } else if (Currency.ADA.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "ADA");
    } else if (Currency.XMR.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Monero");
    } else if (Currency.XRP.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Ripple XRP");
    } else if (Currency.XLM.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Stellar XLM");
    } else if (Currency.BCH.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Bitcoin Cash");
    } else if (Currency.REP.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "REP");
    } else if (Currency.USD.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "SynapsePay (US Wire)");
    } else if (Currency.XDG.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Dogecoin");
    } else if (Currency.MLN.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "MLN");
    } else if (Currency.GNO.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "GNO");
    } else if (Currency.QTUM.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "QTUM");
    } else if (Currency.XTZ.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "XTZ");
    } else if (Currency.ATOM.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Cosmos");
    } else if (Currency.EOS.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "EOS");
    } else if (Currency.DASH.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Dash");
    } else {
      throw new RuntimeException("Not implemented yet, Kraken works only for BTC and LTC");
    }
    return KrakenAdapters.adaptKrakenDepositAddress(depositAddresses);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new KrakenFundingHistoryParams(null, null, null, (Currency[]) null);
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    Date startTime = null;
    Date endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
      startTime = timeSpanParam.getStartTime();
      endTime = timeSpanParam.getEndTime();
    }

    Long offset = null;
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    Currency[] currencies = null;
    if (params instanceof TradeHistoryParamCurrencies) {
      final TradeHistoryParamCurrencies currenciesParam = (TradeHistoryParamCurrencies) params;
      if (currenciesParam.getCurrencies() != null) {
        currencies = currenciesParam.getCurrencies();
      }
    }

    LedgerType ledgerType = null;
    if (params instanceof HistoryParamsFundingType) {
      final FundingRecord.Type type = ((HistoryParamsFundingType) params).getType();
      ledgerType =
          type == FundingRecord.Type.DEPOSIT
              ? LedgerType.DEPOSIT
              : type == FundingRecord.Type.WITHDRAWAL ? LedgerType.WITHDRAWAL : null;
    }

    if (ledgerType == null) {
      Map<String, KrakenLedger> ledgerEntries =
          getKrakenLedgerInfo(LedgerType.DEPOSIT, startTime, endTime, offset, currencies);
      ledgerEntries.putAll(
          getKrakenLedgerInfo(LedgerType.WITHDRAWAL, startTime, endTime, offset, currencies));
      return KrakenAdapters.adaptFundingHistory(ledgerEntries);
    } else {
      return KrakenAdapters.adaptFundingHistory(
          getKrakenLedgerInfo(ledgerType, startTime, endTime, offset, currencies));
    }
  }

  public static class KrakenFundingHistoryParams extends DefaultTradeHistoryParamsTimeSpan
      implements TradeHistoryParamOffset, TradeHistoryParamCurrencies, HistoryParamsFundingType {

    private Long offset;
    private Currency[] currencies;
    private FundingRecord.Type type;

    public KrakenFundingHistoryParams(
        final Date startTime, final Date endTime, final Long offset, final Currency... currencies) {
      super(startTime, endTime);
      this.offset = offset;
      this.currencies = currencies;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public void setOffset(final Long offset) {
      this.offset = offset;
    }

    @Override
    public Currency[] getCurrencies() {
      return this.currencies;
    }

    @Override
    public void setCurrencies(Currency[] currencies) {
      this.currencies = currencies;
    }

    @Override
    public FundingRecord.Type getType() {
      return type;
    }

    @Override
    public void setType(FundingRecord.Type type) {
      this.type = type;
    }
  }
}
