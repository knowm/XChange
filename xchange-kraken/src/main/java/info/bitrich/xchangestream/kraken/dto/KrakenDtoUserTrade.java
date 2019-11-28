package info.bitrich.xchangestream.kraken.dto;

import java.math.BigDecimal;

public class KrakenDtoUserTrade {
    public BigDecimal cost;
    public BigDecimal fee;
    public BigDecimal margin;
    public String ordertxid;
    public String ordertype;
    public String pair;
    public String postxid;
    public BigDecimal price;
    public Double time;
    public String type;

    public BigDecimal vol;
}
