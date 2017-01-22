package org.knowm.xchange.btcchina.service.rest;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.dto.BTCChinaID;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetAccountInfoRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetDepositsRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaGetWithdrawalsRequest;
import org.knowm.xchange.btcchina.dto.account.request.BTCChinaRequestWithdrawalRequest;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetAccountInfoResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetDepositsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaGetWithdrawalsResponse;
import org.knowm.xchange.btcchina.dto.account.response.BTCChinaRequestWithdrawalResponse;

/**
 * @author ObsessiveOrange
 */
public class BTCChinaAccountServiceRaw extends BTCChinaBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCChinaAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BTCChinaGetAccountInfoResponse getBTCChinaAccountInfo() throws IOException {

    return checkResult(btcChina.getAccountInfo(signatureCreator, exchange.getNonceFactory(), new BTCChinaGetAccountInfoRequest()));
  }

  public BTCChinaGetAccountInfoResponse getBTCChinaAccountInfo(String type) throws IOException {

    return checkResult(btcChina.getAccountInfo(signatureCreator, exchange.getNonceFactory(), new BTCChinaGetAccountInfoRequest(type)));
  }

  public BTCChinaGetDepositsResponse getDeposits(String currency) throws IOException {

    return getDeposits(currency, true);
  }

  public BTCChinaGetDepositsResponse getDeposits(String currency, boolean pendingOnly) throws IOException {

    BTCChinaGetDepositsRequest request = new BTCChinaGetDepositsRequest(currency, pendingOnly);
    BTCChinaGetDepositsResponse response = btcChina.getDeposits(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaGetWithdrawalResponse getWithdrawal(long id) throws IOException {

    return getWithdrawal(id, "BTC");
  }

  public BTCChinaGetWithdrawalResponse getWithdrawal(long id, String currency) throws IOException {

    BTCChinaGetWithdrawalRequest request = new BTCChinaGetWithdrawalRequest(id, currency);
    BTCChinaGetWithdrawalResponse response = btcChina.getWithdrawal(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaGetWithdrawalsResponse getWithdrawals(String currency) throws IOException {

    return getWithdrawals(currency, true);
  }

  public BTCChinaGetWithdrawalsResponse getWithdrawals(String currency, boolean pendingOnly) throws IOException {

    BTCChinaGetWithdrawalsRequest request = new BTCChinaGetWithdrawalsRequest(currency, pendingOnly);
    BTCChinaGetWithdrawalsResponse response = btcChina.getWithdrawals(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public BTCChinaResponse<BTCChinaID> withdrawBTCChinaFunds(String currency, BigDecimal amount, String address) throws IOException {

    BTCChinaRequestWithdrawalRequest request = new BTCChinaRequestWithdrawalRequest(currency, amount);
    BTCChinaRequestWithdrawalResponse response = btcChina.requestWithdrawal(signatureCreator, exchange.getNonceFactory(), request);
    return checkResult(response);
  }

  public String requestBTCChinaDepositAddress(String currency) throws IOException {

    BTCChinaResponse<BTCChinaAccountInfo> response = getBTCChinaAccountInfo(BTCChinaGetAccountInfoRequest.PROFILE_TYPE);

    return response.getResult().getProfile().getDepositAddress(currency);
  }

}
