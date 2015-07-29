package com.xeiam.xchange.ripple.dto.account.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.ripple.dto.trade.RippleNotifications;
import com.xeiam.xchange.ripple.dto.trade.RippleNotifications.RippleNotification;

public class RippleNotificationTest {

  @Test
  public void orderEntryResponseUnmarshalTest() throws IOException, ParseException {
    // Read in the JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-notifications.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleNotifications response = mapper.readValue(is, RippleNotifications.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(response.isSuccess()).isEqualTo(true);
    assertThat(response.getNotifications()).hasSize(10);

    final RippleNotification lastNotification = response.getNotifications().get(9);
    assertThat(lastNotification.getAccount()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(lastNotification.getType()).isEqualTo("order");
    assertThat(lastNotification.getDirection()).isEqualTo("incoming");
    assertThat(lastNotification.getState()).isEqualTo("validated");
    assertThat(lastNotification.getResult()).isEqualTo("tesSUCCESS");
    assertThat(lastNotification.getLedger()).isEqualTo(14024693);
    assertThat(lastNotification.getHash()).isEqualTo("BFFD04119882BF26B68E27090EE44C0074EAAFA92E0248C979511808193522D5");
    assertThat(lastNotification.getTimestamp()).isEqualTo(RippleExchange.ToDate("2015-06-13T11:45:20.102Z"));
    assertThat(lastNotification.getTransactionUrl()).isEqualTo(
        "api.ripple.com/v1/accounts/rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B/orders/BFFD04119882BF26B68E27090EE44C0074EAAFA92E0248C979511808193522D5");
    assertThat(lastNotification.getPreviousHash()).isEqualTo("F2DCD35FB1D2E9951B56BD773D67345C4955CB41D8A43DB3D2BF5AE9E2F831C6");
    assertThat(lastNotification.getPreviousNotificationUrl()).isEqualTo(
        "api.ripple.com/v1/accounts/rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B/notifications/F2DCD35FB1D2E9951B56BD773D67345C4955CB41D8A43DB3D2BF5AE9E2F831C6");
    assertThat(lastNotification.getNextHash()).isEqualTo("");
    assertThat(lastNotification.getNextNotificationUrl()).isEqualTo("");
  }
}
