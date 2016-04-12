package org.knowm.xchange.coinsetter.dto.pricealert.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.Test;

import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterPriceAlertListTest {

  @Test
  public void test() throws IOException {

    CoinsetterPriceAlertList priceAlertList = ObjectMapperHelper.readValue(getClass().getResource("list.json"), CoinsetterPriceAlertList.class);
    assertEquals(2, priceAlertList.getPriceAlerts().length);

    CoinsetterPriceAlert alert = priceAlertList.getPriceAlerts()[0];
    assertEquals(new BigDecimal("800"), alert.getPrice());
    assertEquals("CROSSES", alert.getCondition());
    assertEquals(UUID.fromString("f5b02181-fb50-42ca-969f-afc20e91963a"), alert.getUuid());
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setTimeZone(TimeZone.getTimeZone("EST"));
    assertEquals("2014-04-18", format.format(alert.getCreateDate()));
    assertEquals("TEXT", alert.getType());
  }

}
