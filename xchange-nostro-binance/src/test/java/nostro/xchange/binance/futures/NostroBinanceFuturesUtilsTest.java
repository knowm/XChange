package nostro.xchange.binance.futures;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import nostro.xchange.binance.utils.NostroBinanceFuturesDtoUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.utils.NostroUtils;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class NostroBinanceFuturesUtilsTest {
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
         mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    public void testToOrderEntity() throws JsonProcessingException {
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.NEW, "BTCUSDT", new Date().getTime(), new Date().getTime(), null);
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(order);

        assertThat(orderEntity.getId()).isEqualTo(order.clientOrderId);
        FuturesContract contract = new FuturesContract(BinanceAdapters.adaptSymbol(order.symbol), null);
        assertThat(orderEntity.getInstrument()).isEqualTo(contract.toString());
        assertThat(orderEntity.getExternalId()).isEqualTo(String.valueOf(order.orderId));
        assertThat(orderEntity.getCreated().getTime()).isEqualTo(order.time);
        assertThat(orderEntity.getUpdated().getTime()).isEqualTo(order.updateTime);
        assertThat(NostroUtils.readOrderDocument(orderEntity.getDocument())).isEqualTo(BinanceFuturesAdapter.adaptOrder(order));
    }

    @Test
    public void testOrderUpdateRequired_entityTerminal() throws JsonProcessingException {
        long time = new Timestamp(new Date().getTime()).getTime();
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.FILLED, "BTCUSDT", time, time, null);
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(order);
        assertThat(NostroBinanceFuturesUtils.updateRequired(orderEntity, order)).isFalse();
    }

    @Test
    public void testOrderUpdateRequired_orderTerminal() throws JsonProcessingException {
        long time = new Timestamp(new Date().getTime()).getTime();
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.NEW, "BTCUSDT", time, time, null);
        BinanceFuturesOrder orderUpd = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.FILLED, "BTCUSDT", time, time, null);
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(order);
        assertThat(NostroBinanceFuturesUtils.updateRequired(orderEntity, orderUpd)).isTrue();
    }

    @Test
    public void testOrderUpdateRequired_orderUpdateTime() throws JsonProcessingException {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.NEW, "BTCUSDT", timestamp.getTime(), timestamp.getTime(), null);
        BinanceFuturesOrder orderOutdated = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.NEW, "BTCUSDT", timestamp.getTime()-1, timestamp.getTime()-1, null);
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(orderOutdated);

        assertThat(NostroBinanceFuturesUtils.updateRequired(orderEntity, order)).isTrue();
    }

    @Test
    public void testOrderUpdateRequired_orderExecutedQty() throws JsonProcessingException {
        long time = new Date().getTime();
        BinanceFuturesOrder order = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.NEW, "BTCUSDT", time, time, new BigDecimal(1));
        BinanceFuturesOrder orderUpd = NostroBinanceFuturesDtoUtils.generateOrder(1, OrderStatus.FILLED, "BTCUSDT", time, time, new BigDecimal(2));
        OrderEntity orderEntity = NostroBinanceFuturesUtils.toEntity(order);

        assertThat(NostroBinanceFuturesUtils.updateRequired(orderEntity, orderUpd)).isTrue();
    }

    @Test
    public void testAdaptInstrument() {
        assertThat(NostroBinanceFuturesUtils.adaptInstrument("BTCUSDT")).isEqualTo(null);
        assertThat(NostroBinanceFuturesUtils.adaptInstrument("BTC/USDT")).isEqualTo(CurrencyPair.BTC_USDT);
        assertThat(NostroBinanceFuturesUtils.adaptInstrument("BTC/USDT/perpetual")).isEqualTo(new FuturesContract (CurrencyPair.BTC_USDT, null));
    }

    @Test
    public void testAdaptTrade() throws JsonProcessingException {
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(1, 2, Long.valueOf("1569514978020"));
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        UserTrade userTrade = NostroBinanceFuturesUtils.adaptTrade(trade, pair);

        assertThat(userTrade.getOrderId()).isEqualTo(String.valueOf(trade.orderId));
        assertThat(userTrade.getFeeAmount()).isEqualTo(trade.commission);
        assertThat(userTrade.getFeeCurrency()).isEqualTo(Currency.getInstance(trade.commissionAsset));
        assertThat(userTrade.getOrderUserReference()).isEqualTo(null);
        assertThat(userTrade.getId()).isEqualTo(String.valueOf(trade.id));
        assertThat(userTrade.getInstrument()).isEqualTo(pair);
        assertThat(userTrade.getMakerOrderId()).isNull();
        assertThat(userTrade.getOriginalAmount()).isEqualTo(trade.qty);
        assertThat(userTrade.getPrice()).isEqualTo(trade.price);
        assertThat(userTrade.getTakerOrderId()).isNull();
        assertThat(userTrade.getTimestamp()).isEqualTo(trade.getTime());
        assertThat(userTrade.getType()).isEqualTo(Order.OrderType.ASK);
    }

    @Test
    public void testToTradeEntity() throws JsonProcessingException {
        BinanceFuturesTrade trade = NostroBinanceFuturesDtoUtils.generateTrade(1, 2, Long.valueOf("1569514978020"));
        CurrencyPair pair = CurrencyPair.BTC_USDT;
        String orderId = "order-X";
        TradeEntity tradeEntity = NostroBinanceFuturesUtils.toEntity(trade, orderId, pair);

        assertThat(tradeEntity.getOrderId()).isEqualTo(orderId);
        assertThat(tradeEntity.getExternalId()).isEqualTo(String.valueOf(trade.id));
        assertThat(tradeEntity.getTimestamp().getTime()).isEqualTo(trade.time);
        assertThat(NostroUtils.readTradeDocument(tradeEntity.getDocument())).isEqualTo(NostroBinanceFuturesUtils.adaptTrade(trade, pair));
    }

}