package org.knowm.xchange.utils.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.instrument.Instrument;

public class InstrumentDeserializer extends JsonDeserializer<Instrument> {

  public InstrumentDeserializer() {
    this(null);
  }

  public InstrumentDeserializer(Class<?> vc) {
    super();
  }

  @Override
  public Instrument deserialize(JsonParser jsonParser, final DeserializationContext ctxt)
      throws IOException {

    final ObjectCodec oc = jsonParser.getCodec();
    final JsonNode node = oc.readTree(jsonParser);
    final String instrumentString = node.asText();
    long count = instrumentString.chars().filter(ch -> ch == '/').count();

    if (count == 1) return new CurrencyPair(instrumentString);
    else return null;
  }
}
