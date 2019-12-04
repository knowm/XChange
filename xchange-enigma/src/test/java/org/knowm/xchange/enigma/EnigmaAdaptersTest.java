package org.knowm.xchange.enigma;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.utils.jackson.CurrencyPairDeserializer.getCurrencyPairFromString;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.enigma.dto.trade.EnigmaOrderSubmission;

public class EnigmaAdaptersTest {

  @Test
  public void testTradeAdapter() throws IOException {

    InputStream is =
        EnigmaAdaptersTest.class.getClassLoader().getResourceAsStream("order-submission.json");

    ObjectMapper mapper = new ObjectMapper();
    EnigmaOrderSubmission orderSubmission = mapper.readValue(is, EnigmaOrderSubmission.class);
    Trade trade = EnigmaAdapters.adaptTrade(orderSubmission);

    assertThat(trade.getId()).isEqualTo("195");
    assertThat(trade.getCurrencyPair()).isEqualTo(getCurrencyPairFromString("BTC-USD"));
    assertThat(trade.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("10200.5865"));
  }
}
