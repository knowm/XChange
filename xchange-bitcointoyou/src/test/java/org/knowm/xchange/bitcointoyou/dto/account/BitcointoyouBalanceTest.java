package org.knowm.xchange.bitcointoyou.dto.account;

import static org.assertj.core.api.Assertions.entry;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.bitcointoyou.BitcointoyouAdaptersTest;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests the {@link BitcointoyouBalance} class.
 *
 * @author Danilo Guimaraes
 */
public class BitcointoyouBalanceTest {

  public static BitcointoyouBalance bitcointoyouBalance;
  private static BitcointoyouBalance bitcointoyouBalanceError;

  @BeforeClass
  public static void setUp() throws Exception {
    bitcointoyouBalance = loadBitcointoyouBalanceFromExampleData();
    bitcointoyouBalanceError = loadBitcointoyouBalanceErrorFromExampleData();
  }

  @Test
  public void testBalance() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(bitcointoyouBalance.getSuccess()).isEqualTo("1");

    // That's always only one Map on the List
    Map<String, BigDecimal> balances = bitcointoyouBalance.getoReturn().get(0);

    softly.assertThat(balances).size().isEqualTo(5);
    softly.assertThat(balances).containsOnlyKeys("BRL", "BTC", "LTC", "DOGE", "DRK");

    softly.assertThat(balances).containsExactly(
        entry("BRL", new BigDecimal("8657.531311027634275")),
        entry("BTC", new BigDecimal("35.460074025529646")),
        entry("LTC", new BigDecimal("9.840918628667236")),
        entry("DOGE", new BigDecimal("5419.490003406479187")),
        entry("DRK", new BigDecimal("0.121461143982142"))
    );

    softly.assertThat(bitcointoyouBalance.getDate()).isEqualTo("2015-08-06 17:28:58.382");
    softly.assertThat(bitcointoyouBalance.getTimestamp()).isEqualTo("1438882138");

    softly.assertAll();
  }

  @Test
  public void testBalanceError() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(bitcointoyouBalanceError.getSuccess()).isEqualTo("0");
    softly.assertThat(bitcointoyouBalanceError.getError()).isEqualTo("A typical error");
    softly.assertThat(bitcointoyouBalanceError.getDate()).isEqualTo("2015-08-06 17:28:58.382");
    softly.assertThat(bitcointoyouBalanceError.getTimestamp()).isEqualTo("1438882138");

    softly.assertAll();

  }

  private static BitcointoyouBalance loadBitcointoyouBalanceFromExampleData() throws IOException {

    return loadBitcointoyouBalance("/account/example-balance-data.json");
  }

  private static BitcointoyouBalance loadBitcointoyouBalanceErrorFromExampleData() throws IOException {

    return loadBitcointoyouBalance("/account/example-balance-data-error.json");
  }

  private static BitcointoyouBalance loadBitcointoyouBalance(String resource) throws IOException {
    InputStream is = BitcointoyouAdaptersTest.class.getResourceAsStream(resource);

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, BitcointoyouBalance.class);
  }
}