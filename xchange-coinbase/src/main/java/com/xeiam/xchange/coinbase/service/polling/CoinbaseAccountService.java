package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddress;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction.CoinbaseSendMoneyRequest;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUsers;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

public final class CoinbaseAccountService extends CoinbaseAccountServiceRaw implements PollingAccountService {

  public CoinbaseAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    final CoinbaseUsers users = super.getCoinbaseUsers();
    return CoinbaseAdapters.adaptAccountInfo(users.getUsers().get(0));
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) throws IOException {

    final CoinbaseSendMoneyRequest sendMoneyRequest = CoinbaseTransaction.createSendMoneyRequest(address, Currencies.BTC, amount);
    final CoinbaseTransaction sendMoneyTransaction = super.sendMoneyCoinbaseRequest(sendMoneyRequest);
    return sendMoneyTransaction.getTransactionHash();
  }

  @Override
  public String requestBitcoinDepositAddress(String... arguments) throws IOException {

    final CoinbaseAddress receiveAddress = super.getCoinbaseReceiveAddress();
    return receiveAddress.getAddress();
  }

}
