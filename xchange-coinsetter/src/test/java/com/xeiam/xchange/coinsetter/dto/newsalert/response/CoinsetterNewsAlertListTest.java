package com.xeiam.xchange.coinsetter.dto.newsalert.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinsetterNewsAlertListTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void test() throws IOException {

    CoinsetterNewsAlertList newsAlertList = mapper.readValue(getClass().getResource("list.json"), CoinsetterNewsAlertList.class);
    assertEquals(3, newsAlertList.getMessageList().length);

    CoinsetterNewsAlert alert = newsAlertList.getMessageList()[0];
    assertEquals("Buy Low, Sell High", alert.getMessage());
    assertEquals(UUID.fromString("128e405c-7683-02e9-b6a3-d7bdb490526e"), alert.getUuid());
    SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd, yyyy");
    format.setTimeZone(TimeZone.getTimeZone("EST"));
    assertEquals("Thu, Apr 10, 2014", format.format(alert.getCreateDate()));
    assertEquals("MARKET", alert.getMessageType());
  }

}
