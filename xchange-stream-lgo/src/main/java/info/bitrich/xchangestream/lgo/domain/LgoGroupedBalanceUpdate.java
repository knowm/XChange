package info.bitrich.xchangestream.lgo.domain;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

public class LgoGroupedBalanceUpdate {

  private final Map<Currency, Balance> wallet = new ConcurrentHashMap<>();
  private long seq;

  public LgoGroupedBalanceUpdate applySnapshot(long seq, List<Balance> updatedBalances) {
    wallet.clear();
    return applyUpdate(seq, updatedBalances);
  }

  public LgoGroupedBalanceUpdate applyUpdate(long seq, List<Balance> updatedBalances) {
    this.seq = seq;
    updatedBalances.forEach(b -> wallet.put(b.getCurrency(), b));
    return this;
  }

  public long getSeq() {
    return seq;
  }

  public Map<Currency, Balance> getWallet() {
    return wallet;
  }
}
