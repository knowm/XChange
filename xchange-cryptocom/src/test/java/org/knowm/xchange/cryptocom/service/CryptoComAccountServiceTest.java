package org.knowm.xchange.cryptocom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.cryptocom.CryptoCom;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.cryptocom.dto.CryptoComRequest;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.exceptions.DepositAddressAmbiguousException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.NetworkWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

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

  @Test
  public void withdrawFunds_acceptsPlainDefaultParams_omitsNetwork() throws Exception {
    CryptoComRequest[] captured = new CryptoComRequest[1];
    CryptoComAccountService service = newWithdrawService(captured);

    DefaultWithdrawFundsParams params =
        new DefaultWithdrawFundsParams("0xabc", Currency.USDT, new BigDecimal("10"));

    String id = service.withdrawFunds(params);

    assertThat(id).isEqualTo("wid-1");
    assertThat(captured[0].getParams()).doesNotContainKey("network_id");
  }

  @Test
  public void withdrawFunds_withNetworkParams_includesNetwork() throws Exception {
    CryptoComRequest[] captured = new CryptoComRequest[1];
    CryptoComAccountService service = newWithdrawService(captured);

    NetworkWithdrawFundsParams params =
        NetworkWithdrawFundsParams.builder()
            .address("0xabc")
            .currency(Currency.USDT)
            .amount(new BigDecimal("10"))
            .network("eth")
            .build();

    service.withdrawFunds(params);

    assertThat(captured[0].getParams()).containsEntry("network_id", "eth");
  }

  @Test
  public void withdrawFunds_rejectsUnsupportedParamsType() throws Exception {
    CryptoComAccountService service = newWithdrawService(new CryptoComRequest[1]);
    WithdrawFundsParams unsupported = mock(WithdrawFundsParams.class);

    assertThatThrownBy(() -> service.withdrawFunds(unsupported))
        .isInstanceOf(NotAvailableFromExchangeException.class);
  }

  private CryptoComAccountService newWithdrawService(CryptoComRequest[] captured)
      throws Exception {
    CryptoCom cryptoCom = mock(CryptoCom.class);
    when(cryptoCom.createWithdrawal(any()))
        .thenAnswer(
            invocation -> {
              captured[0] = invocation.getArgument(0);
              ObjectNode result = mapper.createObjectNode();
              result.put("id", "wid-1");
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
