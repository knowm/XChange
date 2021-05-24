package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class GateioFeeInfo {

    private final int no;
    private final String symbol;
    private final String name;
    private final String name_cn;
    private final String fee_usdt;
    private final String fee_btc;
    private final String fee_eth;
    private final String deposit;
    private final String withdraw_percent;
    private final String withdraw_fix;
    private final String withdraw_day_limit;
    private final BigDecimal withdraw_amount_mini;
    private final BigDecimal withdraw_day_limit_remain;
    private final BigDecimal withdraw_eachtime_limit;
    private final BigDecimal withdraw_fix_on_chain_ETH;
    private final BigDecimal withdraw_fix_on_chain_BTC;
    private final BigDecimal withdraw_fix_on_chain_TRX;
    private final BigDecimal withdraw_fix_on_chain_EOS;

    public GateioFeeInfo(@JsonProperty("no") int no,
                         @JsonProperty("symbol") String symbol,
                         @JsonProperty("name") String name,
                         @JsonProperty("name_cn") String name_cn,
                         @JsonProperty("fee_usdt") String fee_usdt,
                         @JsonProperty("fee_btc") String fee_btc,
                         @JsonProperty("fee_eth") String fee_eth,
                         @JsonProperty("deposit") String deposit,
                         @JsonProperty("withdraw_percent") String withdraw_percent,
                         @JsonProperty("withdraw_fix") String withdraw_fix,
                         @JsonProperty("withdraw_day_limit") String withdraw_day_limit,
                         @JsonProperty("withdraw_amount_mini") BigDecimal withdraw_amount_mini,
                         @JsonProperty("withdraw_day_limit_remain") BigDecimal withdraw_day_limit_remain,
                         @JsonProperty("withdraw_eachtime_limit") BigDecimal withdraw_eachtime_limit,
                         @JsonProperty("withdraw_fix_on_chain_ETH") BigDecimal withdraw_fix_on_chain_ETH,
                         @JsonProperty("withdraw_fix_on_chain_BTC") BigDecimal withdraw_fix_on_chain_BTC,
                         @JsonProperty("withdraw_fix_on_chain_TRX") BigDecimal withdraw_fix_on_chain_TRX,
                         @JsonProperty("withdraw_fix_on_chain_EOS") BigDecimal withdraw_fix_on_chain_EOS) {

        this.no = no;
        this.symbol = symbol;
        this.name = name;
        this.name_cn = name_cn;
        this.fee_usdt = fee_usdt;
        this.fee_btc = fee_btc;
        this.fee_eth = fee_eth;
        this.deposit = deposit;
        this.withdraw_percent = withdraw_percent;
        this.withdraw_fix = withdraw_fix;
        this.withdraw_day_limit = withdraw_day_limit;
        this.withdraw_amount_mini = withdraw_amount_mini;
        this.withdraw_day_limit_remain = withdraw_day_limit_remain;
        this.withdraw_eachtime_limit = withdraw_eachtime_limit;
        this.withdraw_fix_on_chain_ETH = withdraw_fix_on_chain_ETH;
        this.withdraw_fix_on_chain_BTC = withdraw_fix_on_chain_BTC;
        this.withdraw_fix_on_chain_TRX = withdraw_fix_on_chain_TRX;
        this.withdraw_fix_on_chain_EOS = withdraw_fix_on_chain_EOS;
    }

    @Override
    public String toString() {
        return "GateioFeeInfo{" +
                "no=" + no +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", name_cn='" + name_cn + '\'' +
                ", fee_usdt='" + fee_usdt + '\'' +
                ", fee_btc='" + fee_btc + '\'' +
                ", fee_eth='" + fee_eth + '\'' +
                ", deposit='" + deposit + '\'' +
                ", withdraw_percent='" + withdraw_percent + '\'' +
                ", withdraw_fix='" + withdraw_fix + '\'' +
                ", withdraw_day_limit='" + withdraw_day_limit + '\'' +
                ", withdraw_amount_mini=" + withdraw_amount_mini +
                ", withdraw_day_limit_remain=" + withdraw_day_limit_remain +
                ", withdraw_eachtime_limit=" + withdraw_eachtime_limit +
                ", withdraw_fix_on_chain_ETH=" + withdraw_fix_on_chain_ETH +
                ", withdraw_fix_on_chain_BTC=" + withdraw_fix_on_chain_BTC +
                ", withdraw_fix_on_chain_TRX=" + withdraw_fix_on_chain_TRX +
                ", withdraw_fix_on_chain_EOS=" + withdraw_fix_on_chain_EOS +
                '}';
    }

    public int getNo() {
        return no;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getName_cn() {
        return name_cn;
    }

    public String getFee_usdt() {
        return fee_usdt;
    }

    public String getFee_btc() {
        return fee_btc;
    }

    public String getFee_eth() {
        return fee_eth;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getWithdraw_percent() {
        return withdraw_percent;
    }

    public String getWithdraw_fix() {
        return withdraw_fix;
    }

    public String getWithdraw_day_limit() {
        return withdraw_day_limit;
    }

    public BigDecimal getWithdraw_amount_mini() {
        return withdraw_amount_mini;
    }

    public BigDecimal getWithdraw_day_limit_remain() {
        return withdraw_day_limit_remain;
    }

    public BigDecimal getWithdraw_eachtime_limit() {
        return withdraw_eachtime_limit;
    }

    public BigDecimal getWithdraw_fix_on_chain_ETH() {
        return withdraw_fix_on_chain_ETH;
    }

    public BigDecimal getWithdraw_fix_on_chain_BTC() {
        return withdraw_fix_on_chain_BTC;
    }

    public BigDecimal getWithdraw_fix_on_chain_TRX() {
        return withdraw_fix_on_chain_TRX;
    }

    public BigDecimal getWithdraw_fix_on_chain_EOS() {
        return withdraw_fix_on_chain_EOS;
    }
}
