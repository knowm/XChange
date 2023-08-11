package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioAddressRecord;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioSubAccountTransfer;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import org.knowm.xchange.gateio.dto.account.params.GateioSubAccountTransfersParams;
import org.knowm.xchange.gateio.service.params.GateioWithdrawalsParams;

public class GateioAccountServiceRaw extends GateioBaseService {

  public GateioAccountServiceRaw(GateioExchange exchange) {
    super(exchange);
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


  public List<GateioWithdrawalRecord> getWithdrawals(GateioWithdrawalsParams params) throws IOException {
    String currency = params.getCurrency() != null ? params.getCurrency().toString() : null;
    Long from = params.getStartTime() != null ? params.getStartTime().getEpochSecond() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().getEpochSecond() : null;
    return gateioV4Authenticated.getWithdrawals(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        currency, from, to, params.getPageLength(), params.getZeroBasedPageNumber());
  }


  public GateioWithdrawalRecord withdraw(GateioWithdrawalRequest gateioWithdrawalRequest) throws IOException {
    return gateioV4Authenticated.withdraw(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, gateioWithdrawalRequest);
  }


  public List<GateioAddressRecord> getSavedAddresses(Currency currency) throws IOException {
    Validate.notNull(currency);
    return gateioV4Authenticated.getSavedAddresses(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currency.getCurrencyCode());
  }


  List<GateioSubAccountTransfer> getSubAccountTransfers(GateioSubAccountTransfersParams params) throws IOException {
    Long from = params.getStartTime() != null ? params.getStartTime().getEpochSecond() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().getEpochSecond() : null;

    return gateioV4Authenticated.getSubAccountTransfers(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        params.getSubAccountId(), from, to, params.getPageLength(), params.getZeroBasedPageNumber());
  }


}
