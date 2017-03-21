package org.knowm.xchange.coinbase.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.CoinbaseAdapters;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddress;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseSendMoneyRequest;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUsers;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * @author jamespedwards42
 */
public final class CoinbaseAccountService extends CoinbaseAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbaseAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    final CoinbaseUsers users = super.getCoinbaseUsers();
    return CoinbaseAdapters.adaptAccountInfo(users.getUsers().get(0));
  }

  /**
   * @return The Coinbase transaction id for the newly created withdrawal. See
   *         {@link CoinbaseAccountServiceRaw#getCoinbaseTransaction(String transactionIdOrIdemField)} to retreive more information about the
   *         transaction, including the blockchain transaction hash.
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    final CoinbaseSendMoneyRequest sendMoneyRequest = CoinbaseTransaction.createSendMoneyRequest(address, currency.toString(), amount);
    final CoinbaseTransaction sendMoneyTransaction = super.sendMoneyCoinbaseRequest(sendMoneyRequest);
    return sendMoneyTransaction.getId();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    final CoinbaseAddress receiveAddress = super.getCoinbaseReceiveAddress();
    return receiveAddress.getAddress();
  }

}
