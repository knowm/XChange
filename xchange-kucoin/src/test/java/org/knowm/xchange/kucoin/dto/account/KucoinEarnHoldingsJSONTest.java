package org.knowm.xchange.kucoin.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.kucoin.dto.response.KucoinEarnHolding;
import org.knowm.xchange.kucoin.dto.response.KucoinEarnHoldingsResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;

public class KucoinEarnHoldingsJSONTest {

  @Test
  public void testEarnHoldingsUnmarshal() throws IOException {
    InputStream is =
        KucoinEarnHoldingsJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/kucoin/dto/account/example-earn-holdings.json");
    ObjectMapper mapper = new ObjectMapper();
    KucoinResponse<KucoinEarnHoldingsResponse> kucoinResult =
        mapper.readValue(
            is,
            mapper
                .getTypeFactory()
                .constructParametricType(KucoinResponse.class, KucoinEarnHoldingsResponse.class));
    KucoinEarnHoldingsResponse holdings = kucoinResult.getData();

    assertThat(holdings).isNotNull();
    assertThat(holdings.getTotalNum()).isEqualTo(2);
    assertThat(holdings.getTotalPage()).isEqualTo(1);
    assertThat(holdings.getCurrentPage()).isEqualTo(1);
    assertThat(holdings.getPageSize()).isEqualTo(15);
    assertThat(holdings.getItems()).isNotNull();
    assertThat(holdings.getItems()).hasSize(2);

    // Test first holding
    KucoinEarnHolding holding1 = holdings.getItems().get(0);
    assertThat(holding1.getOrderId()).isEqualTo("2767291");
    assertThat(holding1.getProductId()).isEqualTo("2611");
    assertThat(holding1.getProductCategory()).isEqualTo("KCS_STAKING");
    assertThat(holding1.getProductType()).isEqualTo("DEMAND");
    assertThat(holding1.getCurrency()).isEqualTo("KCS");
    assertThat(holding1.getIncomeCurrency()).isEqualTo("KCS");
    assertThat(holding1.getReturnRate()).isEqualByComparingTo(new BigDecimal("0.03471727"));
    assertThat(holding1.getHoldAmount()).isEqualByComparingTo(new BigDecimal("1.5"));
    assertThat(holding1.getRedeemedAmount()).isEqualByComparingTo(new BigDecimal("0.05"));
    assertThat(holding1.getRedeemingAmount()).isEqualByComparingTo(new BigDecimal("0"));
    assertThat(holding1.getLockStartTime()).isEqualTo(1701252000000L);
    assertThat(holding1.getLockEndTime()).isNull();
    assertThat(holding1.getPurchaseTime()).isEqualTo(1729257513000L);
    assertThat(holding1.getRedeemPeriod()).isEqualTo(3);
    assertThat(holding1.getStatus()).isEqualTo("HOLDING");
    assertThat(holding1.getEarlyRedeemSupported()).isEqualTo(0);

    // Test second holding
    KucoinEarnHolding holding2 = holdings.getItems().get(1);
    assertThat(holding2.getOrderId()).isEqualTo("2767292");
    assertThat(holding2.getProductId()).isEqualTo("2172");
    assertThat(holding2.getProductCategory()).isEqualTo("DEMAND");
    assertThat(holding2.getCurrency()).isEqualTo("BTC");
    assertThat(holding2.getHoldAmount()).isEqualByComparingTo(new BigDecimal("0.1"));
    assertThat(holding2.getReturnRate()).isEqualByComparingTo(new BigDecimal("0.00047208"));
    assertThat(holding2.getRedeemedAmount()).isEqualByComparingTo(new BigDecimal("0.001"));
  }
}
