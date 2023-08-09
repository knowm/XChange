package info.bitrich.xchangestream.gateio;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import info.bitrich.xchangestream.gateio.dto.TickerDTO;
import info.bitrich.xchangestream.gateio.dto.response.GateioTickerNotification;
import java.util.Date;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.dto.marketdata.Ticker;

@UtilityClass
public class GateioStreamingAdapters {

  private final ObjectMapper MAPPER = new ObjectMapper();

  static {
    // by default read timetamps as milliseconds
    MAPPER.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    // don't fail un unknown properties
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // enable parsing to Instant
    MAPPER.registerModule(new JavaTimeModule());
  }


  @SneakyThrows
  public Ticker toTicker(JsonNode jsonNode) {
    GateioTickerNotification notification = MAPPER.treeToValue(jsonNode, GateioTickerNotification.class);
    TickerDTO tickerDTO = notification.getResult();

    return new Ticker.Builder()
        .timestamp(Date.from(notification.getTime()))
        .instrument(tickerDTO.getCurrencyPair())
        .last(tickerDTO.getLastPrice())
        .ask(tickerDTO.getLowestAsk())
        .bid(tickerDTO.getHighestBid())
        .percentageChange(tickerDTO.getChangePercent24h())
        .volume(tickerDTO.getBaseVolume())
        .quoteVolume(tickerDTO.getQuoteVolume())
        .high(tickerDTO.getHighPrice24h())
        .low(tickerDTO.getLowPrice24h())
        .build();
  }


}
