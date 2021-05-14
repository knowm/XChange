package info.bitrich.xchangestream.bitso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BitsoTradsTransaction {
    private final String eventType;
    private final String book;
    private final List<BitsoTradesPayload> payload;
    private final String action;
    private final String response;
    private final Long time;

    public BitsoTradsTransaction(
            @JsonProperty("type") String eventType,
            @JsonProperty("book") String book,
            @JsonProperty("payload") List<BitsoTradesPayload> payload,
            @JsonProperty("action") String action,
            @JsonProperty("response") String response,
            @JsonProperty("time") Long time) {

//    currencyPair= BitsoAdapters.adaptSymbol(book);
        this.payload = payload;
        this.book=book;
        this.eventType=eventType;
        this.action=action;
        this.response=response;
        this.time=time;
    }

    public List<BitsoTradesPayload> getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "BitsoWebSocketTransaction{" +
                "eventType='" + eventType + '\'' +
                ", book='" + book + '\'' +
                ", payload=" + payload +
                ", action='" + action + '\'' +
                ", response='" + response + '\'' +
                ", time=" + time +
                '}';
    }

    public String getAction() {
        return action;
    }

    public String getResponse() {
        return response;
    }

    public Long getTime() {
        return time;
    }

    public String getEventType() {
        return eventType;
    }

    public String getBook() {
        return book;
    }

    public BitsoTrades toBitsoTrade() {
        return new BitsoTrades("", payload.get(0).getOrderId(), new BigDecimal(payload.get(0).getRate()), new BigDecimal(payload.get(0).getAmount()), payload.get(0).getType(), payload.get(0).getMakerOrderId(), payload.get(0).getTakerOrderId());
    }
}
