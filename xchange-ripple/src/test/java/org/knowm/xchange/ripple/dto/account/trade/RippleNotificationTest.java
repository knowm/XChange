package org.knowm.xchange.ripple.dto.account.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import org.junit.Test;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.trade.RippleNotifications;
import org.knowm.xchange.ripple.dto.trade.RippleNotifications.RippleNotification;

public class RippleNotificationTest {

  @Test
  public void notificationUnmarshalTest() throws IOException, ParseException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/ripple/dto/trade/example-notifications.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleNotifications response = mapper.readValue(is, RippleNotifications.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getNotifications()).hasSize(11);

    final RippleNotification orderType = response.getNotifications().get(9);
    assertThat(orderType.getAccount()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(orderType.getType()).isEqualTo("order");
    assertThat(orderType.getDirection()).isEqualTo("incoming");
    assertThat(orderType.getState()).isEqualTo("validated");
    assertThat(orderType.getResult()).isEqualTo("tesSUCCESS");
    assertThat(orderType.getLedger()).isEqualTo(14024693);
    assertThat(orderType.getHash())
        .isEqualTo("BFFD04119882BF26B68E27090EE44C0074EAAFA92E0248C979511808193522D5");
    assertThat(orderType.getTimestamp())
        .isEqualTo(RippleExchange.ToDate("2015-06-13T11:45:20.102Z"));
    assertThat(orderType.getTransactionUrl())
        .isEqualTo(
            "api.ripple.com/v1/accounts/rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B/orders/BFFD04119882BF26B68E27090EE44C0074EAAFA92E0248C979511808193522D5");
    assertThat(orderType.getPreviousHash())
        .isEqualTo("F2DCD35FB1D2E9951B56BD773D67345C4955CB41D8A43DB3D2BF5AE9E2F831C6");
    assertThat(orderType.getPreviousNotificationUrl())
        .isEqualTo(
            "api.ripple.com/v1/accounts/rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B/notifications/F2DCD35FB1D2E9951B56BD773D67345C4955CB41D8A43DB3D2BF5AE9E2F831C6");
    assertThat(orderType.getNextHash())
        .isEqualTo("GHRE072948B95345396B2D9A364363GDE521HRT67QQRGGRTHYTRUP0RRB631107");
    assertThat(orderType.getNextNotificationUrl())
        .isEqualTo(
            "api.ripple.com/v1/accounts/rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B/notifications/GHRE072948B95345396B2D9A364363GDE521HRT67QQRGGRTHYTRUP0RRB631107");
  }
}
