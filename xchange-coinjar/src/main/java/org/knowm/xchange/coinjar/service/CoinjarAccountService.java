package org.knowm.xchange.coinjar.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinjar.CoinjarErrorAdapter;
import org.knowm.xchange.coinjar.CoinjarException;
import org.knowm.xchange.coinjar.CoinjarExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class CoinjarAccountService extends CoinjarAccountServiceRaw implements AccountService {

  public CoinjarAccountService(CoinjarExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<Wallet> wallets =
          getAccounts().stream()
              .map(
                  wallet ->
                      Wallet.Builder.from(
                              Collections.singleton(
                                  new Balance(
                                      Currency.getInstance(wallet.assetCode),
                                      new BigDecimal(wallet.balance),
                                      new BigDecimal(wallet.available))))
                          .id(wallet.number)
                          .build())
              .collect(Collectors.toList());
      return new AccountInfo(wallets);
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }
}
