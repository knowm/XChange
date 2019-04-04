package com.okcoin.commons.okex.open.api.bean.swap.result;

public class ApiPositionVO {

    private String liquidation_price;
    private String position;
    private String avail_position;
    private String avg_cost;
    private String settlement_price;
    private String instrument_id;
    private String leverage;
    private String realized_pnl;
    private String side;
    private String timestamp;

    public ApiPositionVO() {
    }

    public ApiPositionVO(String liquidation_price, String position, String avail_position, String avg_cost, String settlement_price, String instrument_id, String leverage, String realized_pnl, String side, String timestamp) {
        this.liquidation_price = liquidation_price;
        this.position = position;
        this.avail_position = avail_position;
        this.avg_cost = avg_cost;
        this.settlement_price = settlement_price;
        this.instrument_id = instrument_id;
        this.leverage = leverage;
        this.realized_pnl = realized_pnl;
        this.side = side;
        this.timestamp = timestamp;
    }

    public String getLiquidation_price() {
        return liquidation_price;
    }

    public void setLiquidation_price(String liquidation_price) {
        this.liquidation_price = liquidation_price;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvail_position() {
        return avail_position;
    }

    public void setAvail_position(String avail_position) {
        this.avail_position = avail_position;
    }

    public String getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(String avg_cost) {
        this.avg_cost = avg_cost;
    }

    public String getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(String settlement_price) {
        this.settlement_price = settlement_price;
    }

    public String getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(String instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getLeverage() {
        return leverage;
    }

    public void setLeverage(String leverage) {
        this.leverage = leverage;
    }

    public String getRealized_pnl() {
        return realized_pnl;
    }

    public void setRealized_pnl(String realized_pnl) {
        this.realized_pnl = realized_pnl;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
