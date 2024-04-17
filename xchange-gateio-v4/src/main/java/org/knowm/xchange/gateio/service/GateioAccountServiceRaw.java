package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gateio.GateioErrorAdapter;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.GateioException;
import org.knowm.xchange.gateio.dto.account.GateioAccountBookRecord;
import org.knowm.xchange.gateio.dto.account.GateioAddressRecord;
import org.knowm.xchange.gateio.dto.account.GateioCurrencyBalance;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositRecord;
import org.knowm.xchange.gateio.dto.account.GateioSubAccountTransfer;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import org.knowm.xchange.gateio.dto.account.params.GateioSubAccountTransfersParams;
import org.knowm.xchange.gateio.service.params.GateioDepositsParams;
import org.knowm.xchange.gateio.service.params.GateioFundingHistoryParams;
import org.knowm.xchange.gateio.service.params.GateioWithdrawalsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

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


  public List<GateioDepositRecord> getDeposits(GateioDepositsParams params) throws IOException {
    String currency = params.getCurrency() != null ? params.getCurrency().toString() : null;
    Long from = params.getStartTime() != null ? params.getStartTime().getEpochSecond() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().getEpochSecond() : null;
    return gateioV4Authenticated.getDeposits(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        currency, from, to, params.getPageLength(), params.getZeroBasedPageNumber());
  }


  public GateioWithdrawalRecord withdraw(GateioWithdrawalRequest gateioWithdrawalRequest) throws IOException {
    return gateioV4Authenticated.withdraw(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, gateioWithdrawalRequest);
  }


  public List<GateioAddressRecord> getSavedAddresses(Currency currency) throws IOException {
    Validate.notNull(currency);
    return gateioV4Authenticated.getSavedAddresses(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, currency.getCurrencyCode());
  }


  public List<GateioAccountBookRecord> getAccountBookRecords(TradeHistoryParams params) throws IOException {
    // get arguments
    Currency currency = params instanceof GateioFundingHistoryParams ? ((GateioFundingHistoryParams) params).getCurrency() : null;
    String currencyCode = currency != null ? currency.toString() : null;
    String type = params instanceof GateioFundingHistoryParams ? ((GateioFundingHistoryParams) params).getType() : null;
    Integer pageLength = params instanceof TradeHistoryParamPaging ? ((TradeHistoryParamPaging) params).getPageLength() : null;
    Integer pageNumber = params instanceof TradeHistoryParamPaging ? ((TradeHistoryParamPaging) params).getPageNumber() : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() / 1000 : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() / 1000 : null;
    }

    return gateioV4Authenticated.getAccountBookRecords(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        currencyCode, from, to, pageLength, pageNumber, type);
  }


  public List<GateioSubAccountTransfer> getSubAccountTransfers(GateioSubAccountTransfersParams params) throws IOException {
    Long from = params.getStartTime() != null ? params.getStartTime().getEpochSecond() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().getEpochSecond() : null;

    return gateioV4Authenticated.getSubAccountTransfers(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        params.getSubAccountId(), from, to, params.getPageLength(), params.getZeroBasedPageNumber());
  }


}
