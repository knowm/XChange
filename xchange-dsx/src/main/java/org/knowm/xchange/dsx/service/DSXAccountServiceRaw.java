package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dsx.DSXAuthenticatedV2;
import org.knowm.xchange.dsx.dto.account.DSXAccountInfo;
import org.knowm.xchange.dsx.dto.account.DSXAccountInfoReturn;
import org.knowm.xchange.dsx.dto.account.DSXCryptoDepositAddressReturn;
import org.knowm.xchange.dsx.dto.account.DSXCryptoWithdrawReturn;
import org.knowm.xchange.dsx.dto.account.DSXFiatWithdrawReturn;
import org.knowm.xchange.dsx.dto.account.DSXTransaction;
import org.knowm.xchange.dsx.dto.account.DSXTransactionReturn;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryResult;
import org.knowm.xchange.dsx.dto.trade.DSXTransHistoryReturn;

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
   * funds='{BTC=129.6642376500, USD=69208.3700000000, EUR=100000.0000000000, LTC=10000.0000000000, RUB=100000.0000000000}'
   * total='{BTC=130, USD=70000, EUR=100000.0000000000, LTC=10000.0000000000, RUB=100000.0000000000}']
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
  public long withdrawCrypto(String currency, String address, BigDecimal amount, BigDecimal commission) throws IOException {
    DSXCryptoWithdrawReturn info = dsx.cryptoWithdraw(apiKey, signatureCreator, exchange.getNonceFactory(), currency, address, amount, commission);
    checkResult(info);
    return info.getReturnValue().getTransactionId();
  }

  public long withdrawFiat(String currency, BigDecimal amount) throws IOException {
    DSXFiatWithdrawReturn info = dsx.fiatWithdraw(apiKey, signatureCreator, exchange.getNonceFactory(), currency, amount);
    checkResult(info);
    return info.getReturnValue().getTransactionId();
  }

  public DSXTransaction submitWithdraw(long transactionId) throws IOException {
    DSXTransactionReturn result = dsx.submitWithdraw(apiKey, signatureCreator, exchange.getNonceFactory(), transactionId);
    checkResult(result);
    return result.getReturnValue();
  }

  public DSXTransaction cancelWithdraw(long transactionId) throws IOException {
    DSXTransactionReturn result = dsx.cancelWithdraw(apiKey, signatureCreator, exchange.getNonceFactory(), transactionId);
    checkResult(result);
    return result.getReturnValue();
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
    return String.valueOf(info.getReturnValue().getAddress());
  }

    /**
     * Get Map of transactions (withdrawals/deposits) from DSX exchange. Not all parameters are required.
     * @param fromId
     * @param toId
     * @param type
     * @param status
     * @param currency
     * @return
     * @throws IOException
     */
    public Map<Long, DSXTransHistoryResult> getDSXTransHistory(Long to, Long fromId, Long toId, DSXAuthenticatedV2.SortOrder sortOrder, Long since, Long end,
        DSXTransHistoryResult.Type type, DSXTransHistoryResult.Status status, String currency) throws IOException {
      DSXTransHistoryReturn dsxTransHistory = dsx.TransHistory(apiKey, signatureCreator, exchange.getNonceFactory(), to, fromId, toId, sortOrder, since,
          end, type, status, currency);
        checkResult(dsxTransHistory);
        return dsxTransHistory.getReturnValue();
    }
}
