package org.knowm.xchange.binance.dto.account;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.binance.dto.account.DepositList.BinanceDeposit;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class DepositList extends WapiResponse<List<BinanceDeposit>> {
    
    private final BinanceDeposit[] depositList;
    
    
    public DepositList(@JsonProperty("depositList") BinanceDeposit[] depositList
            , @JsonProperty("success") boolean success
            , @JsonProperty("msg") String msg) {
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

    public static final class BinanceDeposit {
        /*
{  
         "insertTime":1509505623000,
         "amount":32.000000000000000000,
         "asset":"BTC",
         "status":1
               }
         */
        
        public final long insertTime;
        public final BigDecimal amount;
        public final String asset;
        public final int status;
        
        public BinanceDeposit(@JsonProperty("insertTime") long insertTime
                , @JsonProperty("amount") BigDecimal amount
                , @JsonProperty("asset") String asset
                , @JsonProperty("status") int status) {
            super();
            this.insertTime = insertTime;
            this.amount = amount;
            this.asset = asset;
            this.status = status;
        }

        @Override
        public String toString() {
            return "BinanceDeposit [insertTime=" + insertTime + ", amount=" + amount + ", asset=" + asset + ", status=" + status
                    + "]";
        }
        
    }

}
