package org.knowm.xchange.dragonex;

import java.util.function.Function;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dragonex.dto.account.CoinPrepay;
import org.knowm.xchange.dragonex.dto.account.CoinWithdraw;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;

public class DragonexAdapters {

  public static FundingRecord adaptFundingRecord(CoinPrepay p, Function<Long, String> currency) {
    return new FundingRecord.Builder()
        .setInternalId(Long.toString(p.prepayId))
        .setAddress(p.addr)
        .setAmount(p.volume)
        .setCurrency(Currency.getInstance(currency.apply(p.coinId)))
        .setType(Type.DEPOSIT)
        .setStatus(convertStatus(p.status))
        .setBlockchainTransactionHash(p.txId)
        .setDescription(p.tag)
        .setDate(p.getArriveTime())
        .build();
  }

  public static FundingRecord adaptFundingRecord(CoinWithdraw p, Function<Long, String> currency) {
    return new FundingRecord.Builder()
        .setInternalId(Long.toString(p.withdrawId))
        .setAddress(p.addr)
        .setAmount(p.volume)
        .setCurrency(Currency.getInstance(currency.apply(p.coinId)))
        .setType(Type.WITHDRAWAL)
        .setStatus(convertStatus(p.status))
        .setBlockchainTransactionHash(p.txId)
        .setDescription(p.rejectReason)
        .setDate(p.getArriveTime())
        .build();
  }

  private static Status convertStatus(int status) {
    // status, 1-to be reviewed; 2 - confirmed; 3 - successful; 4 - coin failed; 5 - failed
    switch (status) {
      case 1:
      case 2:
        return Status.PROCESSING;
      case 3:
        return Status.COMPLETE;
      case 4:
      case 5:
        return Status.FAILED;
      default:
        throw new RuntimeException("Unknown status value: " + status);
    }
  }
}
