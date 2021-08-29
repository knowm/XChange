package nostro.xchange.binance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class BinanceNostroTradeServiceTest extends DataSourceTest {

    private BinanceTradeService inner; 
    private TransactionFactory txFactory;
    private BinanceNostroTradeService service;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Binance", "user0001");
        inner = mock(BinanceTradeService.class);
        service = new BinanceNostroTradeService(inner, txFactory);
    }

    @Test
    public void placeLimitOrder() throws IOException {
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
    }

}