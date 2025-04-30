package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;
import org.knowm.xchange.bitget.dto.account.BitgetDepositWithdrawRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetMainSubTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetSubBalanceDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.params.BitgetMainSubTransferHistoryParams;
import org.knowm.xchange.bitget.dto.account.params.BitgetTransferHistoryParams;
import org.knowm.xchange.bitget.service.params.BitgetFundingHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamClientOid;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class BitgetAccountServiceRaw extends BitgetBaseService {

  public BitgetAccountServiceRaw(BitgetExchange exchange) {
    super(exchange);
  }

  public List<BitgetBalanceDto> getBitgetBalances(Currency currency) throws IOException {
    return bitgetAuthenticated
        .balances(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            BitgetAdapters.toString(currency))
        .getData();
  }

  public List<BitgetSubBalanceDto> getSubBitgetBalances() throws IOException {
    return bitgetAuthenticated
        .subBalances(apiKey, bitgetDigest, passphrase, exchange.getNonceFactory())
        .getData();
  }

  public List<BitgetTransferRecordDto> getBitgetTransferRecords(BitgetTransferHistoryParams params)
      throws IOException {
    Long from = params.getStartTime() != null ? params.getStartTime().toEpochMilli() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().toEpochMilli() : null;

    return bitgetAuthenticated
        .transferRecords(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            BitgetAdapters.toString(params.getCurrency()),
            params.getLimit(),
            params.getClientOid(),
            BitgetAdapters.toString(params.getFromAccountType()),
            from,
            to,
            params.getEndId())
        .getData();
  }

  public List<BitgetMainSubTransferRecordDto> getBitgetMainSubTransferRecords(
      BitgetMainSubTransferHistoryParams params) throws IOException {
    Long from = params.getStartTime() != null ? params.getStartTime().toEpochMilli() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().toEpochMilli() : null;

    return bitgetAuthenticated
        .mainSubTransferRecords(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            BitgetAdapters.toString(params.getCurrency()),
            params.getLimit(),
            params.getClientOid(),
            BitgetAdapters.toString(params.getRole()),
            params.getSubAccountUid(),
            from,
            to,
            params.getEndId())
        .getData();
  }

  public List<BitgetDepositWithdrawRecordDto> getBitgetWithdrawRecords(TradeHistoryParams params)
      throws IOException {
    // get arguments
    Currency currency =
        params instanceof TradeHistoryParamCurrency
            ? ((TradeHistoryParamCurrency) params).getCurrency()
            : null;
    String orderId =
        params instanceof TradeHistoryParamOrderId
            ? ((TradeHistoryParamOrderId) params).getOrderId()
            : null;
    String clientOid =
        params instanceof TradeHistoryParamClientOid
            ? ((TradeHistoryParamClientOid) params).getClientOid()
            : null;
    Integer limit =
        params instanceof TradeHistoryParamLimit
            ? ((TradeHistoryParamLimit) params).getLimit()
            : null;
    String lastTradeId =
        params instanceof TradeHistoryParamsIdSpan
            ? ((TradeHistoryParamsIdSpan) params).getEndId()
            : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() : null;
    }

    return bitgetAuthenticated
        .withdrawalRecords(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            BitgetAdapters.toString(currency),
            limit,
            orderId,
            clientOid,
            from,
            to,
            lastTradeId)
        .getData();
  }

  public List<BitgetDepositWithdrawRecordDto> getBitgetDepositRecords(TradeHistoryParams params)
      throws IOException {
    // get arguments
    Currency currency =
        params instanceof TradeHistoryParamCurrency
            ? ((TradeHistoryParamCurrency) params).getCurrency()
            : null;
    String orderId =
        params instanceof TradeHistoryParamOrderId
            ? ((TradeHistoryParamOrderId) params).getOrderId()
            : null;
    Integer limit =
        params instanceof TradeHistoryParamLimit
            ? ((TradeHistoryParamLimit) params).getLimit()
            : null;
    String lastTradeId =
        params instanceof TradeHistoryParamsIdSpan
            ? ((TradeHistoryParamsIdSpan) params).getEndId()
            : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() : null;
    }

    return bitgetAuthenticated
        .depositRecords(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            BitgetAdapters.toString(currency),
            limit,
            orderId,
            from,
            to,
            lastTradeId)
        .getData();
  }

  public List<BitgetDepositWithdrawRecordDto> getBitgetSubAccountDepositRecords(
      TradeHistoryParams params) throws IOException {
    // get arguments
    Currency currency =
        params instanceof TradeHistoryParamCurrency
            ? ((TradeHistoryParamCurrency) params).getCurrency()
            : null;
    String subAccountUid =
        params instanceof BitgetFundingHistoryParams
            ? ((BitgetFundingHistoryParams) params).getSubAccountUid()
            : null;
    Integer limit =
        params instanceof TradeHistoryParamLimit
            ? ((TradeHistoryParamLimit) params).getLimit()
            : null;
    String lastTradeId =
        params instanceof TradeHistoryParamsIdSpan
            ? ((TradeHistoryParamsIdSpan) params).getEndId()
            : null;
    Long from = null;
    Long to = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan paramsTimeSpan = ((TradeHistoryParamsTimeSpan) params);
      from = paramsTimeSpan.getStartTime() != null ? paramsTimeSpan.getStartTime().getTime() : null;
      to = paramsTimeSpan.getEndTime() != null ? paramsTimeSpan.getEndTime().getTime() : null;
    }

    return bitgetAuthenticated
        .subDepositRecords(
            apiKey,
            bitgetDigest,
            passphrase,
            exchange.getNonceFactory(),
            BitgetAdapters.toString(currency),
            limit,
            subAccountUid,
            from,
            to,
            lastTradeId)
        .getData();
  }
}
