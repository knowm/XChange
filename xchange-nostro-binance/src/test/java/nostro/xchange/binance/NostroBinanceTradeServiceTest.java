package nostro.xchange.binance;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderByUserReferenceParams;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;

public class NostroBinanceTradeServiceTest extends DataSourceTest {

    private BinanceTradeService inner; 
    private TransactionFactory txFactory;
    private NostroBinanceTradeService service;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Binance", "user0001");
        inner = mock(BinanceTradeService.class);
        service = new NostroBinanceTradeService(inner, txFactory);
    }

    @Test
    public void placeAndCancelLimitOrder() throws IOException {
        LimitOrder order = new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .userReference(NostroUtils.randomUUID())
                .limitPrice(new BigDecimal("40000"))
                .originalAmount(new BigDecimal("2"))
                .build();
        
        given(inner.placeLimitOrder(any())).willReturn("12345");
        
        String externalId = service.placeLimitOrder(order);
        
        assertThat(externalId).isEqualTo("12345");
        
        Optional<OrderEntity> entity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId("12345"));
        assertThat(entity.isPresent()).isTrue();
        assertThat(entity.get().getId()).isEqualTo(order.getUserReference());

        LimitOrder dbOrder = ObjectMapperHelper.readValue(entity.get().getDocument(), LimitOrder.class);
        assertThat(dbOrder.getLimitPrice()).isEqualTo(order.getLimitPrice());
        assertThat(dbOrder.getOriginalAmount()).isEqualTo(order.getOriginalAmount());

        given(inner.cancelOrder(CurrencyPair.BTC_USDT, null, "12345", null)).willReturn(null);

        boolean cancelled = service.cancelOrder(new DefaultCancelOrderByUserReferenceParams(order.getUserReference()));
        assertThat(cancelled).isTrue();

        entity = txFactory.executeAndGet(tx -> tx.getOrderRepository().findByExternalId("12345"));
        assertThat(entity.isPresent()).isTrue();

        dbOrder = ObjectMapperHelper.readValue(entity.get().getDocument(), LimitOrder.class);
        assertThat(dbOrder.getStatus()).isEqualTo(Order.OrderStatus.PENDING_CANCEL);
    }

}