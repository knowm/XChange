package org.knowm.xchange.bittrex.v1.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexDepositAddressResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexDepositHistory;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexDepositsHistoryResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexWithdrawResponse;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexWithdrawalHistory;
import org.knowm.xchange.bittrex.v1.dto.account.BittrexWithdrawalsHistoryResponse;
import org.knowm.xchange.exceptions.ExchangeException;

public class BittrexAccountServiceRaw extends BittrexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<BittrexBalance> getBittrexAccountInfo() throws IOException {

    BittrexBalancesResponse response = bittrexAuthenticated.balances(apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.getSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public String getBittrexDepositAddress(String currency) throws IOException {

    BittrexDepositAddressResponse response = bittrexAuthenticated.getdepositaddress(apiKey, signatureCreator, exchange.getNonceFactory(), currency);
    if (response.getSuccess()) {
      return response.getResult().getAddress();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public List<BittrexWithdrawalHistory> getWithdrawalsHistory() throws IOException {

    BittrexWithdrawalsHistoryResponse response = bittrexAuthenticated.getwithdrawalhistory(apiKey, signatureCreator, exchange.getNonceFactory());
    if (response.getSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public List<BittrexDepositHistory> getDepositsHistory() throws IOException {

    BittrexDepositsHistoryResponse response = bittrexAuthenticated.getdeposithistory(apiKey, signatureCreator, exchange.getNonceFactory());
    if (response.getSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public String withdraw(String currencyCode, BigDecimal amount, String address, String paymentId) throws IOException {

    BittrexWithdrawResponse response = bittrexAuthenticated.withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), currencyCode,
        amount.toPlainString(), address, paymentId);
    if (response.getSuccess()) {
      return response.getResult().getUuid();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

}