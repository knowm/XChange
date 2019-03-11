package org.knowm.xchange.ripple.dto.account.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import org.junit.Test;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.RippleAmount;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrders;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrdersBody;
import org.knowm.xchange.ripple.dto.trade.RippleOrderCancelResponse;
import org.knowm.xchange.ripple.dto.trade.RippleOrderEntryResponse;
import org.knowm.xchange.ripple.dto.trade.RippleOrderResponseBody;
import org.knowm.xchange.ripple.dto.trade.RippleOrderTransaction;

public class RippleOrderTest {

  @Test
  public void orderEntryResponseUnmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-order-entry-response.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderEntryResponse response = mapper.readValue(is, RippleOrderEntryResponse.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getHash())
        .isEqualTo("71AE74B03DE3B9A06C559AD4D173A362D96B7D2A5AA35F56B9EF21543D627F34");
    assertThat(response.getLedger()).isEqualTo(9592219);
    assertThat(response.getState()).isEqualTo("validated");

    final RippleOrderResponseBody order = response.getOrder();
    assertThat(order.getAccount()).isEqualTo("sn3nxiW7v8KXzPzAqzyHXbSSKNuN9");
    assertThat(order.getFee()).isEqualTo("0.012");
    assertThat(order.getType()).isEqualTo("sell");
    assertThat(order.getSequence()).isEqualTo(99);

    assertThat(order.getTakerPays().getCurrency()).isEqualTo("JPY");
    assertThat(order.getTakerPays().getCounterparty())
        .isEqualTo("rMAz5ZnK73nyNUL4foAvaxdreczCkG3vA6");
    assertThat(order.getTakerPays().getValue()).isEqualTo("4000");

    assertThat(order.getTakerGets().getCurrency()).isEqualTo("USD");
    assertThat(order.getTakerGets().getCounterparty())
        .isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(order.getTakerGets().getValue()).isEqualTo("0.25");
  }

  @Test
  public void orderCancelResponseUnmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-order-cancel-response.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderCancelResponse response =
        mapper.readValue(is, RippleOrderCancelResponse.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getHash())
        .isEqualTo("71AE74B03DE3B9A06C559AD4D173A362D96B7D2A5AA35F56B9EF21543D627F34");
    assertThat(response.getLedger()).isEqualTo(9592219);
    assertThat(response.getState()).isEqualTo("validated");

    assertThat(response.getOrder().getAccount()).isEqualTo("sn3nxiW7v8KXzPzAqzyHXbSSKNuN9");
    assertThat(response.getOrder().getFee()).isEqualTo("0.012");
    assertThat(response.getOrder().getOfferSequence()).isEqualTo(99);
    assertThat(response.getOrder().getSequence()).isEqualTo(100);
  }

  @Test
  public void accountOrdersUnmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/ripple/dto/trade/example-account-orders.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountOrders response = mapper.readValue(is, RippleAccountOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.isValidated()).isEqualTo(true);
    assertThat(response.getLedger()).isEqualTo(11561783);

    final RippleAccountOrdersBody thirdOrder = response.getOrders().get(2);
    assertThat(thirdOrder.getType()).isEqualTo("buy");
    assertThat(thirdOrder.getSequence()).isEqualTo(11);
    assertThat(thirdOrder.getPassive()).isEqualTo(false);

    assertThat(thirdOrder.getTakerGets().getCurrency()).isEqualTo("CAD");
    assertThat(thirdOrder.getTakerGets().getCounterparty())
        .isEqualTo("rLr7umFScvEZnj3AJzzZjm25yCZYh3tMwc");
    assertThat(thirdOrder.getTakerGets().getValue()).isEqualTo("11205.2494363431");

    assertThat(thirdOrder.getTakerPays().getCurrency()).isEqualTo("USD");
    assertThat(thirdOrder.getTakerPays().getCounterparty())
        .isEqualTo("rDZBotqkN4MywSxm9HDtX4m7V6SRkFo7By");
    assertThat(thirdOrder.getTakerPays().getValue()).isEqualTo("9933.731769807718");

    final RippleAccountOrdersBody lastOrder =
        response.getOrders().get(response.getOrders().size() - 1);
    assertThat(lastOrder.getType()).isEqualTo("sell");
    assertThat(lastOrder.getSequence()).isEqualTo(21);
    assertThat(lastOrder.getPassive()).isEqualTo(false);

    assertThat(lastOrder.getTakerGets().getCurrency()).isEqualTo("USD");
    assertThat(lastOrder.getTakerGets().getCounterparty())
        .isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(lastOrder.getTakerGets().getValue()).isEqualTo("2");

    assertThat(lastOrder.getTakerPays().getCurrency()).isEqualTo("CAD");
    assertThat(lastOrder.getTakerPays().getCounterparty())
        .isEqualTo("rLr7umFScvEZnj3AJzzZjm25yCZYh3tMwc");
    assertThat(lastOrder.getTakerPays().getValue()).isEqualTo("2");
  }

  @Test
  public void orderTransactionUnmarshalTest() throws IOException, ParseException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/ripple/dto/trade/example-order-details.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderTransaction response = mapper.readValue(is, RippleOrderTransaction.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getHash())
        .isEqualTo("793D253739246B820A3E1DD4B38717FBDEEFE718501F5987E8B930E711C20C32");
    assertThat(response.getLedger()).isEqualTo(14024458);
    assertThat(response.isValidated()).isEqualTo(true);

    assertThat(response.getTimestamp())
        .isEqualTo(RippleExchange.ToDate("2015-06-13T11:24:40.000Z"));
    assertThat(response.getFee()).isEqualTo("0.012");
    assertThat(response.getAction()).isEqualTo("order_cancel");
    assertThat(response.getDirection()).isEqualTo("outgoing");

    final RippleOrderResponseBody order = response.getOrder();
    assertThat(order.getAccount()).isEqualTo("rDqQUzKUXWgcJbzwjrGw1fZvGEN5dffQYr");
    assertThat(order.getType()).isEqualTo("cancel");
    assertThat(order.getSequence()).isEqualTo(80);
    assertThat(order.getCancelSequence()).isEqualTo(79);

    final RippleAmount balanceChanges = response.getBalanceChanges().get(0);
    assertThat(balanceChanges.getCounterparty()).isEqualTo("");
    assertThat(balanceChanges.getCurrency()).isEqualTo("XRP");
    assertThat(balanceChanges.getValue()).isEqualTo("-0.012");

    final RippleOrderResponseBody orderChanges = response.getOrderChanges().get(0);
    assertThat(orderChanges.getSequence()).isEqualTo(79);
    assertThat(orderChanges.getStatus()).isEqualTo("canceled");

    assertThat(orderChanges.getTakerPays().getCurrency()).isEqualTo("BTC");
    assertThat(orderChanges.getTakerPays().getCounterparty())
        .isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(orderChanges.getTakerPays().getValue()).isEqualTo("0");

    assertThat(orderChanges.getTakerGets().getCurrency()).isEqualTo("XRP");
    assertThat(orderChanges.getTakerGets().getCounterparty()).isEqualTo("");
    assertThat(orderChanges.getTakerGets().getValue()).isEqualTo("0");
  }
}
