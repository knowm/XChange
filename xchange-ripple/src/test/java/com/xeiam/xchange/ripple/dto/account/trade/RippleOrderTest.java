package com.xeiam.xchange.ripple.dto.account.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrders;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrdersBody;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderCancelResponse;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryResponse;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryResponseBody;

public class RippleOrderTest {
  
  @Test
  public void orderEntryResponseUnmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = RippleAccount.class.getResourceAsStream("/trade/example-order-entry-response.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderEntryResponse response = mapper.readValue(is, RippleOrderEntryResponse.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getHash()).isEqualTo("71AE74B03DE3B9A06C559AD4D173A362D96B7D2A5AA35F56B9EF21543D627F34");
    assertThat(response.getLedger()).isEqualTo("9592219");
    assertThat(response.getState()).isEqualTo("validated");

    final RippleOrderEntryResponseBody order = response.getOrder();
    assertThat(order.getAccount()).isEqualTo("sn3nxiW7v8KXzPzAqzyHXbSSKNuN9");
    assertThat(order.getFee()).isEqualTo("0.012");
    assertThat(order.getType()).isEqualTo("sell");
    assertThat(order.getSequence()).isEqualTo(99);

    assertThat(order.getTakerPays().getCurrency()).isEqualTo("JPY");
    assertThat(order.getTakerPays().getCounterparty()).isEqualTo("rMAz5ZnK73nyNUL4foAvaxdreczCkG3vA6");
    assertThat(order.getTakerPays().getValue()).isEqualTo("4000");

    assertThat(order.getTakerGets().getCurrency()).isEqualTo("USD");
    assertThat(order.getTakerGets().getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(order.getTakerGets().getValue()).isEqualTo("0.25");
  }

  @Test
  public void orderCancelResponseUnmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = RippleAccount.class.getResourceAsStream("/trade/example-order-cancel-response.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderCancelResponse response = mapper.readValue(is, RippleOrderCancelResponse.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getHash()).isEqualTo("71AE74B03DE3B9A06C559AD4D173A362D96B7D2A5AA35F56B9EF21543D627F34");
    assertThat(response.getLedger()).isEqualTo("9592219");
    assertThat(response.getState()).isEqualTo("validated");
    
    assertThat(response.getOrder().getAccount()).isEqualTo("sn3nxiW7v8KXzPzAqzyHXbSSKNuN9");
    assertThat(response.getOrder().getFee()).isEqualTo("0.012");
    assertThat(response.getOrder().getOfferSequence()).isEqualTo(99);
    assertThat(response.getOrder().getSequence()).isEqualTo(100);
  }

  @Test
  public void accountOrdersUnmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = RippleAccount.class.getResourceAsStream("/trade/example-account-orders.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountOrders response = mapper.readValue(is, RippleAccountOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.isValidated()).isEqualTo(true);
    assertThat(response.getLedger()).isEqualTo("11561783");
    
    final RippleAccountOrdersBody firstOrder = response.getOrders().get(0);
    assertThat(firstOrder.getType()).isEqualTo("buy");
    assertThat(firstOrder.getSequence()).isEqualTo(11);
    assertThat(firstOrder.getPassive()).isEqualTo(false);

    assertThat(firstOrder.getTakerGets().getCurrency()).isEqualTo("CAD");
    assertThat(firstOrder.getTakerGets().getCounterparty()).isEqualTo("rLr7umFScvEZnj3AJzzZjm25yCZYh3tMwc");
    assertThat(firstOrder.getTakerGets().getValue()).isEqualTo("11205.2494363431");

    assertThat(firstOrder.getTakerPays().getCurrency()).isEqualTo("USD");
    assertThat(firstOrder.getTakerPays().getCounterparty()).isEqualTo("rDZBotqkN4MywSxm9HDtX4m7V6SRkFo7By");
    assertThat(firstOrder.getTakerPays().getValue()).isEqualTo("9933.731769807718");

    final RippleAccountOrdersBody lastOrder = response.getOrders().get(response.getOrders().size() - 1);
    assertThat(lastOrder.getType()).isEqualTo("sell");
    assertThat(lastOrder.getSequence()).isEqualTo(21);
    assertThat(lastOrder.getPassive()).isEqualTo(false);

    assertThat(lastOrder.getTakerGets().getCurrency()).isEqualTo("USD");
    assertThat(lastOrder.getTakerGets().getCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(lastOrder.getTakerGets().getValue()).isEqualTo("2");

    assertThat(lastOrder.getTakerPays().getCurrency()).isEqualTo("CAD");
    assertThat(lastOrder.getTakerPays().getCounterparty()).isEqualTo("rLr7umFScvEZnj3AJzzZjm25yCZYh3tMwc");
    assertThat(lastOrder.getTakerPays().getValue()).isEqualTo("2");
  }
}
