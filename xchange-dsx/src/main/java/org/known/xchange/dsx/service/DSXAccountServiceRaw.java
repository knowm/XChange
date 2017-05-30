package org.known.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.known.xchange.dsx.dto.account.DSXAccountInfo;
import org.known.xchange.dsx.dto.account.DSXAccountInfoReturn;
import org.known.xchange.dsx.dto.account.DSXCryptoDepositAddressReturn;
import org.known.xchange.dsx.dto.account.DSXCryptoWithdrawReturn;
import org.known.xchange.dsx.dto.account.DSXTransaction;
import org.known.xchange.dsx.dto.account.DSXTransactionReturn;

/**
 * @author Mikhail Wall
 */
public class DSXAccountServiceRaw extends DSXBaseService {
    
  /**
   * Constructor
   *
   * @param exchange
   */
  public DSXAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   *
   * @return DSXAccountInfo[transactionCount=0, openOrderes=3, serverTime=1,494,239,098, rights=Rights[info=true, trade=true, withdraw={2}],
   * funds='{BTC=129.6642376500, USD=69208.3700000000, EUR=100000.0000000000, LTC=10000.0000000000, RUB=100000.0000000000}']
   * @throws IOException
   */
  public DSXAccountInfo getDSXAccountInfo() throws IOException {

    DSXAccountInfoReturn info = dsx.getInfo(apiKey, signatureCreator, exchange.getNonceFactory());

    checkResult(info);
    return info.getReturnValue();
  }

  /**
   *
   * @param currency Currency to withdraw
   * @param address Withdrawall address
   * @param amount Amount of withdrawal
   * @param commission Amount of commission
   * @return Transaction ID
   * @throws IOException
   */
  public String withdrawCrypto(String currency, String address, BigDecimal amount, BigDecimal commission) throws IOException {
    DSXCryptoWithdrawReturn info = dsx.cryptoWithdraw(apiKey, signatureCreator, exchange.getNonceFactory(), currency, address, amount, commission);
    checkResult(info);
    return String.valueOf(info.getReturnValue().getTransactionId());
  }

  /**
   *
   * @param currency Currency for getting address
   * @param newAddress 0 - get old address, 1 - generate new address
   * @return address
   * @throws IOException
   */
  public String requestAddress(String currency, int newAddress) throws IOException {

    DSXCryptoDepositAddressReturn info = dsx.getCryptoDepositAddress(apiKey, signatureCreator, exchange.getNonceFactory(), currency, newAddress);
    checkResult(info);
    return String.valueOf(info.getReturnValue().getCryproAddress());
  }

    /**
     * Get Map of transactions (withdrawals/deposits) from DSX exchange. Not all parameters are required.
     * @param from
     * @param to
     * @param fromId
     * @param told
     * @param type
     * @param status
     * @param currency
     * @return
     * @throws IOException
     */
    public List<DSXTransaction> getDSXTransHistory(Long from, Long to, Long fromId, Long told, DSXTransaction.Type type,
            DSXTransaction.Status status, String currency) throws IOException {
        DSXTransactionReturn dsxTransHistory = dsx.getTransactions(apiKey, signatureCreator, exchange.getNonceFactory(), from, to,
                fromId, told, type, status, currency);
        checkResult(dsxTransHistory);
        return dsxTransHistory.getReturnValue();
    }
}
