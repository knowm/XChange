package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetErrorAdapter;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetException;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BitgetAccountService extends BitgetAccountServiceRaw implements AccountService {

  public BitgetAccountService(BitgetExchange exchange) {
    super(exchange);
  }


  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<BitgetBalanceDto> spotBalances = getBitgetBalances();
      Wallet wallet = BitgetAdapters.toWallet(spotBalances);
      return new AccountInfo(wallet);

    } catch (BitgetException e) {
      throw BitgetErrorAdapter.adapt(e);
    }
  }

}
