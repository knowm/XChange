package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddress;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseSendMoneyRequest;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUsers;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author jamespedwards42
 */
public final class CoinbaseAccountService extends CoinbaseAccountServiceRaw implements PollingAccountService {

  public CoinbaseAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    final CoinbaseUsers users = super.getCoinbaseUsers();
    return CoinbaseAdapters.adaptAccountInfo(users.getUsers().get(0));
  }

  /**
   * @return The Coinbase transaction id for the newly created withdrawal.
   *         See {@link CoinbaseAccountServiceRaw#getCoinbaseTransaction(String transactionIdOrIdemField)} to retreive more information
   *         about the transaction, including the blockchain transaction hash.
   */
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    final CoinbaseSendMoneyRequest sendMoneyRequest = CoinbaseTransaction.createSendMoneyRequest(address, currency, amount);
    final CoinbaseTransaction sendMoneyTransaction = super.sendMoneyCoinbaseRequest(sendMoneyRequest);
    return sendMoneyTransaction.getId();
  }

  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    final CoinbaseAddress receiveAddress = super.getCoinbaseReceiveAddress();
    return receiveAddress.getAddress();
  }

}
