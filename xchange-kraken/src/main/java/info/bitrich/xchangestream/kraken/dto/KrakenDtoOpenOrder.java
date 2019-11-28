package info.bitrich.xchangestream.kraken.dto;

import java.math.BigDecimal;

/**
 * https://docs.kraken.com/websockets/#message-openOrders
 */
public class KrakenDtoOpenOrder {
    public String refid;
    public Integer userref;
    public String status;
    public Double opentm;
    public Double starttm;
    public Double expiretm;

    public KrakenDtoDescr descr;

    public BigDecimal vol;
    public BigDecimal vol_exec;
    public BigDecimal cost;
    public BigDecimal fee;
    public BigDecimal avg_price;

    public BigDecimal stopprice;
    public BigDecimal limitprice;
    public String misc;
    public String oflags;

    public static class KrakenDtoDescr {
        public String pair;
        public String type;
        public String ordertype;
        public BigDecimal price;
        public BigDecimal price2;
        public Double leverage;
        public String order;
        public BigDecimal close;
    }
}
