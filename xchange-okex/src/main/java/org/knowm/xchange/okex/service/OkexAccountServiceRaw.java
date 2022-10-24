package org.knowm.xchange.okex.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.okex.OkexAuthenticated;
import org.knowm.xchange.okex.OkexExchange;
import org.knowm.xchange.okex.dto.account.*;
import org.knowm.xchange.okex.dto.OkexException;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.dto.account.OkexDepositAddress;
import org.knowm.xchange.okex.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.dto.account.PiggyBalance;
import org.knowm.xchange.okex.dto.subaccount.OkexSubAccountDetails;
import org.knowm.xchange.utils.DateUtils;

import static org.knowm.xchange.okex.OkexAuthenticated.assetTransferPath;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexAccountServiceRaw extends OkexBaseService {
  public OkexAccountServiceRaw(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public OkexResponse<List<OkexAssetBalance>> getAssetBalances(List<Currency> currencies)
      throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getAssetBalances(
                      currencies,
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter(rateLimiter(OkexAuthenticated.assetBalancesPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexWalletBalance>> getWalletBalances(List<Currency> currencies)
      throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getWalletBalances(
                      currencies,
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter(rateLimiter(OkexAuthenticated.balancePath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexSetLeverageResponse>> setLeverage(String instrumentId, String currency, String leverage, String marginMode, String positionSide)
      throws OkexException, IOException {
    try {
      OkexSetLeverageRequest requestPayload = OkexSetLeverageRequest.builder()
              .instrumentId(instrumentId)
              .currency(currency)
              .leverage(leverage)
              .marginMode(marginMode)
              .positionSide(positionSide)
              .build();
      return decorateApiCall(
              () ->
                  okexAuthenticated.setLeverage(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                          requestPayload)

              )
          .withRateLimiter(rateLimiter(OkexAuthenticated.positionsPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexDepositAddress>> getDepositAddress(String currency)
      throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getDepositAddress(
                      currency,
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter(rateLimiter(OkexAuthenticated.depositAddressPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexTradeFee>> getTradeFee(
      String instrumentType, String instrumentId, String underlying, String category)
      throws IOException, OkexException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getTradeFee(
                      instrumentType,
                      instrumentId,
                      underlying,
                      category,
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter(rateLimiter(OkexAuthenticated.tradeFeePath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexAccountConfig>> getOkexAccountConfiguration() throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                      okexAuthenticated.getAccountConfiguration(
                              exchange.getExchangeSpecification().getApiKey(),
                              signatureCreator,
                              DateUtils.toUTCISODateString(new Date()),
                              (String)
                                      exchange
                                              .getExchangeSpecification()
                                              .getExchangeSpecificParametersItem("passphrase"),
                              (String)
                                      exchange
                                              .getExchangeSpecification()
                                              .getExchangeSpecificParametersItem("simulated")))
              .withRateLimiter(rateLimiter(okexAuthenticated.currenciesPath))
              .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexBillDetails>> getBills(
          String instrumentType,
          String currency,
          String marginMode,
          String contractType,
          String billType,
          String billSubType,
          String afterBillId,
          String beforeBillId,
          String beginTimestamp,
          String endTimestamp,
          String maxNumberOfResults)
          throws OkexException, IOException {
    try {
      return decorateApiCall(
              () ->
                      okexAuthenticated.getBills(
                              instrumentType,
                              currency,
                              marginMode,
                              contractType,
                              billType,
                              billSubType,
                              afterBillId,
                              beforeBillId,
                              beginTimestamp,
                              endTimestamp,
                              maxNumberOfResults,
                              exchange.getExchangeSpecification().getApiKey(),
                              signatureCreator,
                              DateUtils.toUTCISODateString(new Date()),
                              (String)
                                      exchange
                                              .getExchangeSpecification()
                                              .getExchangeSpecificParametersItem("passphrase"),
                              (String)
                                      exchange
                                              .getExchangeSpecification()
                                              .getExchangeSpecificParametersItem("simulated")))
              .withRateLimiter(rateLimiter(okexAuthenticated.currenciesPath))
              .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexChangeMarginResponse>> changeMargin(
          String instrumentId,
          String positionSide,
          String type,
          String amount,
          String currency,
          boolean auto,
          boolean loadTrans)
          throws OkexException, IOException {
    try {
      OkexChangeMarginRequest requestPayload = OkexChangeMarginRequest.builder()
              .instrumentId(instrumentId)
              .posSide(positionSide)
              .type(type)
              .amount(amount)
              .currency(currency)
              .auto(auto)
              .loanTrans(loadTrans)
              .build();
      return decorateApiCall(
              () ->
                      okexAuthenticated.changeMargin(
                              exchange.getExchangeSpecification().getApiKey(),
                              signatureCreator,
                              DateUtils.toUTCISODateString(new Date()),
                              (String)
                                      exchange
                                              .getExchangeSpecification()
                                              .getExchangeSpecificParametersItem("passphrase"),
                              (String)
                                      exchange
                                              .getExchangeSpecification()
                                              .getExchangeSpecificParametersItem("simulated"),
                              requestPayload))
              .withRateLimiter(rateLimiter(okexAuthenticated.currenciesPath))
              .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexSubAccountDetails>> getSubAccounts(Boolean enable, String subAcct)
      throws IOException {
    return decorateApiCall(
            () ->
                this.okexAuthenticated.getSubAccountList(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    DateUtils.toUTCISODateString(new Date()),
                    (String)
                        exchange
                            .getExchangeSpecification()
                            .getExchangeSpecificParametersItem("passphrase"),
                    (String)
                        exchange
                            .getExchangeSpecification()
                            .getExchangeSpecificParametersItem("simulated"),
                    enable == null ? null : enable.toString(),
                    subAcct))
        .withRateLimiter(rateLimiter(OkexAuthenticated.subAccountList))
        .call();
  }

  public OkexResponse<List<OkexWalletBalance>> getSubAccountBalance(String subAcct)
      throws IOException {
    return decorateApiCall(
            () ->
                this.okexAuthenticated.getSubAccountBalance(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    DateUtils.toUTCISODateString(new Date()),
                    (String)
                        exchange
                            .getExchangeSpecification()
                            .getExchangeSpecificParametersItem("passphrase"),
                    (String)
                        exchange
                            .getExchangeSpecification()
                            .getExchangeSpecificParametersItem("simulated"),
                    subAcct))
        .withRateLimiter(rateLimiter(OkexAuthenticated.subAccountList))
        .call();
  }

  public OkexResponse<List<PiggyBalance>> getPiggyBalance(String ccy) throws IOException {
    return decorateApiCall(
            () ->
                this.okexAuthenticated.getPiggyBalance(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    DateUtils.toUTCISODateString(new Date()),
                    (String)
                        exchange
                            .getExchangeSpecification()
                            .getExchangeSpecificParametersItem("passphrase"),
                    (String)
                        exchange
                            .getExchangeSpecification()
                            .getExchangeSpecificParametersItem("simulated"),
                    ccy))
        .withRateLimiter(rateLimiter(OkexAuthenticated.subAccountList))
        .call();
  }
  public OkexResponse<List<org.knowm.xchange.okex.v5.dto.account.OkexAssetTransferResponse>> postAssetTransfer(String ccy, String amt, String from, String to,
                                                                                                               String subAccount, String type, Boolean loanTrans,
                                                                                                               String clientId, String omitPosRisk) throws IOException {
    return decorateApiCall(
            () ->
                    this.okexAuthenticated.postAssetTransfer(
                            exchange.getExchangeSpecification().getApiKey(),
                            signatureCreator,
                            DateUtils.toUTCISODateString(new Date()),
                            (String)
                                    exchange
                                            .getExchangeSpecification()
                                            .getExchangeSpecificParametersItem("passphrase"),
                            (String)
                                    exchange
                                            .getExchangeSpecification()
                                            .getExchangeSpecificParametersItem("simulated"),
                            org.knowm.xchange.okex.v5.dto.account.OkexAssetTransferRequest.builder()
                                    .currency(ccy)
                                    .amount(amt)
                                    .from(from)
                                    .to(to)
                                    .subAccount(subAccount)
                                    .type(type)
                                    .loanTrans(loanTrans)
                                    .clientId(clientId)
                                    .omitPosRisk(omitPosRisk)
                                    .build()))
            .withRateLimiter(rateLimiter(assetTransferPath))
            .call();
  }
}
