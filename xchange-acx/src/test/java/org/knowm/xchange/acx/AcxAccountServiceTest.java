package org.knowm.xchange.acx;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.acx.dto.account.AcxAccountInfo;
import org.knowm.xchange.acx.service.account.AcxAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

public class AcxAccountServiceTest {

  private AcxApi api;
  private ObjectMapper objectMapper;
  private AccountService service;
  private String accessKey;

  @Before
  public void setUp() {
    objectMapper = new ObjectMapper();
    AcxMapper mapper =
        new AcxMapper(new ExchangeMetaData(new HashMap<>(), new HashMap<>(), null, null, false));
    api = mock(AcxApi.class);
    accessKey = "access_key";
    service =
        new AcxAccountService(
            new CurrentTimeNonceFactory(), api, mapper, mock(AcxSignatureCreator.class), accessKey);
  }

  @Test
  public void testGetAccountInfo() throws IOException {
    when(api.getAccountInfo(eq(accessKey), anyLong(), any()))
        .thenReturn(read("/account/account_info.json", AcxAccountInfo.class));

    AccountInfo accountInfo = service.getAccountInfo();

    assertEquals("Satoshi Nakamoto", accountInfo.getUsername());
    assertEquals(
        new BigDecimal("2159091.0"), accountInfo.getWallet().getBalance(Currency.BTC).getTotal());
    assertEquals(
        new BigDecimal("2159090.0"),
        accountInfo.getWallet().getBalance(Currency.BTC).getAvailable());
  }

  private <T> T read(String path, Class<T> clz) throws IOException {
    return objectMapper.readValue(this.getClass().getResourceAsStream(path), clz);
  }
}
