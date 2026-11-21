package info.bitrich.xchangestream.bybit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.instrument.Instrument;

class BybitStreamingTradeServiceTest {

  private BybitUserDataStreamingService userDataStreamingService;
  private BybitStreamingTradeService tradeService;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  @BeforeEach
  void setUp() {
    ExchangeSpecification specification = new BybitExchange().getDefaultExchangeSpecification();
    specification.setApiKey("api-key");
    specification.setSecretKey("api-secret");
    BybitExchange exchange = mock(BybitExchange.class);
    when(exchange.getExchangeSpecification()).thenReturn(specification);

    userDataStreamingService = mock(BybitUserDataStreamingService.class);
    tradeService =
        new BybitStreamingTradeService(
            userDataStreamingService,
            mock(BybitUserTradeStreamingService.class),
            new ResilienceRegistries(),
            exchange);
  }

  private void givenOrderChanges() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("orderChangeSpotResponse.json5"));
    when(userDataStreamingService.subscribeChannel("order")).thenReturn(Observable.just(jsonNode));
    when(userDataStreamingService.subscribeChannel("order.spot"))
        .thenReturn(Observable.just(jsonNode));
  }

  @Test
  void getOrderChangesWithoutCategoryArgument_shouldSubscribeToTheAllCategoriesTopic()
      throws Exception {
    givenOrderChanges();

    List<Order> orders = tradeService.getOrderChanges(CurrencyPair.XRP_USDT).toList().blockingGet();

    verify(userDataStreamingService).subscribeChannel("order");
    assertThat(orders).hasSize(1);
    assertThat(orders.get(0).getInstrument()).isEqualTo(CurrencyPair.XRP_USDT);
  }

  @Test
  void getOrderChangesWithCategoryArgument_shouldSubscribeToTheCategorisedTopic() throws Exception {
    givenOrderChanges();

    List<Order> orders =
        tradeService
            .getOrderChanges(CurrencyPair.XRP_USDT, BybitCategory.SPOT)
            .toList()
            .blockingGet();

    verify(userDataStreamingService).subscribeChannel("order.spot");
    assertThat(orders).hasSize(1);
  }

  @Test
  void getOrderChangesWithoutInstrument_shouldEmitEveryInstrument() throws Exception {
    givenOrderChanges();

    List<Order> orders = tradeService.getOrderChanges((Instrument) null).toList().blockingGet();

    assertThat(orders).hasSize(2);
    assertThat(orders).extracting(Order::getInstrument).contains(CurrencyPair.XRP_USDT);
  }

  @Test
  void getOrderChangesWithUnknownInstrument_shouldEmitNothing() throws Exception {
    givenOrderChanges();

    List<Order> orders = tradeService.getOrderChanges(CurrencyPair.ETH_USDT).toList().blockingGet();

    assertThat(orders).isEmpty();
  }
}
