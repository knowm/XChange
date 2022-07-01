package org.knowm.xchange.dto.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WalletTest {

  @Test
  public void walletJsonMarchallTest() throws JsonProcessingException {

    List<Balance> balances = new ArrayList<>();
    balances.add(new Balance(Currency.BTC, BigDecimal.ONE));
    balances.add(new Balance(Currency.ETH, BigDecimal.ONE, BigDecimal.ONE));
    balances.add(new Balance(Currency.LTC, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE));
    balances.add(new Balance.Builder().currency(Currency.ADA).total(BigDecimal.ONE).build());

    Wallet wallet =
        Wallet.Builder.from(balances)
            .features(new HashSet<>(Collections.singletonList(Wallet.WalletFeature.TRADING)))
            .id("id")
            .name("name")
            .currentLeverage(BigDecimal.ONE)
            .maxLeverage(BigDecimal.TEN)
            .build();

    String json = new ObjectMapper().writeValueAsString(wallet);

    Wallet result = new ObjectMapper().readValue(json, Wallet.class);

    assertThat(result).isInstanceOf(Wallet.class);
    assertThat(result.getBalance(Currency.BTC)).isInstanceOf(Balance.class);
    System.out.println(result);
  }
}
