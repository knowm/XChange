package com.xeiam.xchange.bitso;

import java.util.Arrays;

import com.xeiam.xchange.bitso.dto.account.BitsoBalance;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.Wallet;

public final class BitsoAdapters {

  private BitsoAdapters() { }

  public static AccountInfo adaptAccountInfo(BitsoBalance bitsoBalance, String userName) {
    // Adapt to XChange DTOs
    Wallet mxnWallet = new Wallet(Currencies.MXN, bitsoBalance.getMxnBalance());
    Wallet btcWallet = new Wallet(Currencies.BTC, bitsoBalance.getBtcBalance());

    return new AccountInfo(userName, Arrays.asList(mxnWallet, btcWallet));
  }
}
