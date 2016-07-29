package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterPairTest {

  @Test
  public void testCoinsetterPair() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterPair[] pairs = ObjectMapperHelper.readValue(getClass().getResource("depth-websockets.json"), CoinsetterPair[].class);
    assertEquals(10, pairs.length);

    assertEquals(new BigDecimal("512.51"), pairs[0].getBid().getPrice());
    assertEquals(new BigDecimal("0.03"), pairs[0].getBid().getSize());
    assertEquals("SMART", pairs[0].getBid().getExchangeId());
    assertEquals(1397661675231L, pairs[0].getBid().getTimeStamp());

    assertEquals(new BigDecimal("514.49"), pairs[0].getAsk().getPrice());
    assertEquals(new BigDecimal("0.51"), pairs[0].getAsk().getSize());
    assertEquals("SMART", pairs[0].getAsk().getExchangeId());
    assertEquals(1397661675231L, pairs[0].getAsk().getTimeStamp());

    assertEquals(new BigDecimal("512.5"), pairs[1].getBid().getPrice());
    assertEquals(new BigDecimal("1.3"), pairs[1].getBid().getSize());
    assertEquals("SMART", pairs[1].getBid().getExchangeId());
    assertEquals(1397661675231L, pairs[1].getBid().getTimeStamp());

    assertEquals(new BigDecimal("514.5"), pairs[1].getAsk().getPrice());
    assertEquals(new BigDecimal("0.49"), pairs[1].getAsk().getSize());
    assertEquals("SMART", pairs[1].getAsk().getExchangeId());
    assertEquals(1397661675231L, pairs[1].getAsk().getTimeStamp());
  }

}
