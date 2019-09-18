package org.knowm.xchange.binance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.*;
import org.knowm.xchange.binance.dto.account.DepositList.BinanceDeposit;
import org.knowm.xchange.currency.Currency;

public class BinanceAccountServiceRaw extends BinanceBaseService {

  public BinanceAccountServiceRaw(BinanceExchange exchange, BinanceAuthenticated binance) {
    super(exchange, binance);
  }

  public BinanceAccountInformation account() throws BinanceException, IOException {
    return binance.account(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator);
  }

  // the /wapi endpoint of binance is not stable yet and can be changed in future, there is also a
  // lack of current documentation

  public String withdraw(String asset, String address, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(asset, address, amount, name);
  }

  public String withdraw(String asset, String address, String addressTag, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(asset, address, addressTag, amount, name);
  }

  private String withdraw(String asset, String address, BigDecimal amount, String name)
      throws IOException, BinanceException {
    WithdrawRequest result =
        binance.withdraw(
            asset,
            address,
            null,
            amount,
            name,
            getRecvWindow(),
            getTimestampFactory(),
            apiKey,
            signatureCreator);
    checkWapiResponse(result);
    return result.getData();
  }

  private String withdraw(
      String asset, String address, String addressTag, BigDecimal amount, String name)
      throws IOException, BinanceException {
    WithdrawRequest result =
        binance.withdraw(
            asset,
            address,
            addressTag,
            amount,
            name,
            getRecvWindow(),
            getTimestampFactory(),
            apiKey,
            signatureCreator);
    checkWapiResponse(result);
    return result.getData();
  }

  public DepositAddress requestDepositAddress(Currency currency) throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.depositAddress(
        BinanceAdapters.toSymbol(currency),
        recvWindow,
        getTimestampFactory(),
        apiKey,
        signatureCreator);
  }

  public AssetDetailResponse requestAssetDetail() throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.assetDetail(recvWindow, getTimestampFactory(), apiKey, signatureCreator);
  }

  public List<BinanceDeposit> depositHistory(String asset, Long startTime, Long endTime)
      throws BinanceException, IOException {
    DepositList result =
        binance.depositHistory(
            asset,
            startTime,
            endTime,
            getRecvWindow(),
            getTimestampFactory(),
            apiKey,
            signatureCreator);
    return checkWapiResponse(result);
  }

  public List<WithdrawList.BinanceWithdraw> withdrawHistory(
      String asset, Long startTime, Long endTime) throws BinanceException, IOException {
    WithdrawList result =
        binance.withdrawHistory(
            asset,
            startTime,
            endTime,
            getRecvWindow(),
            getTimestampFactory(),
            apiKey,
            signatureCreator);
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
