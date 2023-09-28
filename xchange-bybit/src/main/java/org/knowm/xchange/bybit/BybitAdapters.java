package org.knowm.xchange.bybit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitDepositRecordsResponse.BybitDepositRecord;
import org.knowm.xchange.bybit.dto.account.BybitInternalDepositRecordsResponse.BybitInternalDepositRecord;
import org.knowm.xchange.bybit.dto.account.BybitTransactionLogResponse.BybitTransactionLog;
import org.knowm.xchange.bybit.dto.account.BybitTransactionLogResponse.BybitTransactionLog.BybitTransactionLogType;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse.BybitTransfer;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse.BybitTransferStatus;
import org.knowm.xchange.bybit.dto.account.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.BybitAllCoinsBalance.BybitCoinBalance;
import org.knowm.xchange.bybit.dto.account.BybitWithdrawRecordsResponse.BybitWithdrawRecord;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo.InstrumentStatus;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.option.BybitOptionTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.spot.BybitSpotTicker;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.BybitTradeHistoryResponse;
import org.knowm.xchange.bybit.dto.trade.BybitUserTradeDto;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.derivative.OptionsContract.OptionType;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class BybitAdapters {

  protected static final List<String> QUOTE_CURRENCIES =
      Arrays.asList("USDT", "USDC", "BTC", "DAI", "EUR", "ETH");

  public static final String FUTURES_CONTRACT_QUOTE_CURRENCY = "USD";

  private static final String BYBIT_PERPETUAL = "PERP";

  public static final SimpleDateFormat OPTIONS_EXPIRED_DATE_PARSER = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);

  public static Wallet adaptBybitBalances(BybitAllCoinsBalance allCoinsBalance, Set<WalletFeature> features) {
    List<Balance> balances = new ArrayList<>(allCoinsBalance.getBalance().size());
    for (BybitCoinBalance coinBalance : allCoinsBalance.getBalance()) {
      balances.add(
          new Balance(
              new Currency(coinBalance.getCoin()),
              coinBalance.getWalletBalance(),
              coinBalance.getTransferBalance()));
    }

    return Wallet.Builder.from(balances)
        .id(allCoinsBalance.getAccountType().name())
        .features(features)
        .build();
  }

  public static BybitSide getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK) {
      return BybitSide.SELL;
    }
    if (type == Order.OrderType.BID) {
      return BybitSide.BUY;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static Order.OrderType getOrderType(BybitSide side) {
    if ("sell".equalsIgnoreCase(side.name())) {
      return Order.OrderType.ASK;
    }
    if ("buy".equalsIgnoreCase(side.name())) {
      return Order.OrderType.BID;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static String convertToBybitSymbol(String instrumentName) {
    return instrumentName.replace("/", "").toUpperCase();
  }

  public static CurrencyPair guessSymbol(String symbol) {
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
      }
    }
    int splitIndex = symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
  }

  public static LimitOrder adaptBybitOrderDetails(BybitOrderDetail bybitOrderResult) {
    LimitOrder limitOrder =
        new LimitOrder(
            getOrderType(bybitOrderResult.getSide()),
            bybitOrderResult.getQty(),
            bybitOrderResult.getCumExecQty(),
            guessSymbol(bybitOrderResult.getSymbol()),
            bybitOrderResult.getOrderId(),
            bybitOrderResult.getCreatedTime(),
            bybitOrderResult.getPrice()) {};
    limitOrder.setAveragePrice(bybitOrderResult.getAvgPrice());
    limitOrder.setOrderStatus(adaptBybitOrderStatus(bybitOrderResult.getOrderStatus()));
    return limitOrder;
  }

  private static OrderStatus adaptBybitOrderStatus(BybitOrderStatus orderStatus) {
    switch (orderStatus) {
      case CREATED:
        return OrderStatus.OPEN;
      case NEW:
        return OrderStatus.NEW;
      case REJECTED:
        return OrderStatus.REJECTED;
      case PARTIALLY_FILLED:
      case ACTIVE:
        return OrderStatus.PARTIALLY_FILLED;
      case PARTIALLY_FILLED_CANCELED:
        return OrderStatus.PARTIALLY_CANCELED;
      case FILLED:
        return OrderStatus.FILLED;
      case CANCELLED:
        return OrderStatus.CANCELED;
      case UNTRIGGERED:
      case TRIGGERED:
        return OrderStatus.UNKNOWN;
      case DEACTIVATED:
        return OrderStatus.STOPPED;
      default:
        throw new IllegalStateException("Unexpected value: " + orderStatus);
    }
  }

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> walletBalances) {
    return new BybitException(
        walletBalances.getRetCode(), walletBalances.getRetMsg(), walletBalances.getRetExtInfo());
  }

  public static Ticker adaptBybitLinearInverseTicker(
      Instrument instrument, Date time, BybitLinearInverseTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker)
        .open(bybitTicker.getPrevPrice24h())
        .percentageChange(bybitTicker.getPrice24hPcnt())
        .build();
  }

  public static Ticker adaptBybitSpotTicker(
      Instrument instrument, Date time, BybitSpotTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker)
        .open(bybitTicker.getPrevPrice24h())
        .percentageChange(bybitTicker.getPrice24hPcnt())
        .build();
  }

  public static Ticker adaptBybitOptionTicker(
      Instrument instrument, Date time, BybitOptionTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker).build();
  }

  private static Builder adaptBybitTickerBuilder(
      Instrument instrument, Date time, BybitTicker bybitTicker) {
    return new Ticker.Builder()
        .timestamp(time)
        .instrument(instrument)
        .last(bybitTicker.getLastPrice())
        .bid(bybitTicker.getBid1Price())
        .bidSize(bybitTicker.getBid1Size())
        .ask(bybitTicker.getAsk1Price())
        .askSize(bybitTicker.getAsk1Size())
        .high(bybitTicker.getHighPrice24h())
        .low(bybitTicker.getLowPrice24h())
        .quoteVolume(bybitTicker.getTurnover24h())
        .volume(bybitTicker.getVolume24h());
  }

  public static Map<Instrument, InstrumentMetaData> adaptBybitInstruments(
      List<BybitInstrumentInfo> instrumentList) {
    Map<Instrument, InstrumentMetaData> map = new HashMap<>();

    instrumentList.forEach(
        info -> {
          if (info instanceof BybitSpotInstrumentInfo) {
            BybitSpotInstrumentInfo spotInstrumentInfo = (BybitSpotInstrumentInfo) info;
            map.put(
                adaptInstrument(spotInstrumentInfo.getSymbol(), BybitCategory.SPOT),
                new InstrumentMetaData.Builder()
                    .minimumAmount(spotInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .maximumAmount(spotInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .counterMinimumAmount(spotInstrumentInfo.getLotSizeFilter().getMinOrderAmt())
                    .counterMaximumAmount(spotInstrumentInfo.getLotSizeFilter().getMaxOrderAmt())
                    .priceScale(spotInstrumentInfo.getPriceFilter().getTickSize().scale())
                    .volumeScale(spotInstrumentInfo.getLotSizeFilter().getBasePrecision().scale())
                    .amountStepSize(spotInstrumentInfo.getLotSizeFilter().getBasePrecision())
                    .priceStepSize(spotInstrumentInfo.getPriceFilter().getTickSize())
                    .marketOrderEnabled(
                        spotInstrumentInfo.getStatus().equals(InstrumentStatus.TRADING))
                    .build());
          } else if (info instanceof BybitLinearInverseInstrumentInfo) {
            BybitLinearInverseInstrumentInfo perpetualInstrumentInfo =
                (BybitLinearInverseInstrumentInfo) info;
            map.put(
                adaptInstrument(perpetualInstrumentInfo.getSymbol(), BybitCategory.LINEAR),
                new InstrumentMetaData.Builder()
                    .minimumAmount(perpetualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .maximumAmount(perpetualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .counterMinimumAmount(
                        perpetualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .counterMaximumAmount(
                        perpetualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .priceScale(perpetualInstrumentInfo.getPriceScale())
                    .volumeScale(perpetualInstrumentInfo.getLotSizeFilter().getQtyStep().scale())
                    .amountStepSize(perpetualInstrumentInfo.getLotSizeFilter().getQtyStep())
                    .priceStepSize(perpetualInstrumentInfo.getPriceFilter().getTickSize())
                    .marketOrderEnabled(
                        perpetualInstrumentInfo.getStatus().equals(InstrumentStatus.TRADING))
                    .build());
          } else if (info instanceof BybitOptionInstrumentInfo) {
            BybitOptionInstrumentInfo optionsInstrumentInfo = (BybitOptionInstrumentInfo) info;
            map.put(
                adaptInstrument(optionsInstrumentInfo.getSymbol(), BybitCategory.OPTION),
                new InstrumentMetaData.Builder()
                    .minimumAmount(optionsInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .maximumAmount(optionsInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .counterMinimumAmount(optionsInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .counterMaximumAmount(optionsInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .priceScale(optionsInstrumentInfo.getPriceFilter().getTickSize().scale())
                    .volumeScale(optionsInstrumentInfo.getLotSizeFilter().getQtyStep().scale())
                    .amountStepSize(optionsInstrumentInfo.getLotSizeFilter().getQtyStep())
                    .priceStepSize(optionsInstrumentInfo.getPriceFilter().getTickSize())
                    .marketOrderEnabled(optionsInstrumentInfo.getStatus().equals(InstrumentStatus.TRADING))
                    .build());
          }
        });
    return map;
  }

  public static OrderType adaptSide(BybitSide side) {
    return (side.equals(BybitSide.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static String getBybitQuoteCurrency(String symbol) {
    String quoteCurrency = FUTURES_CONTRACT_QUOTE_CURRENCY;

    for (String quote : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quote)) {
        quoteCurrency = quote;
        break;
      }
    }

    return quoteCurrency;
  }

  public static Currency getFeeCurrency(
      boolean isMaker, BigDecimal feeRate, Instrument instrument, BybitSide side) {
    if (instrument instanceof CurrencyPair) {
      if (isMaker && feeRate.compareTo(BigDecimal.ZERO) > 0) {
        return (side.equals(BybitSide.BUY)
            ? ((CurrencyPair) instrument).base
            : ((CurrencyPair) instrument).counter);
      } else {
        if (isMaker) {
          return (side.equals(BybitSide.BUY)
              ? ((CurrencyPair) instrument).counter
              : ((CurrencyPair) instrument).base);
        } else {
          return (side.equals(BybitSide.BUY)
              ? ((CurrencyPair) instrument).base
              : ((CurrencyPair) instrument).counter);
        }
      }
    } else {
      return instrument.getCounter();
    }
  }

  @SneakyThrows
  public static Instrument adaptInstrument(String symbol, BybitCategory category) {
    Instrument instrument = null;

    String quoteCurrency = getBybitQuoteCurrency(symbol);

    if (category.equals(BybitCategory.SPOT)) {
      String baseCurrency = symbol.substring(0, symbol.length() - quoteCurrency.length());

      instrument = new CurrencyPair(baseCurrency, quoteCurrency);
    } else if (category.equals(BybitCategory.LINEAR) || category.equals(BybitCategory.INVERSE)) {
      instrument =
          (symbol.contains("-"))
              ? new FuturesContract(new CurrencyPair(symbol.substring(0, symbol.indexOf("-")), quoteCurrency), symbol.substring(symbol.indexOf("-") + 1))
              : new FuturesContract(new CurrencyPair(symbol.substring(0, symbol.length() - quoteCurrency.length()), quoteCurrency), BYBIT_PERPETUAL);
    } else if (category.equals(BybitCategory.OPTION)) {
      int secondIndex = symbol.indexOf("-", symbol.indexOf("-") + 1); // second index of "-" after the first one
      instrument =
          new OptionsContract.Builder()
              .currencyPair(new CurrencyPair(symbol.substring(0, symbol.indexOf("-")), quoteCurrency))
              .expireDate(OPTIONS_EXPIRED_DATE_PARSER.parse(symbol.substring(symbol.indexOf("-") + 1, secondIndex)))
              .strike(new BigDecimal(symbol.substring(secondIndex + 1, symbol.lastIndexOf("-"))))
              .type(symbol.contains("C") ? OptionType.CALL : OptionType.PUT)
              .build();
    }

    return instrument;
  }

  public static BybitCategory getBybitCategoryFromInstrument(Instrument instrument) {
    int count = StringUtils.countMatches(instrument.toString(), "/");
    if(count == 1){
      return BybitCategory.SPOT;
    } else if(count == 4){
      return BybitCategory.OPTION;
    } else if(instrument.getCounter().equals(Currency.USDC) || instrument.getCounter().equals(Currency.USDT)){
      return BybitCategory.LINEAR;
    } else {
      return BybitCategory.INVERSE;
    }
  }

  public static List<UserTrade> adaptUserTrades(BybitTradeHistoryResponse result) {
    List<UserTrade> userTrades = new ArrayList<>();

    result.getTradeHistoryList().forEach(bybitUserTradeDto -> userTrades.add(adaptUserTrade(bybitUserTradeDto, result.getCategory())));

    return userTrades;
  }

  public static UserTrade adaptUserTrade(BybitUserTradeDto bybitUserTradeDto, BybitCategory bybitCategory) {
    Instrument instrument = BybitAdapters.adaptInstrument(bybitUserTradeDto.getSymbol(), bybitCategory);
    return new UserTrade.Builder()
        .instrument(instrument)
        .feeAmount(bybitUserTradeDto.getExecFee())
        .type(BybitAdapters.adaptSide(bybitUserTradeDto.getSide()))
        .orderUserReference(bybitUserTradeDto.getOrderLinkId())
        .id(bybitUserTradeDto.getExecId())
        .orderId(bybitUserTradeDto.getOrderId())
        .originalAmount(bybitUserTradeDto.getExecQty())
        .price(bybitUserTradeDto.getExecPrice())
        .timestamp(bybitUserTradeDto.getExecTime())
        .feeCurrency((instrument == null) ? null : BybitAdapters.getFeeCurrency(bybitUserTradeDto.getIsMaker(), bybitUserTradeDto.getFeeRate(), instrument , bybitUserTradeDto.getSide()))
        .build();
  }

  public static String adaptBybitSymbol(Instrument instrument) {
    if(instrument instanceof CurrencyPair){
      return instrument.toString().replace("/","");
    } else if(instrument instanceof OptionsContract){
      return instrument.toString().replace("/","-");
    } else if(instrument.toString().contains(BYBIT_PERPETUAL)){
      return instrument.toString().replace("/","").replace(BYBIT_PERPETUAL,"");
    } else {
      return instrument.toString().replace("/","-");
    }
  }

  public static Map<Currency, CurrencyMetaData> adaptBybitCurrencies(List<BybitInstrumentInfo> list) {
    Map<Currency, CurrencyMetaData> currencyCurrencyMetaDataMap = new HashMap<>();

    list.forEach(bybitInstrumentInfo -> {
      BybitSpotInstrumentInfo spotInfo = (BybitSpotInstrumentInfo) bybitInstrumentInfo;

      if(!currencyCurrencyMetaDataMap.containsKey(new Currency(spotInfo.getBaseCoin()))){
        currencyCurrencyMetaDataMap.put(
            new Currency(spotInfo.getBaseCoin()),
            CurrencyMetaData.builder()
                .scale(spotInfo.getLotSizeFilter().getBasePrecision().scale())
                .build());
      }
      if(!currencyCurrencyMetaDataMap.containsKey(new Currency(spotInfo.getQuoteCoin()))) {
        currencyCurrencyMetaDataMap.put(
            new Currency(spotInfo.getQuoteCoin()),
            CurrencyMetaData.builder()
                .scale(spotInfo.getLotSizeFilter().getQuotePrecision().scale())
                .build());
      }
    });

    return currencyCurrencyMetaDataMap;
  }

  public static List<FundingRecord> adaptBybitInternalTransfers(List<BybitTransfer> internalTransfers) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    internalTransfers.forEach(internalTransfer -> fundingRecords.add(FundingRecord.builder()
            .internalId(internalTransfer.getTransferId())
            .currency(new Currency(internalTransfer.getCoin()))
            .amount(internalTransfer.getAmount())
            .date(internalTransfer.getTimestamp())
            .type(Type.INTERNAL_WALLET_TRANSFER)
            .status(Status.resolveStatus(internalTransfer.getStatus().name()))
            .fromWallet(internalTransfer.getFromAccountType().name())
            .toWallet(internalTransfer.getToAccountType().name())
            .description(internalTransfer.getFromAccountType().name()+"->"+internalTransfer.getToAccountType().name())
        .build()));
    return fundingRecords;
  }

  public static List<FundingRecord> adaptBybitWithdrawRecords(List<BybitWithdrawRecord> withdrawRecords) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    withdrawRecords.forEach(withdrawRecord -> fundingRecords.add(FundingRecord.builder()
        .internalId(withdrawRecord.getWithdrawId())
        .blockchainTransactionHash(withdrawRecord.getTxID())
        .addressTag(withdrawRecord.getTag())
        .address(withdrawRecord.getToAddress())
        .currency(new Currency(withdrawRecord.getCoin()))
        .type(withdrawRecord.getWithdrawType() == 0 ? Type.WITHDRAWAL: Type.INTERNAL_WITHDRAWAL)
        .amount(withdrawRecord.getAmount())
        .date(withdrawRecord.getCreateTime())
        .status(Status.resolveStatus(withdrawRecord.getStatus().name()))
        .fee(withdrawRecord.getWithdrawFee())
        .description(withdrawRecord.getChain())
        .build()));
    return fundingRecords;
  }

  public static List<FundingRecord> adaptBybitUniversalTransfers(List<BybitTransfer> universalTransfers) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    universalTransfers.forEach(universalTransfer -> fundingRecords.add(FundingRecord.builder()
        .internalId(universalTransfer.getTransferId())
        .currency(Currency.getInstance(universalTransfer.getCoin()))
        .amount(universalTransfer.getAmount())
        .date(universalTransfer.getTimestamp())
        .type(Type.INTERNAL_SUB_ACCOUNT_TRANSFER)
        .status(Status.resolveStatus(universalTransfer.getStatus().name()))
        .toSubAccount(universalTransfer.getToMember())
        .fromSubAccount(universalTransfer.getFromMember())
        .toWallet(universalTransfer.getToAccountType().name())
        .fromWallet(universalTransfer.getFromAccountType().name())
        .description(universalTransfer.getFromMember()+"."+universalTransfer.getFromAccountType().name()+"->"+universalTransfer.getToMember()+"."+universalTransfer.getToAccountType().name())
        .build()));

    return fundingRecords;
  }


  public static List<FundingRecord> adaptBybitDepositRecords(List<BybitDepositRecord> bybitDepositRecords) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    bybitDepositRecords.forEach(depositRecord -> fundingRecords.add(FundingRecord.builder()
        .internalId(depositRecord.getTxID())
        .addressTag(depositRecord.getTag())
        .address((depositRecord.getToAddress() == null) ? "" : depositRecord.getToAddress())
        .type(Type.DEPOSIT)
        .fee((depositRecord.getDepositFee() == null) ? BigDecimal.ZERO : depositRecord.getDepositFee())
        .blockchainTransactionHash(depositRecord.getBlockHash())
        .currency(Currency.getInstance(depositRecord.getCoin()))
        .amount(depositRecord.getAmount())
        .date(depositRecord.getSuccessAt())
        .status(Status.resolveStatus(depositRecord.getStatus().name()))
        .description(depositRecord.getDepositType().name())
        .build()));

    return fundingRecords;
  }

  public static List<FundingRecord> adaptBybitInternalDepositRecords(List<BybitInternalDepositRecord> bybitInternalDepositRecords) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    bybitInternalDepositRecords.forEach(internalRecord -> fundingRecords.add(FundingRecord.builder()
        .internalId(internalRecord.getId())
        .address(internalRecord.getAddress())
        .type(Type.INTERNAL_DEPOSIT)
        .currency(Currency.getInstance(internalRecord.getCoin()))
        .amount(internalRecord.getAmount())
        .date(internalRecord.getCreatedTime())
        .status(Status.resolveStatus(internalRecord.getStatus().name()))
        .build()));

    return fundingRecords;
  }

  public static BybitTransferStatus convertToBybitStatus(FundingRecord.Status status) {
    BybitTransferStatus bybitStatus = null;

    if(status != null){
      switch (status){
        case CANCELLED:
        case FAILED:
          bybitStatus = BybitTransferStatus.FAILED;
          break;
        case COMPLETE:
          bybitStatus = BybitTransferStatus.SUCCESS;
          break;
        case PROCESSING:
          bybitStatus = BybitTransferStatus.PENDING;
          break;
        default:
          break;
      }
    }

    return bybitStatus;
  }

  public static List<FundingRecord> adaptBybitLedger(List<BybitTransactionLog> list) {
    List<FundingRecord> fundingRecords = new ArrayList<>();

    list.forEach(
        bybitTransactionLog -> fundingRecords.add(
            FundingRecord.builder()
                .currency(Currency.getInstance(bybitTransactionLog.getCurrency()))
                .balance(bybitTransactionLog.getCashBalance())
                .internalId(bybitTransactionLog.getId())
                .fee(bybitTransactionLog.getFee())
                .amount(bybitTransactionLog.getChange())
                .type(convertToFundingRecordType(bybitTransactionLog.getType()))
                .status(Status.COMPLETE)
                .date(bybitTransactionLog.getTransactionTime())
                .build()));

    return fundingRecords;
  }

  private static Type convertToFundingRecordType(BybitTransactionLogType type) {
    Type fundingRecordType = null;

    if(type != null){
      switch (type) {
        case TRANSFER_IN:
        case TRANSFER_IN_INS_LOAN:
          fundingRecordType = Type.DEPOSIT;
          break;
        case TRANSFER_OUT:
        case TRANSFER_OUT_INS_LOAN:
          fundingRecordType = Type.WITHDRAWAL;
          break;
        case TRADE:
        case CURRENCY_BUY:
        case CURRENCY_SELL:
        case SPOT_REPAYMENT_BUY:
        case SPOT_REPAYMENT_SELL:
        case AUTO_BUY_LIABILITY_INS_LOAN:
        case LIQUIDATION:
        case AUTO_SOLD_COLLATERAL_INS_LOAN:
          fundingRecordType = Type.TRADE;
          break;
        case DELIVERY:
          fundingRecordType = Type.DELIVERY;
          break;
        case INTEREST:
          fundingRecordType = Type.INTEREST;
          break;
        case SETTLEMENT:
          fundingRecordType = Type.SETTLEMENT;
          break;
        case BONUS:
        case FEE_REFUND:
        case BORROWED_AMOUNT_INS_LOAN:
          fundingRecordType = Type.OTHER_INFLOW;
            break;
        case AUTO_PRINCIPLE_REPAYMENT_INS_LOAN:
        case PRINCIPLE_REPAYMENT_INS_LOAN:
        case INTEREST_REPAYMENT_INS_LOAN:
        case AUTO_INTEREST_REPAYMENT_INS_LOAN:
          fundingRecordType = Type.OTHER_OUTFLOW;
          break;
        default:
          break;
      }
    }
    return fundingRecordType;
  }
}
