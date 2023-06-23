package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.math.BigDecimal;
import java.util.Date;

public class KucoinTickerConverter implements Converter<JsonNode, Ticker> {
    @Override
    public Ticker convert(JsonNode jsonNode) {
        return new Ticker.Builder()
                .timestamp(new Date(jsonNode.get("time").longValue()))
                .ask(new BigDecimal(jsonNode.get("bestAsk").asText()))
                .askSize(new BigDecimal(jsonNode.get("bestAskSize").asText()))
                .bid(new BigDecimal(jsonNode.get("bestBid").asText()))
                .bidSize(new BigDecimal(jsonNode.get("bestBidSize").asText()))
                .last(new BigDecimal(jsonNode.get("price").asText()))
                .volume(new BigDecimal(jsonNode.get("size").asText()))
                .build();
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(JsonNode.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(Ticker.class);
    }
}
