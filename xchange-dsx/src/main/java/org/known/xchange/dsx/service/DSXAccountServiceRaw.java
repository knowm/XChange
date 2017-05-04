package org.known.xchange.dsx.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.known.xchange.dsx.dto.account.DSXAccountInfo;
import org.known.xchange.dsx.dto.account.DSXAccountInfoReturn;
import org.known.xchange.dsx.dto.account.DSXCryptoDepositAddressReturn;
import org.known.xchange.dsx.dto.account.DSXCryptoWithdrawReturn;

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

  public DSXAccountInfo getDSXAccountInfo() throws IOException {

    DSXAccountInfoReturn info = dsx.getInfo(apiKey, signatureCreator, System.currentTimeMillis());

    checkResult(info);
    return info.getReturnValue();
  }

  public String withdrawCrypto(String currency, String address, BigDecimal amount, BigDecimal commission) throws IOException {
    DSXCryptoWithdrawReturn info = dsx.cryptoWithdraw(apiKey, signatureCreator, System.currentTimeMillis(), currency, address, amount, commission);
    checkResult(info);
    return String.valueOf(info.getReturnValue().getTransactionId());
  }

  // newAddress:
  // 0 - get old address
  // 1 - generate new address
  public String requestAddress(String currency, int newAddress) throws IOException {

    DSXCryptoDepositAddressReturn info = dsx.getCryptoDepositAddress(apiKey, signatureCreator, System.currentTimeMillis(), currency, newAddress);
    checkResult(info);
    return String.valueOf(info.getReturnValue().getCryproAddress());
  }
}
