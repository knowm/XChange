package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CexIOArchivedOrder {
    public final String id;
    public final String type;
    public final String time;
    public final String lastTxTime;
    public final String lastTx;
    public final String pos;
    public final String status;
    public final String symbol1;
    public final String symbol2;
    public final String amount;
    public final String price;
    public final String remains;
    public final String tradingFeeMaker;
    public final String tradingFeeTaker;
    public final String tradingFeeUserVolumeAmount;
    public final String orderId;

    public CexIOArchivedOrder(@JsonProperty("id") String id, @JsonProperty("type") String type, @JsonProperty("time") String time, @JsonProperty("lastTxTime") String lastTxTime,
                              @JsonProperty("lastTx") String lastTx, @JsonProperty("pos") String pos, @JsonProperty("status") String status, @JsonProperty("symbol1") String symbol1,
                              @JsonProperty("symbol2") String symbol2, @JsonProperty("amount") String amount, @JsonProperty("price") String price, @JsonProperty("remains") String remains,
                              @JsonProperty("tradingFeeMaker") String tradingFeeMaker, @JsonProperty("tradingFeeTaker") String tradingFeeTaker, @JsonProperty("tradingFeeUserVolumeAmount") String tradingFeeUserVolumeAmount,
                              @JsonProperty("orderId") String orderId) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.lastTxTime = lastTxTime;
        this.lastTx = lastTx;
        this.pos = pos;
        this.status = status;
        this.symbol1 = symbol1;
        this.symbol2 = symbol2;
        this.amount = amount;
        this.price = price;
        this.remains = remains;
        this.tradingFeeMaker = tradingFeeMaker;
        this.tradingFeeTaker = tradingFeeTaker;
        this.tradingFeeUserVolumeAmount = tradingFeeUserVolumeAmount;
        this.orderId = orderId;
    }

}
