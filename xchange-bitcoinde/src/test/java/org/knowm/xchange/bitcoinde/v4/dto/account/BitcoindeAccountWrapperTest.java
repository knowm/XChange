package org.knowm.xchange.bitcoinde.v4.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.junit.Test;

public class BitcoindeAccountWrapperTest {

  @Test
  public void testBitcoindeAccountWarpper() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeAccountWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/account.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeAccountWrapper bitcoindeAccountWrapper =
        mapper.readValue(is, BitcoindeAccountWrapper.class);

    // Make sure balance values are correct
    assertThat(bitcoindeAccountWrapper.getData()).isNotNull();
    assertThat(bitcoindeAccountWrapper.getData().getBalances()).isNotNull();

    final Map<String, BitcoindeBalance> balances = bitcoindeAccountWrapper.getData().getBalances();
    assertThat(balances).containsOnlyKeys("btc", "eth", "bch", "btg", "bsv");

    final BitcoindeBalance btcBalance = balances.get("btc");
    assertThat(btcBalance.getAvailableAmount()).isEqualByComparingTo("0.009");
    assertThat(btcBalance.getReservedAmount()).isEqualByComparingTo("0");
    assertThat(btcBalance.getTotalAmount()).isEqualByComparingTo("0.009");

    final BitcoindeBalance ethBalance = balances.get("eth");
    assertThat(ethBalance.getAvailableAmount()).isEqualByComparingTo("0.06463044");
    assertThat(ethBalance.getReservedAmount()).isEqualByComparingTo("0");
    assertThat(ethBalance.getTotalAmount()).isEqualByComparingTo("0.06463044");

    final BitcoindeBalance bchBalance = balances.get("bch");
    assertThat(bchBalance.getAvailableAmount()).isEqualByComparingTo("0.008");
    assertThat(bchBalance.getReservedAmount()).isEqualByComparingTo("0");
    assertThat(bchBalance.getTotalAmount()).isEqualByComparingTo("0.008");

    final BitcoindeBalance btgBalance = balances.get("btg");
    assertThat(btgBalance.getAvailableAmount()).isEqualByComparingTo("0.007");
    assertThat(btgBalance.getReservedAmount()).isEqualByComparingTo("0");
    assertThat(btgBalance.getTotalAmount()).isEqualByComparingTo("0.007");

    final BitcoindeBalance bsvBalance = balances.get("bsv");
    assertThat(bsvBalance.getAvailableAmount()).isEqualByComparingTo("0.006");
    assertThat(bsvBalance.getReservedAmount()).isEqualByComparingTo("0");
    assertThat(bsvBalance.getTotalAmount()).isEqualByComparingTo("0.006");

    // Make sure balance fidor reservations are correct
    final BitcoindeFidorReservation fidorReservation =
        bitcoindeAccountWrapper.getData().getFidorReservation();

    assertThat(fidorReservation).isNotNull();
    assertThat(fidorReservation.getAllocation()).isNotNull();
    assertThat(fidorReservation.getTotalAmount()).isEqualByComparingTo("2000");
    assertThat(fidorReservation.getAvailableAmount()).isEqualByComparingTo("2000");
    assertThat(fidorReservation.getReservedAt()).isEqualTo("2018-01-24T10:36:03+01:00");
    assertThat(fidorReservation.getValidUntil()).isEqualTo("2018-01-31T10:36:02+01:00");

    final Map<String, BitcoindeAllocation> allocations = fidorReservation.getAllocation();
    assertThat(allocations).containsOnlyKeys("btc", "eth", "bch", "btg");

    final BitcoindeAllocation btcAllocation = allocations.get("btc");
    assertThat(btcAllocation.getPercent()).isEqualByComparingTo("100");
    assertThat(btcAllocation.getMaxEurVolume()).isEqualByComparingTo("2000");
    assertThat(btcAllocation.getEurVolumeOpenOrders()).isEqualByComparingTo("0");

    final BitcoindeAllocation ethAllocation = allocations.get("eth");
    assertThat(ethAllocation.getPercent()).isEqualByComparingTo("0");
    assertThat(ethAllocation.getMaxEurVolume()).isEqualByComparingTo("0");
    assertThat(ethAllocation.getEurVolumeOpenOrders()).isEqualByComparingTo("0");

    final BitcoindeAllocation bchAllocation = allocations.get("bch");
    assertThat(bchAllocation.getPercent()).isEqualByComparingTo("0");
    assertThat(bchAllocation.getMaxEurVolume()).isEqualByComparingTo("0");
    assertThat(bchAllocation.getEurVolumeOpenOrders()).isEqualByComparingTo("0");

    final BitcoindeAllocation btgAllocation = allocations.get("btg");
    assertThat(btgAllocation.getPercent()).isEqualByComparingTo("0");
    assertThat(btgAllocation.getMaxEurVolume()).isEqualByComparingTo("0");
    assertThat(btgAllocation.getEurVolumeOpenOrders()).isEqualByComparingTo("0");
  }
}
