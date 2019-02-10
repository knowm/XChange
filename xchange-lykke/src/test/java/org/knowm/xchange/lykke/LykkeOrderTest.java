package org.knowm.xchange.lykke;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeOrder;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LykkeOrderTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void orderbookUnmarshalTest() throws IOException {
        InputStream is =
                LykkeAssetsTest.class.getResourceAsStream(
                        "/org/knowm/xchange/lykke/example-lykkeOrder.json");
        LykkeOrder[] lykkeOrderRespons = mapper.readValue(is, LykkeOrder[].class);

        assertThat(lykkeOrderRespons[0].getId()).isEqualTo("string");
        assertThat(lykkeOrderRespons[0].getStatus()).isEqualTo("string");
        assertThat(lykkeOrderRespons[0].getAssetPairId()).isEqualTo("string");
        assertThat(lykkeOrderRespons[0].getVolume()).isEqualTo(0);
        assertThat(lykkeOrderRespons[0].getPrice()).isEqualTo(0);
        assertThat(lykkeOrderRespons[0].getRemainingVolume()).isEqualTo(0);
        assertThat(lykkeOrderRespons[0].getLastMatchTime()).isEqualTo("2018-07-19T21:19:48.544Z");
        assertThat(lykkeOrderRespons[0].getCreatedAt()).isEqualTo("2018-07-19T21:19:48.544Z");

    }

    @Test
    public void testPlaceOrder() throws IOException {
        Exchange exchange = LykkeKeys.getExchange();
        LimitOrder lykkeLimitOrder = new LimitOrder(Order.OrderType.BID,new BigDecimal(3),new CurrencyPair(Currency.EUR,Currency.CHF),null,null,new BigDecimal(1.2649));
        System.out.println(exchange.getTradeService().placeLimitOrder(lykkeLimitOrder));
    }

    @Test
    public void getLastOrdersTest() throws IOException{
        Exchange exchange  = LykkeKeys.getExchange();
        System.out.println(exchange.getTradeService().getOpenOrders());

    }

    @Test
    public void testss() throws IOException{
        Exchange exchange = LykkeKeys.getExchange();
        System.out.println(exchange.getExchangeMetaData().getCurrencies());
    }

    @Test
    public void testCancelOrder() throws IOException{
        Exchange exchange = LykkeKeys.getExchange();
        System.out.println(exchange.getTradeService().cancelOrder("81f66d38-d297-4015-a2b6-6573e8a4a185"));
    }
    @Test
    public void testCancelAllOrders() throws IOException{
        Exchange exchange = LykkeKeys.getExchange();
        CancelOrderByCurrencyPair test = new CancelOrderByCurrencyPair() {
            @Override
            public CurrencyPair getCurrencyPair() {
                return CurrencyPair.BTC_EUR;
            }
        };
        System.out.println(exchange.getTradeService().cancelOrder(test));
    }
    //65709232-5f9f-491a-9243-77dfef0775b3
}
