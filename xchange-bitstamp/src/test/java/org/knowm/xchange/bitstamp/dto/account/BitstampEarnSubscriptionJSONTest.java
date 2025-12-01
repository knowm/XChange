package org.knowm.xchange.bitstamp.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;

public class BitstampEarnSubscriptionJSONTest {

  @Test
  public void testUnmarshal() throws IOException {
    InputStream is =
        BitstampEarnSubscriptionJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/earn-subscriptions.json");

    ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType =
        mapper.getTypeFactory().constructCollectionType(List.class, BitstampEarnSubscription.class);

    List<BitstampEarnSubscription> subscriptions = mapper.readValue(is, collectionType);

    assertThat(subscriptions).isNotEmpty();
    BitstampEarnSubscription subscription = subscriptions.get(0);

    assertThat(subscription.getCurrency()).isNotNull();
    assertThat(subscription.getType()).isIn(BitstampEarnType.STAKING, BitstampEarnType.LENDING);
    assertThat(subscription.getTerm()).isIn(BitstampEarnTerm.FLEXIBLE, BitstampEarnTerm.FIXED);
    assertThat(subscription.getEstimatedAnnualYield()).isNotNull();
    assertThat(subscription.getAmount()).isNotNull();
  }

  @Test
  public void testUnmarshalSingle() throws IOException {
    InputStream is =
        BitstampEarnSubscriptionJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/account/earn-subscription-single.json");

    ObjectMapper mapper = new ObjectMapper();
    BitstampEarnSubscription subscription = mapper.readValue(is, BitstampEarnSubscription.class);

    assertThat(subscription.getCurrency()).isEqualTo(Currency.ETH);
    assertThat(subscription.getType()).isEqualTo(BitstampEarnType.STAKING);
    assertThat(subscription.getTerm()).isEqualTo(BitstampEarnTerm.FLEXIBLE);
    assertThat(subscription.getEstimatedAnnualYield()).isNotNull();
    assertThat(subscription.getAmount()).isNotNull();
    assertThat(subscription.getAvailableAmount()).isNotNull();
  }
}
