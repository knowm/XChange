package org.knowm.xchange.ripple.dto.account.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import org.junit.Test;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.dto.RippleAmount;
import org.knowm.xchange.ripple.dto.trade.RipplePaymentTransaction;

public class RipplePaymentTest {

  @Test
  public void passthroughUnmarshalTest() throws IOException, ParseException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-payment-passthrough.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RipplePaymentTransaction response = mapper.readValue(is, RipplePaymentTransaction.class);

    final RipplePaymentTransaction.Payment payment = response.getPayment();

    assertThat(payment.getSourceAccount()).isEqualTo("r9qdKsVyeSTc9P5mDP4s7qucQaLrhTfPcv");
    assertThat(payment.getSourceTag()).isEqualTo("");
    assertThat(payment.getSourceAmount().getCurrency()).isEqualTo("BTC");
    assertThat(payment.getSourceAmount().getValue()).isEqualTo("0.010000000003347");
    assertThat(payment.getSourceAmount().getCounterparty())
        .isEqualTo("r9qdKsVyeSTc9P5mDP4s7qucQaLrhTfPcv");
    assertThat(payment.getSourceSlippage()).isEqualTo("0");

    assertThat(payment.getDestinationAccount()).isEqualTo("r9qdKsVyeSTc9P5mDP4s7qucQaLrhTfPcv");
    assertThat(payment.getDestinationTag()).isEqualTo("");
    assertThat(payment.getDestinationAmount().getCurrency()).isEqualTo("CNY");
    assertThat(payment.getDestinationAmount().getValue()).isEqualTo("17.2701672");
    assertThat(payment.getDestinationAmount().getCounterparty())
        .isEqualTo("r9qdKsVyeSTc9P5mDP4s7qucQaLrhTfPcv");

    assertThat(payment.getInvoiceID()).isEqualTo("");
    assertThat(payment.isNoDirectRipple()).isEqualTo(false);
    assertThat(payment.isPartialPayment()).isEqualTo(false);
    assertThat(payment.getDirection()).isEqualTo("passthrough");
    assertThat(payment.getTimestamp()).isEqualTo(RippleExchange.ToDate("2015-08-07T03:58:10.000Z"));
    assertThat(payment.getFee()).isEqualTo("0.012");
    assertThat(payment.getResult()).isEqualTo("tesSUCCESS");

    assertThat(payment.getBalanceChanges()).hasSize(2);
    final RippleAmount balance1 = payment.getBalanceChanges().get(0);
    assertThat(balance1.getCurrency()).isEqualTo("XRP");
    assertThat(balance1.getValue()).isEqualTo("-349.559725");
    assertThat(balance1.getCounterparty()).isEqualTo("");

    final RippleAmount balance2 = payment.getBalanceChanges().get(1);
    assertThat(balance2.getCurrency()).isEqualTo("BTC");
    assertThat(balance2.getValue()).isEqualTo("0.009941478580724");
    assertThat(balance2.getCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");

    assertThat(payment.getSourceBalanceChanges()).hasSize(3);
    assertThat(payment.getDestinationBalanceChanges()).hasSize(3);

    assertThat(response.getClientResourceId()).isEqualTo("");
    assertThat(response.getHash())
        .isEqualTo("GHRE072948B95345396B2D9A364363GDE521HRT67QQRGGRTHYTRUP0RRB631107");
    assertThat(response.getLedger()).isEqualTo("15103564");
    assertThat(response.getState()).isEqualTo("validated");
    assertThat(response.isSuccess()).isEqualTo(true);
  }
}
