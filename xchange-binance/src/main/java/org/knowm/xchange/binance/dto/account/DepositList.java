package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.knowm.xchange.binance.dto.account.DepositList.BinanceDeposit;

public final class DepositList extends WapiResponse<List<BinanceDeposit>> {

  private final BinanceDeposit[] depositList;

  public DepositList(
      @JsonProperty("depositList") BinanceDeposit[] depositList,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.depositList = depositList;
  }

  @Override
  public List<BinanceDeposit> getData() {
    return Arrays.asList(depositList);
  }

  @Override
  public String toString() {
    return "DepositList [depositList=" + Arrays.toString(depositList) + "]";
  }

  @Data
  public static final class BinanceDeposit {
    /*
    {
             "insertTime":1509505623000,
             "amount":32.000000000000000000,
             "asset":"BTC",
             "status":1
                   }
             */

    private long insertTime;
    private BigDecimal amount;
    private String asset;
    private String txId;
    private String address;
    private String addressTag;

    /** (0:pending,1:success) */
    private int status;
  }
}
