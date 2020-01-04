package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.knowm.xchange.binance.dto.account.WithdrawList.BinanceWithdraw;

public final class WithdrawList extends WapiResponse<List<BinanceWithdraw>> {

  private final BinanceWithdraw[] withdrawList;

  public WithdrawList(
      @JsonProperty("withdrawList") BinanceWithdraw[] withdrawList,
      @JsonProperty("success") boolean success,
      @JsonProperty("msg") String msg) {
    super(success, msg);
    this.withdrawList = withdrawList;
  }

  @Override
  public List<BinanceWithdraw> getData() {
    return Arrays.asList(withdrawList);
  }

  @Override
  public String toString() {
    return "WithdrawList [withdrawList="
        + Arrays.toString(withdrawList)
        + ", success="
        + success
        + ", msg="
        + msg
        + "]";
  }

  @Data
  public static final class BinanceWithdraw {
    private BigDecimal amount;
    private BigDecimal transactionFee;
    private String address;
    private String addressTag;
    private long successTime;
    private String txId;
    private String id;
    private String asset;
    private long applyTime;
    /**
     * (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed)
     */
    private int status;
  }
}
