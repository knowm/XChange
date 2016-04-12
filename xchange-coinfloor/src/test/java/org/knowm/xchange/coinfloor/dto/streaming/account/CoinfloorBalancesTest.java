package org.knowm.xchange.coinfloor.dto.streaming.account;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.coinfloor.CoinfloorUtils.CoinfloorCurrency;

/**
 * @author obsessiveOrange
 */
public class CoinfloorBalancesTest {

  @Test
  public void testMapping() throws JsonParseException, JsonMappingException, IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinfloorBalancesTest.class.getResourceAsStream("/account/example-balances-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinfloorBalances testObject = mapper.readValue(is, CoinfloorBalances.class);

    // Verify that the example data was mapped correctly
    Assert.assertEquals(0, testObject.getErrorCode());
    Assert.assertEquals(101, testObject.getTag());
    Assert.assertEquals(2, testObject.getBalances().size());
    Assert.assertEquals(CoinfloorCurrency.BTC, testObject.getBalances().get(0).getAsset());
    Assert.assertEquals(BigDecimal.valueOf(100014718, 4), testObject.getBalances().get(0).getBalance());
    Assert.assertEquals(CoinfloorCurrency.GBP, testObject.getBalances().get(1).getAsset());
    Assert.assertEquals(BigDecimal.valueOf(931913, 2), testObject.getBalances().get(1).getBalance());
  }
}
