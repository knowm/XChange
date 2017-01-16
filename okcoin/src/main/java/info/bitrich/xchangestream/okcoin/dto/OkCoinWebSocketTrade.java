package info.bitrich.xchangestream.okcoin.dto;

import org.knowm.xchange.okcoin.dto.marketdata.OkCoinTrade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OkCoinWebSocketTrade extends OkCoinTrade {
    public OkCoinWebSocketTrade(String[] items) throws ParseException {
        // TODO: fix date parse
        super(new SimpleDateFormat("hh:mm:ss").parse(items[2]).getTime(), new BigDecimal(items[0]), new BigDecimal(items[1]), 0, items[3]);
    }
}
