package org.knowm.xchange.binance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.binance.dto.account.DepositList;
import org.knowm.xchange.binance.dto.account.DepositList.BinanceDeposit;
import org.knowm.xchange.binance.dto.account.WapiResponse;
import org.knowm.xchange.binance.dto.account.WithdrawList;
import org.knowm.xchange.binance.dto.account.WithdrawRequest;
import org.knowm.xchange.currency.Currency;

public class BinanceAccountServiceRaw extends BinanceBaseService {

  public BinanceAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BinanceAccountInformation account(Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    return binance.account(recvWindow, timestamp, super.apiKey, super.signatureCreator);
  }

  // the /wapi endpoint of binance is not stable yet and can be changed in future, there is also a
  // lack of current documentation

  public String withdraw(String asset, String address, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(asset, address, amount, name, null, getTimestamp());
  }

  public String withdraw(String asset, String address, String addressTag, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return withdraw(asset, address, addressTag, amount, name, recvWindow, getTimestamp());
  }

  private String withdraw(
      String asset, String address, BigDecimal amount, String name, Long recvWindow, long timestamp)
      throws IOException, BinanceException {
    WithdrawRequest result =
        binance.withdraw(
            asset,
            address,
            null,
            amount,
            name,
            recvWindow,
            timestamp,
            super.apiKey,
            super.signatureCreator);
    checkWapiResponse(result);
    return result.getData();
  }

  private String withdraw(
      String asset,
      String address,
      String addressTag,
      BigDecimal amount,
      String name,
      Long recvWindow,
      long timestamp)
      throws IOException, BinanceException {
    WithdrawRequest result =
        binance.withdraw(
            asset,
            address,
            addressTag,
            amount,
            name,
            recvWindow,
            timestamp,
            super.apiKey,
            super.signatureCreator);
    checkWapiResponse(result);
    return result.getData();
  }

  public DepositAddress requestDepositAddress(Currency currency) throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.depositAddress(
        BinanceAdapters.toSymbol(currency),
        recvWindow,
        getTimestamp(),
        apiKey,
        super.signatureCreator);
  }

  public List<BinanceDeposit> depositHistory(
      String asset, Long startTime, Long endTime, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    DepositList result =
        binance.depositHistory(
            asset, startTime, endTime, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    return checkWapiResponse(result);
  }

  public List<WithdrawList.BinanceWithdraw> withdrawHistory(
      String asset, Long startTime, Long endTime, Long recvWindow, long timestamp)
      throws BinanceException, IOException {
    WithdrawList result =
        binance.withdrawHistory(
            asset, startTime, endTime, recvWindow, timestamp, super.apiKey, super.signatureCreator);
    return checkWapiResponse(result);
  }

  private <T> T checkWapiResponse(WapiResponse<T> result) {
    if (!result.success) {
      BinanceException exception;
      try {
        exception = new ObjectMapper().readValue(result.msg, BinanceException.class);
      } catch (Throwable e) {
        exception = new BinanceException(-1, result.msg);
      }
      throw exception;
    }
    return result.getData();
  }
}
