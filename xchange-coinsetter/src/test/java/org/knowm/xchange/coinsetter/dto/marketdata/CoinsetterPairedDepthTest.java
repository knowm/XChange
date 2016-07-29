package org.knowm.xchange.coinsetter.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterPairedDepthTest {

  @Test
  public void test() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterPairedDepth depth = ObjectMapperHelper.readValue(getClass().getResource("depth-paired.json"), CoinsetterPairedDepth.class);

    assertEquals(10, depth.getDepth());
    assertEquals("SMART", depth.getExchangeId());
    assertEquals(0, depth.getSequenceNumber());

    CoinsetterPair[] topNBidAsks = depth.getTopNBidAsks();
    assertEquals(10, topNBidAsks.length);

    assertEquals(new BigDecimal("500.54"), topNBidAsks[0].getBid().getPrice());
    assertEquals(new BigDecimal("0.10"), topNBidAsks[0].getBid().getSize());
    assertEquals("SMART", topNBidAsks[0].getBid().getExchangeId());
    assertEquals(1396022831136L, topNBidAsks[0].getBid().getTimeStamp());

    assertEquals(new BigDecimal("505.68"), topNBidAsks[0].getAsk().getPrice());
    assertEquals(new BigDecimal("0.05"), topNBidAsks[0].getAsk().getSize());
    assertEquals("SMART", topNBidAsks[0].getAsk().getExchangeId());
    assertEquals(1396022831136L, topNBidAsks[0].getAsk().getTimeStamp());

    assertEquals(new BigDecimal("500.11"), topNBidAsks[1].getBid().getPrice());
    assertEquals(new BigDecimal("1.33"), topNBidAsks[1].getBid().getSize());
    assertEquals("SMART", topNBidAsks[1].getBid().getExchangeId());
    assertEquals(1396022831136L, topNBidAsks[1].getBid().getTimeStamp());

    assertEquals(new BigDecimal("506.6"), topNBidAsks[1].getAsk().getPrice());
    assertEquals(new BigDecimal("4.40"), topNBidAsks[1].getAsk().getSize());
    assertEquals("SMART", topNBidAsks[1].getAsk().getExchangeId());
    assertEquals(1396022831136L, topNBidAsks[1].getAsk().getTimeStamp());

  }

}
