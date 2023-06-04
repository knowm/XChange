package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositsWithdrawals;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;

public class GateioAccountServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountServiceRaw(GateioExchange exchange) {

    super(exchange);
  }

  public String withdraw(
      Currency currency, BigDecimal amount, String baseAddress, String addressTag)
      throws IOException {
    String withdrawAddress = baseAddress;
    if (addressTag != null && addressTag.length() > 0) {
      withdrawAddress = withdrawAddress + " " + addressTag;
    }
    return withdraw(currency.getCurrencyCode(), amount, withdrawAddress);
  }

  public GateioDepositsWithdrawals getDepositsWithdrawals(Date start, Date end) throws IOException {
    GateioDepositsWithdrawals gateioDepositsWithdrawals =
        gateioAuthenticated.getDepositsWithdrawals(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            start == null ? null : start.getTime() / 1000,
            end == null ? null : end.getTime() / 1000);
    return handleResponse(gateioDepositsWithdrawals);
  }

  public String withdraw(String currency, BigDecimal amount, String address) throws IOException {
    GateioBaseResponse withdraw =
        gateioAuthenticated.withdraw(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            currency,
            amount,
            address);
    if (!withdraw.isResult()) {
      throw new ExchangeException(withdraw.getMessage());
    }
    // unfortunatelly gate.io does not return any id for the withdrawal
    return null;
  }


  public GateioDepositAddress getDepositAddress(Currency currency) throws IOException {
    String currencyCode = currency == null ? null : currency.getCurrencyCode();

    try {
      return gateioV4Authenticated.getDepositAddress(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currencyCode);
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }

  }


  public List<GateioWithdrawStatus> getWithdrawStatus(Currency currency) throws IOException {
    String currencyCode = currency == null ? null : currency.getCurrencyCode();

    try {
      return gateioV4Authenticated.getWithdrawStatus(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currencyCode);
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }

  }


  public List<GateioCurrencyBalance> getSpotBalances(Currency currency) throws IOException {

    String currencyCode = currency == null ? null : currency.getCurrencyCode();
    return gateioV4Authenticated.getSpotAccounts(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currencyCode);

  }


  public List<GateioWithdrawalRecord> getWithdrawals(Currency currency) throws IOException {
    String currencyCode = currency == null ? null : currency.getCurrencyCode();
    return gateioV4Authenticated.getWithdrawals(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currencyCode);
  }


  public GateioWithdrawalRecord withdraw(GateioWithdrawalRequest gateioWithdrawalRequest) throws IOException {
    return gateioV4Authenticated.withdraw(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, gateioWithdrawalRequest);
  }


}
