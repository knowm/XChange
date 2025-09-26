package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.*;
import org.knowm.xchange.bitso.dto.BitsoBaseResponse;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.funding.*;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitsoAccountServiceRaw extends BitsoBaseService {

  private final BitsoDigest signatureCreator;
  private final BitsoAuthenticated bitsoAuthenticated;
  private final BitsoFundingAuthenticated bitsoFundingAuthenticated;

  protected BitsoAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.bitsoAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitsoAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitsoJacksonObjectMapperFactory()))
            .build();
    this.bitsoFundingAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitsoFundingAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitsoJacksonObjectMapperFactory()))
            .build();
    this.signatureCreator =
        BitsoDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }

  public BitsoBalance getBitsoBalance() throws IOException {
    try {
      BitsoBaseResponse<BitsoBalance> response =
          bitsoAuthenticated.getBalance(signatureCreator, exchange.getNonceFactory());

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error getting balance";
        throw new ExchangeException("Error getting balance. " + errorMessage);
      }

      return response.getPayload();
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** List funding transactions (deposits) */
  public List<BitsoFunding> getBitsoFundings(
      String currency, Integer limit, String marker, String sort) throws IOException {
    try {
      BitsoBaseResponse<BitsoFunding[]> response =
          bitsoFundingAuthenticated.listFundings(
              signatureCreator, exchange.getNonceFactory(), currency, limit, marker, sort);

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error getting fundings";
        throw new ExchangeException("Error getting fundings. " + errorMessage);
      }

      return Arrays.asList(response.getPayload());
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** List withdrawal transactions */
  public List<BitsoWithdrawal> getBitsoWithdrawals(
      String currency, Integer limit, String marker, String sort) throws IOException {
    try {
      BitsoBaseResponse<BitsoWithdrawal[]> response =
          bitsoFundingAuthenticated.listWithdrawals(
              signatureCreator, exchange.getNonceFactory(), currency, limit, marker, sort);

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error getting withdrawals";
        throw new ExchangeException("Error getting withdrawals. " + errorMessage);
      }

      return Arrays.asList(response.getPayload());
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** Create a cryptocurrency withdrawal */
  public BitsoWithdrawal createBitsoCryptoWithdrawal(BitsoWithdrawalRequest request)
      throws IOException {
    try {
      BitsoBaseResponse<BitsoWithdrawal> response =
          bitsoFundingAuthenticated.createCryptoWithdrawal(
              signatureCreator, exchange.getNonceFactory(), request);

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error creating crypto withdrawal";
        throw new ExchangeException("Error creating crypto withdrawal. " + errorMessage);
      }

      return response.getPayload();
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** Create a fiat withdrawal */
  public BitsoWithdrawal createBitsoFiatWithdrawal(BitsoWithdrawalRequest request)
      throws IOException {
    try {
      BitsoBaseResponse<BitsoWithdrawal> response =
          bitsoFundingAuthenticated.createFiatWithdrawal(
              signatureCreator, exchange.getNonceFactory(), request);

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error creating fiat withdrawal";
        throw new ExchangeException("Error creating fiat withdrawal. " + errorMessage);
      }

      return response.getPayload();
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** Get withdrawal methods for a currency */
  public List<BitsoWithdrawalMethod> getBitsoWithdrawalMethods(String currencyTicker)
      throws IOException {
    try {
      BitsoBaseResponse<BitsoWithdrawalMethod[]> response =
          bitsoFundingAuthenticated.getWithdrawalMethods(
              signatureCreator,
              exchange.getNonceFactory(),
              BitsoAdapters.toBitsoCurrency(currencyTicker));

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error getting withdrawal methods";
        throw new ExchangeException("Error getting withdrawal methods. " + errorMessage);
      }

      return Arrays.asList(response.getPayload());
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }

  /** List receiving accounts */
  public List<BitsoReceivingAccount> getBitsoReceivingAccounts(String currency) throws IOException {
    try {
      BitsoBaseResponse<BitsoReceivingAccount[]> response =
          bitsoFundingAuthenticated.listReceivingAccounts(
              signatureCreator, exchange.getNonceFactory(), currency);

      if (!response.getSuccess() || response.getError() != null) {
        String errorMessage =
            response.getError() != null
                ? response.getError().getMessage()
                : "Unknown error getting receiving accounts";
        throw new ExchangeException("Error getting receiving accounts. " + errorMessage);
      }

      return Arrays.asList(response.getPayload());
    } catch (BitsoException e) {
      throw BitsoErrorAdapter.adapt(e);
    }
  }
}
