package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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

  public static final class BinanceWithdraw {
    /*
    {
             "amount":30.00000000,
             "address":"1JcxZvwsmmE2Dx3K4vSsCPWEJKo2ofsKXW",
             "successTime":1509295869000,
             "txId":"b14b92b1960b0b2e1f52f9be82a2f3ae7477593d20a4195a3bd471c342b4370c",
             "asset":"BTC",
             "applyTime":1509293557000,
             "status":6
          }
             */

    public final BigDecimal amount;
    public final String address;
    public final long successTime;
    public final String txId;
    public final String id;
    public final String asset;
    public final long applyTime;
    /**
     * (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed)
     */
    public final int status;

    public BinanceWithdraw(
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("address") String address,
        @JsonProperty("successTime") long successTime,
        @JsonProperty("txId") String txId,
        @JsonProperty("id") String id,
        @JsonProperty("asset") String asset,
        @JsonProperty("applyTime") long applyTime,
        @JsonProperty("status") int status) {
      super();
      this.amount = amount;
      this.address = address;
      this.successTime = successTime;
      this.txId = txId;
      this.id = id;
      this.asset = asset;
      this.applyTime = applyTime;
      this.status = status;
    }

    @Override
    public String toString() {
      return "BinanceWithdraw [amount="
          + amount
          + ", address="
          + address
          + ", successTime="
          + successTime
          + ", txId="
          + txId
          + ", id="
          + id
          + ", asset="
          + asset
          + ", applyTime="
          + applyTime
          + ", status="
          + status
          + "]";
    }
  }
}
