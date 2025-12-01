package org.knowm.xchange.binance.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class BinanceSimpleEarnJSONTest {

  @Test
  public void testSimpleAccountUnmarshal() throws IOException {
    InputStream is =
        BinanceSimpleEarnJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/binance/dto/account/example-simple-account.json");

    ObjectMapper mapper = new ObjectMapper();
    BinanceSimpleAccount account = mapper.readValue(is, BinanceSimpleAccount.class);

    assertThat(account).isNotNull();
    assertThat(account.getTotalAmountInBTC()).isEqualByComparingTo(new BigDecimal("0.01067982"));
    assertThat(account.getTotalAmountInUSDT()).isEqualByComparingTo(new BigDecimal("77.13289230"));
    assertThat(account.getTotalFlexibleAmountInBTC()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(account.getTotalFlexibleAmountInUSDT()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(account.getTotalLockedInBTC()).isEqualByComparingTo(new BigDecimal("0.01067982"));
    assertThat(account.getTotalLockedInUSDT()).isEqualByComparingTo(new BigDecimal("77.13289230"));
  }

  @Test
  public void testFlexiblePositionUnmarshal() throws IOException {
    InputStream is =
        BinanceSimpleEarnJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/binance/dto/account/example-flexible-position.json");

    ObjectMapper mapper = new ObjectMapper();
    BinanceFlexiblePositionResponse response =
        mapper.readValue(is, BinanceFlexiblePositionResponse.class);

    assertThat(response).isNotNull();
    assertThat(response.getTotal()).isEqualTo(2L);
    assertThat(response.getData()).hasSize(2);

    // Test first position (USDT)
    BinanceFlexiblePosition usdtPosition = response.getData().get(0);
    assertThat(usdtPosition.getAsset()).isEqualTo("USDT");
    assertThat(usdtPosition.getTotalAmount()).isEqualByComparingTo(new BigDecimal("75.46000000"));
    assertThat(usdtPosition.getLatestAnnualPercentageRate())
        .isEqualByComparingTo(new BigDecimal("0.02599895"));
    assertThat(usdtPosition.getCumulativeTotalRewards())
        .isEqualByComparingTo(new BigDecimal("0.45459183"));
    assertThat(usdtPosition.getProductId()).isEqualTo("USDT001");
    assertThat(usdtPosition.getCanRedeem()).isTrue();
    assertThat(usdtPosition.getAutoSubscribe()).isTrue();
    assertThat(usdtPosition.getTierAnnualPercentageRate()).isNotNull();
    assertThat(usdtPosition.getTierAnnualPercentageRate().get("0-5BTC"))
        .isEqualByComparingTo(new BigDecimal("0.05"));

    // Test second position (BTC)
    BinanceFlexiblePosition btcPosition = response.getData().get(1);
    assertThat(btcPosition.getAsset()).isEqualTo("BTC");
    assertThat(btcPosition.getTotalAmount()).isEqualByComparingTo(new BigDecimal("0.50000000"));
    assertThat(btcPosition.getLatestAnnualPercentageRate())
        .isEqualByComparingTo(new BigDecimal("0.05000000"));
    assertThat(btcPosition.getCumulativeTotalRewards())
        .isEqualByComparingTo(new BigDecimal("0.00013699"));
    assertThat(btcPosition.getProductId()).isEqualTo("BTC001");
    assertThat(btcPosition.getAutoSubscribe()).isFalse();
  }

  @Test
  public void testLockedPositionUnmarshal() throws IOException {
    InputStream is =
        BinanceSimpleEarnJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/binance/dto/account/example-locked-position.json");

    ObjectMapper mapper = new ObjectMapper();
    BinanceLockedPositionResponse response =
        mapper.readValue(is, BinanceLockedPositionResponse.class);

    assertThat(response).isNotNull();
    assertThat(response.getTotal()).isEqualTo(2L);
    assertThat(response.getData()).hasSize(2);

    // Test first position (AXS)
    BinanceLockedPosition axsPosition = response.getData().get(0);
    assertThat(axsPosition.getAsset()).isEqualTo("AXS");
    assertThat(axsPosition.getPositionId()).isEqualTo(123123L);
    assertThat(axsPosition.getParentPositionId()).isEqualTo(123122L);
    assertThat(axsPosition.getProjectId()).isEqualTo("Axs*90");
    assertThat(axsPosition.getAmount()).isEqualByComparingTo(new BigDecimal("122.09202928"));
    assertThat(axsPosition.getApy()).isEqualByComparingTo(new BigDecimal("0.2032"));
    assertThat(axsPosition.getRewardAmt()).isEqualByComparingTo(new BigDecimal("5.17181528"));
    assertThat(axsPosition.getDuration()).isEqualTo(60);
    assertThat(axsPosition.getAccrualDays()).isEqualTo(4);
    assertThat(axsPosition.getRewardAsset()).isEqualTo("AXS");
    assertThat(axsPosition.getExtraRewardAsset()).isEqualTo("BNB");
    assertThat(axsPosition.getExtraRewardAPR()).isEqualByComparingTo(new BigDecimal("0.0203"));
    assertThat(axsPosition.getCanRedeemEarly()).isTrue();
    assertThat(axsPosition.getAutoSubscribe()).isTrue();
    assertThat(axsPosition.getType()).isEqualTo("AUTO");
    assertThat(axsPosition.getStatus()).isEqualTo("HOLDING");

    // Test second position (ETH)
    BinanceLockedPosition ethPosition = response.getData().get(1);
    assertThat(ethPosition.getAsset()).isEqualTo("ETH");
    assertThat(ethPosition.getPositionId()).isEqualTo(123124L);
    assertThat(ethPosition.getParentPositionId()).isNull();
    assertThat(ethPosition.getProjectId()).isEqualTo("ETH*30");
    assertThat(ethPosition.getAmount()).isEqualByComparingTo(new BigDecimal("10.50000000"));
    assertThat(ethPosition.getApy()).isEqualByComparingTo(new BigDecimal("0.0500"));
    assertThat(ethPosition.getRewardAmt()).isEqualByComparingTo(new BigDecimal("0.04315068"));
    assertThat(ethPosition.getDuration()).isEqualTo(30);
    assertThat(ethPosition.getExtraRewardAsset()).isNull();
    assertThat(ethPosition.getExtraRewardAPR()).isNull();
    assertThat(ethPosition.getCanRedeemEarly()).isFalse();
    assertThat(ethPosition.getAutoSubscribe()).isFalse();
    assertThat(ethPosition.getType()).isEqualTo("NORMAL");
  }
}
