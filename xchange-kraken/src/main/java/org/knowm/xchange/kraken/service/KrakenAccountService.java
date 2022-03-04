package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.account.*;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencies;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenAccountService extends KrakenAccountServiceRaw implements AccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger(KrakenAccountService.class);

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
  public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    return KrakenAdapters.adaptFees(
        super.getTradeVolume(
            exchange
                .getExchangeMetaData()
                .getCurrencyPairs()
                .keySet()
                .toArray(new CurrencyPair[0])));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdraw(null, KrakenUtils.getKrakenCurrencyCode(currency), address, amount).getRefid();
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

  private String getMethod(Currency currency, String protocol) throws IOException {
    KrakenDepositMethods[] depositMethods = getDepositMethods(null, currency.toString());

    if(depositMethods == null || depositMethods.length <= 0) {
      return null;
    }

    if(depositMethods.length > 1 && protocol == null) {
      return null; // we don't know what to do
    } else if(protocol != null) {
      return protocol;
    }

    return depositMethods[0].getMethod();
  }

  private KrakenDepositAddress[] requestDepositAddress(boolean newAddress, Currency currency, String protocol) throws IOException {
    KrakenDepositAddress[] depositAddresses = null;

    String method = getMethod(currency, protocol);

    if(method != null) {
      depositAddresses = getDepositAddresses(currency.toString(), method, newAddress);
    } else if(protocol != null) {
      depositAddresses = getDepositAddresses(currency.toString(), protocol, newAddress);
    }else if (Currency.BTC.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Bitcoin", newAddress);
    } else if (Currency.LTC.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Litecoin", newAddress);
    } else if (Currency.ETH.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Ether (Hex)", newAddress);
    } else if (Currency.ZEC.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Zcash (Transparent)", newAddress);
    } else if (Currency.ADA.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "ADA", newAddress);
    } else if (Currency.XMR.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Monero", newAddress);
    } else if (Currency.XRP.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Ripple XRP", newAddress);
    } else if (Currency.XLM.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Stellar XLM", newAddress);
    } else if (Currency.BCH.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Bitcoin Cash", newAddress);
    } else if (Currency.REP.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "REP", newAddress);
    } else if (Currency.USD.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "SynapsePay (US Wire)", newAddress);
    } else if (Currency.XDG.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Dogecoin", newAddress);
    } else if (Currency.MLN.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "MLN", newAddress);
    } else if (Currency.GNO.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "GNO", newAddress);
    } else if (Currency.QTUM.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "QTUM", newAddress);
    } else if (Currency.XTZ.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "XTZ", newAddress);
    } else if (Currency.ATOM.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Cosmos", newAddress);
    } else if (Currency.EOS.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "EOS", newAddress);
    } else if (Currency.DASH.equals(currency)) {
      depositAddresses = getDepositAddresses(currency.toString(), "Dash", newAddress);
    }

    return depositAddresses;
  }

  KrakenDepositAddress[] getDepositAddresses(Currency currency, String ...args) throws IOException {
    String protocol = null;

    if(args != null && args.length > 0) {
      protocol = args[0];
    }

    KrakenDepositAddress[] depositAddresses = requestDepositAddress(false, currency, protocol);
    if(depositAddresses == null || depositAddresses.length <= 0) {
      depositAddresses = requestDepositAddress(true, currency, protocol);
    }

    if(depositAddresses == null || depositAddresses.length <= 0) {
      throw new IOException("Unable to create wallet currency: "+currency+" protocol: "+protocol);
    }

    return depositAddresses;
  }

  @Override
  public AddressWithTag requestDepositAddressData(Currency currency, String...args) throws IOException {
    KrakenDepositAddress[] depositAddresses = getDepositAddresses(currency, args);
    return new AddressWithTag(depositAddresses[0].getAddress(), depositAddresses[0].getTag());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    KrakenDepositAddress[] depositAddresses = getDepositAddresses(currency, args);
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
