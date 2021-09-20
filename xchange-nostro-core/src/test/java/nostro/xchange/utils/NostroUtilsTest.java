package nostro.xchange.utils;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class NostroUtilsTest {

    @Test
    public void randomUUID() {
        assertThat(NostroUtils.randomUUID().length()).isEqualTo(32);
        NostroUtils.randomUUID().chars()
                .forEach(i -> assertThat(Character.digit((char) i, 16) >= 0).isTrue());
    }
    
    @Test
    public void marketOrderReadWrite() {
        MarketOrder market = new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .originalAmount(BigDecimal.ONE)
                .build();

        String marketJson = NostroUtils.writeOrderDocument(market);
        Order o = NostroUtils.readOrderDocument(marketJson);
        
        assertThat(o).isExactlyInstanceOf(MarketOrder.class);
        assertThat(o.getType()).isEqualTo(Order.OrderType.BID);
        assertThat(o.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
        assertThat(o.getOriginalAmount()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    public void limitOrderReadWrite() {
        LimitOrder limit = new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .originalAmount(BigDecimal.ONE)
                .limitPrice(BigDecimal.TEN)
                .build();

        String limitJson = NostroUtils.writeOrderDocument(limit);
        Order o = NostroUtils.readOrderDocument(limitJson);

        assertThat(o).isExactlyInstanceOf(LimitOrder.class);
        assertThat(o.getType()).isEqualTo(Order.OrderType.BID);
        assertThat(o.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
        assertThat(o.getOriginalAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(((LimitOrder) o).getLimitPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void stopOrderReadWrite() {
        StopOrder limit = new StopOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                .originalAmount(BigDecimal.ONE)
                .limitPrice(BigDecimal.TEN)
                .stopPrice(new BigDecimal("15"))
                .intention(StopOrder.Intention.TAKE_PROFIT)
                .build();

        String limitJson = NostroUtils.writeOrderDocument(limit);
        Order o = NostroUtils.readOrderDocument(limitJson);

        assertThat(o).isExactlyInstanceOf(StopOrder.class);
        assertThat(o.getType()).isEqualTo(Order.OrderType.BID);
        assertThat(o.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
        assertThat(o.getOriginalAmount()).isEqualTo(BigDecimal.ONE);
        assertThat(((StopOrder) o).getLimitPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(((StopOrder) o).getStopPrice()).isEqualTo(new BigDecimal("15"));
        assertThat(((StopOrder) o).getIntention()).isEqualTo(StopOrder.Intention.TAKE_PROFIT);
    }

}