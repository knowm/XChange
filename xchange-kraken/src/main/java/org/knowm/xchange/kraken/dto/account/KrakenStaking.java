package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.account.results.KrakenTransactionStatus;

import java.math.BigDecimal;

public class KrakenStaking {

    private final String refId;
    private final KrakenStakingType type;
    private final String asset;
    private final BigDecimal amount;
    private final double time;
    private final double bondStart;
    private final double bondEnd;
    private final KrakenTransactionStatus status;

    /**
     * Constructor
     *
     * @param refId
     * @param type
     * @param asset
     * @param amount
     * @param unixTime
     * @param bondStart
     * @param bondEnd
     * @param status
     */
    public KrakenStaking(
            @JsonProperty("refid") String refId,
            @JsonProperty("type") String type,
            @JsonProperty("asset") String asset,
            @JsonProperty("amount") String amount,
            @JsonProperty("time") double unixTime,
            @JsonProperty("bond_start") double bondStart,
            @JsonProperty("bond_end") double bondEnd,
            @JsonProperty("status") String status
    ) {
        this.refId = refId;
        this.type = KrakenStakingType.valueOf(type);
        this.asset = asset;
        this.amount = new BigDecimal(amount);
        this.time = unixTime;
        this.bondStart = bondStart;
        this.bondEnd = bondEnd;
        this.status = KrakenTransactionStatus.valueOf(status);
    }

    public String getRefId() {
        return refId;
    }

    public KrakenStakingType getType() {
        return type;
    }

    public String getAsset() {
        return asset;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public double getTime() {
        return time;
    }

    public double getBondStart() {
        return bondStart;
    }

    public double getBondEnd() {
        return bondEnd;
    }

    public KrakenTransactionStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "KrakenStaking[" +
                "refId='" + refId + '\'' +
                ", type=" + type +
                ", asset='" + asset + '\'' +
                ", amount=" + amount +
                ", time=" + time +
                ", bondStart=" + bondStart +
                ", bondEnd=" + bondEnd +
                ", status=" + status +
                ']';
    }

}
