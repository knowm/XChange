package org.knowm.xchange.cryptocom.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComException;
import org.knowm.xchange.cryptocom.dto.CryptoComRequest;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositAddress;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositAddressResult;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositHistoryResult;
import org.knowm.xchange.cryptocom.dto.account.CryptoComDepositRecord;
import org.knowm.xchange.cryptocom.dto.account.CryptoComWithdrawalHistoryResult;
import org.knowm.xchange.cryptocom.dto.account.CryptoComWithdrawalRecord;

public class CryptoComAccountServiceRaw extends CryptoComBaseService {

  protected CryptoComAccountServiceRaw(
      CryptoComExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public List<CryptoComBalance> getCryptoComBalances() throws IOException, CryptoComException {
    CryptoComRequest request = buildRequest("private/user-balance", null);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.userBalance(request)).call();
    return getDataList(response, CryptoComBalance.class);
  }

  public List<CryptoComDepositAddress> getCryptoComDepositAddresses(String currency)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    params.put("currency", currency);
    CryptoComRequest request = buildRequest("private/get-deposit-address", params);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getDepositAddress(request)).call();
    CryptoComDepositAddressResult result =
        toObject(response.getResult(), CryptoComDepositAddressResult.class);
    return orEmpty(result == null ? null : result.getDepositAddressList());
  }

  public List<CryptoComDepositRecord> getCryptoComDepositHistory(
      String currency, Long startTime, Long endTime) throws IOException, CryptoComException {
    CryptoComRequest request =
        buildRequest(
            "private/get-deposit-history", currencyTimeRangeParams(currency, startTime, endTime));
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.getDepositHistory(request)).call();
    CryptoComDepositHistoryResult result =
        toObject(response.getResult(), CryptoComDepositHistoryResult.class);
    return orEmpty(result == null ? null : result.getDepositList());
  }

  public List<CryptoComWithdrawalRecord> getCryptoComWithdrawalHistory(
      String currency, Long startTime, Long endTime) throws IOException, CryptoComException {
    CryptoComRequest request =
        buildRequest(
            "private/get-withdrawal-history",
            currencyTimeRangeParams(currency, startTime, endTime));
    CryptoComResponse response =
        decorateApiCall(() -> cryptoCom.getWithdrawalHistory(request)).call();
    CryptoComWithdrawalHistoryResult result =
        toObject(response.getResult(), CryptoComWithdrawalHistoryResult.class);
    return orEmpty(result == null ? null : result.getWithdrawalList());
  }

  public CryptoComWithdrawalRecord createCryptoComWithdrawal(
      String currency,
      String amount,
      String address,
      String networkId,
      String addressTag,
      String clientWid)
      throws IOException, CryptoComException {
    Map<String, Object> params = new HashMap<>();
    params.put("currency", currency);
    params.put("amount", amount);
    params.put("address", address);
    if (networkId != null) {
      params.put("network_id", networkId);
    }
    if (addressTag != null) {
      params.put("address_tag", addressTag);
    }
    if (clientWid != null) {
      params.put("client_wid", clientWid);
    }
    CryptoComRequest request = buildRequest("private/create-withdrawal", params);
    CryptoComResponse response = decorateApiCall(() -> cryptoCom.createWithdrawal(request)).call();
    return toObject(response.getResult(), CryptoComWithdrawalRecord.class);
  }

  private Map<String, Object> currencyTimeRangeParams(
      String currency, Long startTime, Long endTime) {
    Map<String, Object> params = new HashMap<>();
    if (currency != null) {
      params.put("currency", currency);
    }
    if (startTime != null) {
      params.put("start_ts", startTime);
    }
    if (endTime != null) {
      params.put("end_ts", endTime);
    }
    return params;
  }
}
