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
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositsWithdrawals;
import org.knowm.xchange.gateio.dto.account.GateioFunds;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;

public class GateioAccountServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioAccountServiceRaw(GateioExchange exchange) {

    super(exchange);
  }

  public GateioFunds getGateioAccountInfo() throws IOException {

    GateioFunds gateioFunds =
        gateioAuthenticated.getFunds(exchange.getExchangeSpecification().getApiKey(), signatureCreator);
    return handleResponse(gateioFunds);
  }

  public GateioDepositAddress getGateioDepositAddress(Currency currency) throws IOException {
    GateioDepositAddress depositAddress =
        gateioAuthenticated.getDepositAddress(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            currency.getCurrencyCode());
    return depositAddress;
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


  public List<GateioWithdrawStatus> getWithdrawStatus(Currency currency) throws IOException {
    String currencyCode = currency == null ? null : currency.getCurrencyCode();

    try {
      return gateioV4Authenticated.getWithdrawStatus(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currencyCode);
    }
    catch (GateioException e) {
      throw GateioErrorAdapter.adapt(e);
    }

  }
}
