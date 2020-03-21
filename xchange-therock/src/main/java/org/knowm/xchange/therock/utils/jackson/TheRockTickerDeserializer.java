package org.knowm.xchange.therock.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.therock.dto.marketdata.TheRockTicker;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class TheRockTickerDeserializer extends StdDeserializer<TheRockTicker> {
  /** */
  private static final long serialVersionUID = 7519223950854643014L;

  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

  public TheRockTickerDeserializer() {
    this(null);
  }

  public TheRockTickerDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public TheRockTicker deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);

    try {
      CurrencyPair fund_id =
          CurrencyPairDeserializer.getCurrencyPairFromString((node.get("fund_id")).asText());
      Date date = format.parse((node.get("date")).asText());
      BigDecimal bid = new BigDecimal((node.get("bid")).asText());
      BigDecimal ask = new BigDecimal((node.get("ask")).asText());
      BigDecimal last = new BigDecimal((node.get("last")).asText());
      BigDecimal volume = new BigDecimal((node.get("volume")).asText());
      BigDecimal volumeTraded = new BigDecimal((node.get("volume_traded")).asText());
      BigDecimal open = new BigDecimal((node.get("open")).asText());
      BigDecimal high = new BigDecimal((node.get("high")).asText());
      BigDecimal low = new BigDecimal((node.get("low")).asText());
      BigDecimal close = new BigDecimal((node.get("close")).asText());

      return new TheRockTicker(
          fund_id, date, bid, ask, last, volume, volumeTraded, open, high, low, close);
    } catch (ParseException e) {
      throw new IOException(e);
    }
  }
}
