package org.knowm.xchange.coinfloor.dto.streaming.account;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author obsessiveOrange
 */
public class CoinfloorTradeVolumeTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorTradeVolumeTest.class.getResourceAsStream("/account/example-tradeVolume-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorTradeVolume testObject = mapper.readValue(is, CoinfloorTradeVolume.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(102, testObject.getTag());
    Assert.assertEquals(BigDecimal.valueOf(40070, 4), testObject.getAssetVol());
  }
}
