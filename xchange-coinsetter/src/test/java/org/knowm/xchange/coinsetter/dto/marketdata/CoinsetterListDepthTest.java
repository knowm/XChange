package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterListDepthTest {

  @Test
  public void test() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterListDepth depth = ObjectMapperHelper.readValue(getClass().getResource("depth-list.json"), CoinsetterListDepth.class);

    assertEquals(0, depth.getSequenceNumber());

    assertEquals(new BigDecimal("1000.0"), depth.getAsks()[0][0]);
    assertEquals(new BigDecimal("0.92"), depth.getAsks()[0][1]);

    assertEquals(new BigDecimal("0.0"), depth.getAsks()[1][0]);
    assertEquals(new BigDecimal("0.0"), depth.getAsks()[1][1]);

    assertEquals(new BigDecimal("703.0"), depth.getBids()[0][0]);
    assertEquals(new BigDecimal("0.15"), depth.getBids()[0][1]);

    assertEquals(new BigDecimal("700.0"), depth.getBids()[1][0]);
    assertEquals(new BigDecimal("2.0"), depth.getBids()[1][1]);
  }

  @Test
  public void testFullDepth() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterListDepth depth = ObjectMapperHelper.readValue(getClass().getResource("full_depth.json"), CoinsetterListDepth.class);

    assertEquals("SMART", depth.getExchangeId());
    assertEquals(0, depth.getSequenceNumber());
    assertEquals(1323424234L, depth.getTimeStamp().longValue());

    assertEquals(new BigDecimal("1000.0"), depth.getAsks()[0][0]);
    assertEquals(new BigDecimal("0.92"), depth.getAsks()[0][1]);

    assertEquals(new BigDecimal("0.0"), depth.getAsks()[1][0]);
    assertEquals(new BigDecimal("0.0"), depth.getAsks()[1][1]);

    assertEquals(new BigDecimal("703.0"), depth.getBids()[0][0]);
    assertEquals(new BigDecimal("0.15"), depth.getBids()[0][1]);

    assertEquals(new BigDecimal("700.0"), depth.getBids()[1][0]);
    assertEquals(new BigDecimal("2.0"), depth.getBids()[1][1]);

  }

}
