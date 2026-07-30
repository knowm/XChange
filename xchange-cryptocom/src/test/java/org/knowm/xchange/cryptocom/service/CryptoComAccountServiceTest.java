package org.knowm.xchange.cryptocom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.cryptocom.CryptoCom;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.exceptions.DepositAddressAmbiguousException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;

public class CryptoComAccountServiceTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void singleAddress_returnedDirectly() throws Exception {
    CryptoComAccountService service = newService(address("eth", "0xabc"));

    String address = service.requestDepositAddress(Currency.USDT);

    assertThat(address).isEqualTo("0xabc");
  }

  @Test
  public void multipleAddressesWithoutNetwork_throwsAmbiguousException() throws Exception {
    CryptoComAccountService service =
        newService(address("eth", "0xabc"), address("sol", "SoLAddr"));

    assertThatThrownBy(() -> service.requestDepositAddress(Currency.USDT))
        .isInstanceOf(DepositAddressAmbiguousException.class);
  }

  @Test
  public void multipleAddressesWithNetwork_returnsMatchingAddress() throws Exception {
    CryptoComAccountService service =
        newService(address("eth", "0xabc"), address("sol", "SoLAddr"));

    String address = service.requestDepositAddress(Currency.USDT, "sol");

    assertThat(address).isEqualTo("SoLAddr");
  }

  @Test
  public void multipleAddressesWithUnknownNetwork_throws() throws Exception {
    CryptoComAccountService service =
        newService(address("eth", "0xabc"), address("sol", "SoLAddr"));

    assertThatThrownBy(() -> service.requestDepositAddress(Currency.USDT, "trx"))
        .isInstanceOf(NotAvailableFromExchangeException.class);
  }

  private ObjectNode address(String networkId, String addr) {
    ObjectNode node = mapper.createObjectNode();
    node.put("network_id", networkId);
    node.put("address", addr);
    return node;
  }

  private CryptoComAccountService newService(ObjectNode... addresses) throws Exception {
    ArrayNode list = mapper.createArrayNode();
    for (ObjectNode a : addresses) {
      list.add(a);
    }
    ObjectNode result = mapper.createObjectNode();
    result.set("deposit_address_list", list);

    CryptoCom cryptoCom = mock(CryptoCom.class);
    when(cryptoCom.getDepositAddress(any()))
        .thenAnswer(
            invocation -> {
              CryptoComResponse response = new CryptoComResponse();
              response.setResult(result);
              return response;
            });

    CryptoComExchange exchange = mock(CryptoComExchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(CryptoComExchange.class);
    spec.setApiKey("key");
    spec.setSecretKey("secret");
    when(exchange.getExchangeSpecification()).thenReturn(spec);
    when(exchange.getCryptoCom()).thenReturn(cryptoCom);
    when(exchange.nextRequestId()).thenReturn(1L);

    return new CryptoComAccountService(exchange, new ResilienceRegistries());
  }
}
