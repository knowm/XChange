package org.knowm.xchange.mexbt.dto.marketdata;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTOrderBookTest {

  @Test
  public void testMeXBTOrderBook() throws JsonParseException, JsonMappingException, IOException {
    MeXBTOrderBook meXBTOrderBook = new ObjectMapper().readValue(MeXBTOrderBookTest.class.getResource("order-book.json"), MeXBTOrderBook.class);

    assertEquals(new BigDecimal("238.68"), meXBTOrderBook.getBids()[0][0]);
    assertEquals(new BigDecimal("2.5"), meXBTOrderBook.getBids()[0][1]);

    assertEquals(new BigDecimal("230.62"), meXBTOrderBook.getAsks()[0][0]);
    assertEquals(new BigDecimal("30"), meXBTOrderBook.getAsks()[0][1]);
  }

}
