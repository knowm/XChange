package org.knowm.xchange.coinsph.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphAdapters;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.account.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.account.params.RequestDepositAddressParams;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.withdrawals.Beneficiary;

public class CoinsphAccountService extends CoinsphAccountServiceRaw implements AccountService {

  public CoinsphAccountService(
      CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException, CoinsphException {
    CoinsphAccount coinsphAccount = super.getCoinsphAccount();
    return CoinsphAdapters.adaptAccountInfo(
        coinsphAccount, exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException, CoinsphException {
    // Get the default network for the currency
    String network = currency.getCurrencyCode();

    // For some currencies, the network might be different from the currency code
    // For example, USDT could be on different networks like ETH, BSC, etc.
    // This would need to be expanded based on Coins.ph's supported networks

    CoinsphWithdrawal withdrawal =
        withdraw(currency.getCurrencyCode(), network, address, amount, null);

    return withdrawal.getId();
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException, CoinsphException {
    if (params instanceof NetworkWithdrawFundsParams) {
      NetworkWithdrawFundsParams coinsphParams = (NetworkWithdrawFundsParams) params;
      CoinsphWithdrawal withdrawal =
          withdraw(
              coinsphParams.getCurrency().getCurrencyCode(),
              coinsphParams.getNetwork(),
              coinsphParams.getAddress(),
              coinsphParams.getAmount(),
              coinsphParams.getAddressTag());

      return withdrawal.getId();
    } else if (params instanceof FiatWithdrawFundsParams) {
      return withdrawFiat((FiatWithdrawFundsParams) params);
    } else if (params instanceof DefaultWithdrawFundsParams) {
      DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
      return withdrawFunds(
          defaultParams.getCurrency(), defaultParams.getAmount(), defaultParams.getAddress());
    }

    throw new IllegalArgumentException(
        "WithdrawFundsParams must be either DefaultWithdrawFundsParams or CoinsphWithdrawFundsParams");
  }

  private String withdrawFiat(FiatWithdrawFundsParams params) throws IOException, CoinsphException {
    // 1. List channels using the currency provided and transactionType -1
    String currencyCode = params.getCurrency().getCurrencyCode();

    String transactionChannel =
        getCustomParameter(params.getCustomParameters(), "transactionChannel", String.class);

    String transactionSubject =
        getCustomParameter(params.getCustomParameters(), "transactionSubject", String.class);

    Optional<CoinsphFiatChannel> availableChannel =
        findFirstAvailableChannel(
            currencyCode, -1, transactionChannel, transactionSubject, params.getAmount());

    if (!availableChannel.isPresent()) {
      throw new ExchangeException("No available fiat channels found for currency: " + currencyCode);
    }

    CoinsphFiatChannel channel = availableChannel.get();

    // 3. Create a cash out request matching the documentation
    String internalOrderId = generateInternalOrderId(params);
    Map<String, Object> extendInfo = buildExtendInfo(params);

    CoinsphCashOutRequest request =
        CoinsphCashOutRequest.builder()
            .amount(params.getAmount())
            .internalOrderId(internalOrderId)
            .currency(currencyCode)
            .channelName(channel.getTransactionChannel())
            .channelSubject(channel.getTransactionSubject())
            .extendInfo(extendInfo)
            .build();

    // 4. Return the resulting ID
    CoinsphCashOutResponse response = cashOut(request);
    return response.getInternalOrderId();
  }

  private String generateInternalOrderId(FiatWithdrawFundsParams params) {
    // Use userReference if provided, otherwise generate a UUID
    String userReference = params.getUserReference();
    if (userReference != null && !userReference.trim().isEmpty()) {
      return userReference;
    }
    return UUID.randomUUID().toString();
  }

  private Map<String, Object> buildExtendInfo(FiatWithdrawFundsParams params) {
    Map<String, Object> extendInfo = new HashMap<>();

    Beneficiary beneficiary = params.getBeneficiary();
    if (beneficiary != null) {
      // Add recipient account number
      if (beneficiary.getAccountNumber() != null) {
        extendInfo.put("recipientAccountNumber", beneficiary.getAccountNumber());
      }

      // Add recipient name
      if (beneficiary.getName() != null) {
        extendInfo.put("recipientName", beneficiary.getName());
      }

      // Add recipient address (formatted as a single string)
      if (beneficiary.getAddress() != null) {
        extendInfo.put("recipientAddress", formatAddress(beneficiary.getAddress()));
      }

      // Add remarks/reference
      if (beneficiary.getReference() != null) {
        extendInfo.put("remarks", beneficiary.getReference());
      }
    }

    // Add any custom parameters
    Map<String, Object> customParameters = params.getCustomParameters();
    if (customParameters != null) {
      extendInfo.putAll(customParameters);
    }

    return extendInfo;
  }

  private String formatAddress(org.knowm.xchange.service.trade.params.withdrawals.Address address) {
    StringBuilder addressBuilder = new StringBuilder();

    if (address.getLine1() != null) {
      addressBuilder.append(address.getLine1());
    }

    if (address.getLine2() != null && !address.getLine2().trim().isEmpty()) {
      if (addressBuilder.length() > 0) {
        addressBuilder.append(", ");
      }
      addressBuilder.append(address.getLine2());
    }

    if (address.getCity() != null) {
      if (addressBuilder.length() > 0) {
        addressBuilder.append(", ");
      }
      addressBuilder.append(address.getCity());
    }

    if (address.getState() != null) {
      if (addressBuilder.length() > 0) {
        addressBuilder.append(", ");
      }
      addressBuilder.append(address.getState());
    }

    if (address.getCountry() != null) {
      if (addressBuilder.length() > 0) {
        addressBuilder.append(", ");
      }
      addressBuilder.append(address.getCountry());
    }

    return addressBuilder.toString();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args)
      throws IOException, CoinsphException {
    String network = null;

    // If network is provided as an argument, use it
    if (args != null && args.length > 0) {
      network = args[0];
    }

    CoinsphDepositAddress depositAddress =
        requestDepositAddress(currency.getCurrencyCode(), network);

    return depositAddress.getAddress();
  }

  @Override
  public String requestDepositAddress(RequestDepositAddressParams params) throws IOException {
    return requestDepositAddress(params.getCurrency(), params.getNetwork());
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new CoinsphFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
      throws IOException, CoinsphException {

    boolean includeDeposits = false;
    boolean includeWithdrawals = false;
    Currency currency = null;

    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType fundingTypeParams = (HistoryParamsFundingType) params;
      includeDeposits = fundingTypeParams.getType() == FundingRecord.Type.DEPOSIT;
      includeWithdrawals = fundingTypeParams.getType() == FundingRecord.Type.WITHDRAWAL;
    }
    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency currencyParam = (TradeHistoryParamCurrency) params;
      currency = currencyParam.getCurrency();
    }

    List<CoinsphFundingRecord> fundingRecords =
        getFundingHistory(includeDeposits, includeWithdrawals, currency);
    return CoinsphAdapters.adaptFundingRecords(fundingRecords);
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException, CoinsphException {
    List<CoinsphTradeFee> fees = super.getCoinsphTradeFees();
    return CoinsphAdapters.adaptTradeFees(fees);
  }

  private static <T> T getCustomParameter(Map<String, Object> params, String key, Class<T> type) {
    if (params == null) {
      return null;
    }
    Object value = params.get(key);
    if (value == null) {
      return null;
    }

    if (type.isInstance(value)) {
      return type.cast(value);
    }
    return null;
  }
}
