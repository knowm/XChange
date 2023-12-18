package org.knowm.xchange.btcturk.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderMethods;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTickerTest;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author mertguner
 */
public class BTCTurkOrderTest {

  @Test
  public void testWithStaticData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/trade/example-order-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkOrder btcTurkOrder = mapper.readValue(is, BTCTurkOrder.class);

    assertThat(btcTurkOrder.getPrice()).isEqualTo("900");
    assertThat(btcTurkOrder.getPricePrecision()).isEqualTo("00");

    assertThat(btcTurkOrder.getAmount()).isEqualTo("0");
    assertThat(btcTurkOrder.getAmountPrecision()).isEqualTo("0123");

    assertThat(btcTurkOrder.getOrderType()).isEqualTo(BTCTurkOrderTypes.Sell);
    assertThat(btcTurkOrder.getOrderMethod()).isEqualTo(BTCTurkOrderMethods.LIMIT);

    assertThat(btcTurkOrder.getDenominatorPrecision()).isEqualTo(2);

    assertThat(btcTurkOrder.getPairSymbol().pair).isEqualTo(CurrencyPair.ETH_TRY);

    assertThat(btcTurkOrder.getTotal()).isEqualTo("0");
    assertThat(btcTurkOrder.getTotalPrecision()).isEqualTo("00");

    assertThat(btcTurkOrder.getTriggerPrice()).isEqualTo("0");
    assertThat(btcTurkOrder.getTriggerPricePrecision()).isEqualTo("00");
  }
}
