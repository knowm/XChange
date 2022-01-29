package org.knowm.xchange.okex.v5.service;

import static org.knowm.xchange.okex.v5.OkexAuthenticated.assetBalancesPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.balancePath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.depositAddressPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.subAccountList;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.tradeFeePath;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.account.*;
import org.knowm.xchange.okex.v5.dto.account.OkexAssetBalance;
import org.knowm.xchange.okex.v5.dto.account.OkexDepositAddress;
import org.knowm.xchange.okex.v5.dto.account.OkexTradeFee;
import org.knowm.xchange.okex.v5.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.v5.dto.account.PiggyBalance;
import org.knowm.xchange.okex.v5.dto.subaccount.OkexSubAccountDetails;
import org.knowm.xchange.utils.DateUtils;

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
          .withRateLimiter(rateLimiter(assetBalancesPath))
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
          .withRateLimiter(rateLimiter(balancePath))
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
          .withRateLimiter(rateLimiter(depositAddressPath))
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
          .withRateLimiter(rateLimiter(tradeFeePath))
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
        .withRateLimiter(rateLimiter(subAccountList))
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
        .withRateLimiter(rateLimiter(subAccountList))
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
        .withRateLimiter(rateLimiter(subAccountList))
        .call();
  }
}
