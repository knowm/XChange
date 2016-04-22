package org.knowm.xchange.coinsetter.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;

import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterAccountTest {

  @Test
  public void test() throws IOException {

    CoinsetterAccount coinsetterAccountResponse = ObjectMapperHelper.readValue(getClass().getResource("account.json"), CoinsetterAccount.class);
    assertEquals(UUID.fromString("c7d6d8f6-79e9-4975-9122-d981cd0602a5"), coinsetterAccountResponse.getAccountUuid());
    assertEquals(UUID.fromString("aa72fa7d-06d6-466f-a141-155b22c5e2dc"), coinsetterAccountResponse.getCustomerUuid());
    assertEquals("CST0000001", coinsetterAccountResponse.getAccountNumber());
    assertEquals("My Bitcoin account", coinsetterAccountResponse.getName());
    assertEquals("For primary trading", coinsetterAccountResponse.getDescription());
    assertEquals(new BigDecimal("0E-8"), coinsetterAccountResponse.getBtcBalance());
    assertEquals(new BigDecimal("1000"), coinsetterAccountResponse.getUsdBalance());
    assertEquals("TEST", coinsetterAccountResponse.getAccountClass());
    assertEquals("ACTIVE", coinsetterAccountResponse.getActiveStatus());
    assertEquals(new BigDecimal("1.0000"), coinsetterAccountResponse.getApprovedMarginRatio());
    assertEquals(1376956481571L, coinsetterAccountResponse.getCreateDate().getTime());
  }

}
