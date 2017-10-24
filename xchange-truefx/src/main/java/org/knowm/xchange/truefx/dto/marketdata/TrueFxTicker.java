package org.knowm.xchange.truefx.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@JsonPropertyOrder(value = {"pair", "timestamp", "bid", "bidBP", "ask", "askBP", "low", "high", "open"})
public class TrueFxTicker {
  private static final BigDecimal BASIS_POINT_MULTIPLIER = new BigDecimal("0.00001");

  private final String pair;
  private final long timestamp;
  private final BigDecimal bid;
  private final BigDecimal bidBP;
  private final BigDecimal ask;
  private final BigDecimal askBP;

  private final BigDecimal low;
  private final BigDecimal high;
  private final BigDecimal open;

  public TrueFxTicker(@JsonProperty("pair") String pair, @JsonProperty("timestamp") long timestamp, @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("bidBP") BigDecimal bidBP, @JsonProperty("ask") BigDecimal ask, @JsonProperty("askBP") BigDecimal askBP,
      @JsonProperty("low") BigDecimal low, @JsonProperty("high") BigDecimal high, @JsonProperty("open") BigDecimal open) {
    this.pair = pair;
    this.timestamp = timestamp;
    this.bid = bid;
    this.bidBP = bidBP;
    this.ask = ask;
    this.askBP = askBP;
    this.low = low;
    this.high = high;
    this.open = open;
  }

  public String getPair() {
    return pair;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getBid() {
    return bid;
  }

  public BigDecimal getBidBP() {
    return bidBP;
  }

  public BigDecimal getAsk() {
    return ask;
  }

  public BigDecimal getAskBP() {
    return askBP;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal calcBid() {
    return bid.add(BASIS_POINT_MULTIPLIER.multiply(bidBP));
  }

  public BigDecimal calcAsk() {
    return ask.add(BASIS_POINT_MULTIPLIER.multiply(askBP));
  }

  @Override
  public String toString() {
    return "TrueFxTicker [pair=" + pair + ", bid=" + calcBid() + ", ask=" + calcAsk() + ", low=" + low + ", high=" + high + "]";
  }

  public static class TrueFxTickerDeserializer extends JsonDeserializer<TrueFxTicker> {
    private final CsvMapper mapper = new CsvMapper();
    private final CsvSchema schema = mapper.schemaFor(TrueFxTicker.class);

    @Override
    public TrueFxTicker deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
      ArrayNode array = mapper.readerFor(TrueFxTicker.class).with(schema).readTree(parser);

      String pair = array.get(0).asText();
      long timestamp = array.get(1).asLong();
      BigDecimal bid = new BigDecimal(array.get(2).asText());
      BigDecimal bidBP = new BigDecimal(array.get(3).asText());
      BigDecimal ask = new BigDecimal(array.get(4).asText());
      BigDecimal askBP = new BigDecimal(array.get(5).asText());
      BigDecimal low = new BigDecimal(array.get(6).asText());
      BigDecimal high = new BigDecimal(array.get(7).asText());
      BigDecimal open = new BigDecimal(array.get(8).asText());

      return new TrueFxTicker(pair, timestamp, bid, bidBP, ask, askBP, low, high, open);
    }
  }
}
