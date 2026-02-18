package org.knowm.xchange.kraken.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.kraken.dto.account.results.KrakenEarnAllocationsResult;

public class KrakenEarnAllocationsJSONTest {

  @Test
  public void testEarnAllocationsUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenEarnAllocationsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kraken/dto/account/example-earnallocations-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenEarnAllocationsResult krakenResult =
        mapper.readValue(is, KrakenEarnAllocationsResult.class);
    KrakenEarnAllocations allocations = krakenResult.getResult();

    assertThat(allocations).isNotNull();
    assertThat(allocations.getConvertedAsset()).isEqualTo("USD");
    assertThat(allocations.getTotalAllocated()).isEqualByComparingTo(new BigDecimal("1000.50"));
    assertThat(allocations.getTotalRewarded()).isEqualByComparingTo(new BigDecimal("5.25"));
    assertThat(allocations.getItems()).hasSize(2);

    // Test first allocation (BTC)
    KrakenEarnAllocation btcAllocation = allocations.getItems().get(0);
    assertThat(btcAllocation.getNativeAsset()).isEqualTo("BTC");
    assertThat(btcAllocation.getStrategyId()).isEqualTo("strategy-1");

    // Test amount allocated
    KrakenEarnAmountAllocated amountAllocated = btcAllocation.getAmountAllocated();
    assertThat(amountAllocated).isNotNull();
    assertThat(amountAllocated.getTotal().getNativeAmount())
        .isEqualByComparingTo(new BigDecimal("0.1"));
    assertThat(amountAllocated.getTotal().getConvertedAmount())
        .isEqualByComparingTo(new BigDecimal("5000.00"));

    // Test pending
    assertThat(amountAllocated.getPending().getNativeAmount())
        .isEqualByComparingTo(new BigDecimal("0.01"));
    assertThat(amountAllocated.getPending().getConvertedAmount())
        .isEqualByComparingTo(new BigDecimal("500.00"));

    // Test bonding state
    KrakenEarnAmountAllocated.State bonding = amountAllocated.getBonding();
    assertThat(bonding).isNotNull();
    assertThat(bonding.getNativeAmount()).isEqualByComparingTo(new BigDecimal("0.05"));
    assertThat(bonding.getAllocationCount()).isEqualTo(1);
    assertThat(bonding.getAllocations()).hasSize(1);
    assertThat(bonding.getAllocations().get(0).getNativeAmount())
        .isEqualByComparingTo(new BigDecimal("0.05"));

    // Test total rewarded
    assertThat(btcAllocation.getTotalRewarded().getNativeAmount())
        .isEqualByComparingTo(new BigDecimal("0.001"));
    assertThat(btcAllocation.getTotalRewarded().getConvertedAmount())
        .isEqualByComparingTo(new BigDecimal("50.00"));

    // Test payout
    KrakenEarnPayout payout = btcAllocation.getPayout();
    assertThat(payout).isNotNull();
    assertThat(payout.getAccumulatedReward().getNativeAmount())
        .isEqualByComparingTo(new BigDecimal("0.0005"));
    assertThat(payout.getEstimatedReward().getNativeAmount())
        .isEqualByComparingTo(new BigDecimal("0.0005"));

    // Test second allocation (ETH)
    KrakenEarnAllocation ethAllocation = allocations.getItems().get(1);
    assertThat(ethAllocation.getNativeAsset()).isEqualTo("ETH");
    assertThat(ethAllocation.getStrategyId()).isEqualTo("strategy-2");
    assertThat(ethAllocation.getAmountAllocated().getBonding()).isNull();
    assertThat(ethAllocation.getAmountAllocated().getUnbonding()).isNull();
    assertThat(ethAllocation.getAmountAllocated().getExitQueue()).isNull();
  }
}
