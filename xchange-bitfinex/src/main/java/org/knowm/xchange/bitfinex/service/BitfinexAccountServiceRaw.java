package org.knowm.xchange.bitfinex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexAccountFeesResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalanceHistoryRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalanceHistoryResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalancesRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositWithdrawalHistoryRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositWithdrawalHistoryResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexTradingFeeResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexTradingFeesRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import org.knowm.xchange.bitfinex.v2.dto.EmptyRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.LedgerEntry;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitfinexAccountServiceRaw extends BitfinexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitfinexTradingFeeResponse[] getBitfinexDynamicTradingFees() throws IOException {
    try {
      BitfinexTradingFeeResponse[] response =
          bitfinex.tradingFees(
              apiKey,
              payloadCreator,
              signatureCreator,
              new BitfinexTradingFeesRequest(
                  String.valueOf(exchange.getNonceFactory().createValue())));
      return response;
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexBalancesResponse[] getBitfinexAccountInfo() throws IOException {

    BitfinexBalancesResponse[] balances =
        bitfinex.balances(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexBalancesRequest(String.valueOf(exchange.getNonceFactory().createValue())));
    return balances;
  }

  public BitfinexMarginInfosResponse[] getBitfinexMarginInfos() throws IOException {

    BitfinexMarginInfosResponse[] marginInfos =
        bitfinex.marginInfos(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexMarginInfosRequest(
                String.valueOf(exchange.getNonceFactory().createValue())));
    return marginInfos;
  }

  public BitfinexDepositWithdrawalHistoryResponse[] getDepositWithdrawalHistory(
      String currency, String method, Date since, Date until, Integer limit) throws IOException {
    BitfinexDepositWithdrawalHistoryRequest request =
        new BitfinexDepositWithdrawalHistoryRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            currency,
            method,
            since,
            until,
            limit);
    return bitfinex.depositWithdrawalHistory(apiKey, payloadCreator, signatureCreator, request);
  }

  public String withdraw(
      String withdrawType, String walletSelected, BigDecimal amount, String address)
      throws IOException {
    return withdraw(withdrawType, walletSelected, amount, address, null);
  }

  public String withdraw(
      String withdrawType,
      String walletSelected,
      BigDecimal amount,
      String address,
      String tagOrPaymentId)
      throws IOException {
    return withdraw(withdrawType, walletSelected, amount, address, tagOrPaymentId, null);
  }

  public String withdraw(
      String withdrawType,
      String walletSelected,
      BigDecimal amount,
      String address,
      String tagOrPaymentId,
      String currency)
      throws IOException {

    BitfinexWithdrawalRequest req =
        new BitfinexWithdrawalRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            withdrawType,
            walletSelected,
            amount,
            address,
            tagOrPaymentId);
    req.setCurrency(currency);
    BitfinexWithdrawalResponse[] withdrawResponse =
        bitfinex.withdraw(apiKey, payloadCreator, signatureCreator, req);
    if ("error".equalsIgnoreCase(withdrawResponse[0].getStatus())) {
      throw new ExchangeException(withdrawResponse[0].getMessage());
    }
    return withdrawResponse[0].getWithdrawalId();
  }

  public BitfinexDepositAddressResponse requestDepositAddressRaw(String currency)
      throws IOException {
    String type = "unknown";
    if (currency.equalsIgnoreCase("BTC")) {
      type = "bitcoin";
    } else if (currency.equalsIgnoreCase("LTC")) {
      type = "litecoin";
    } else if (currency.equalsIgnoreCase("ETH")) {
      type = "ethereum";
    } else if (currency.equalsIgnoreCase("IOT")) {
      type = "iota";
    } else if (currency.equalsIgnoreCase("BCH")) {
      type = "bcash";
    } else if (currency.equalsIgnoreCase("BTG")) {
      type = "bgold";
    } else if (currency.equalsIgnoreCase("DASH")) {
      type = "dash";
    } else if (currency.equalsIgnoreCase("EOS")) {
      type = "eos";
    } else if (currency.equalsIgnoreCase("XMR")) {
      type = "monero";
    } else if (currency.equalsIgnoreCase("NEO")) {
      type = "neo";
    } else if (currency.equalsIgnoreCase("XRP")) {
      type = "ripple";
    } else if (currency.equalsIgnoreCase("XLM")) {
      type = "xlm";
    } else if (currency.equalsIgnoreCase("TRX")) {
      type = "trx";
    } else if (currency.equalsIgnoreCase("ZEC")) {
      type = "zcash";
    }

    BitfinexDepositAddressResponse requestDepositAddressResponse =
        bitfinex.requestDeposit(
            apiKey,
            payloadCreator,
            signatureCreator,
            new BitfinexDepositAddressRequest(
                String.valueOf(exchange.getNonceFactory().createValue()), type, "exchange", 0));
    if (requestDepositAddressResponse != null) {
      return requestDepositAddressResponse;
    } else {
      return null;
    }
  }

  public BitfinexAccountFeesResponse getAccountFees() throws IOException {
    return bitfinex.accountFees(
        apiKey,
        payloadCreator,
        signatureCreator,
        new BitfinexNonceOnlyRequest(
            "/v1/account_fees", String.valueOf(exchange.getNonceFactory().createValue())));
  }

  public BitfinexBalanceHistoryResponse[] getBitfinexBalanceHistory(
      String currency, String wallet, Long since, Long until, int limit) throws IOException {
    return bitfinex.balanceHistory(
        apiKey,
        payloadCreator,
        signatureCreator,
        new BitfinexBalanceHistoryRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            currency,
            since,
            until,
            limit,
            wallet));
  }

  public List<LedgerEntry> getLedgerEntries(
      String currency, Long startTimeMillis, Long endTimeMillis, Long limit) throws IOException {
    if (StringUtils.isBlank(currency)) {
      return bitfinexV2.getLedgerEntries(
          exchange.getNonceFactory(),
          apiKey,
          signatureV2,
          startTimeMillis,
          endTimeMillis,
          limit,
          EmptyRequest.INSTANCE);
    }
    return bitfinexV2.getLedgerEntries(
        exchange.getNonceFactory(),
        apiKey,
        signatureV2,
        currency,
        startTimeMillis,
        endTimeMillis,
        limit,
        EmptyRequest.INSTANCE);
  }
}
