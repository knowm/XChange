package org.knowm.xchange.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.KrakenAdaptersTest;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class KrakenUtilsTest {

    @Test
    public void testFilterOpenOrdersByCurrencyPair() throws IOException {

        // Read in the JSON from the example resources
        InputStream is =
                KrakenAdaptersTest.class.getResourceAsStream(
                        "/org/knowm/xchange/kraken/dto/trading/example-openorders-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);

        Map<String, KrakenOrder> krakenOrders = KrakenUtils.filterOpenOrdersByCurrencyPair(krakenResult.getResult().getOrders(),
                CurrencyPair.BTC_EUR);

        OpenOrders orders = KrakenAdapters.adaptOpenOrders(krakenOrders);

        // Verify that the example data was unmarshalled correctly
        assertThat(orders.getOpenOrders()).hasSize(1);
        assertThat(orders.getOpenOrders().get(0).getId()).isEqualTo("OU5JPQ-OIDTK-QIGIGI");
        assertThat(orders.getOpenOrders().get(0).getLimitPrice()).isEqualTo("1000.000");
        assertThat(orders.getOpenOrders().get(0).getOriginalAmount()).isEqualTo("0.01000000");
        assertThat(orders.getOpenOrders().get(0).getCurrencyPair().base).isEqualTo(Currency.XBT);
        assertThat(orders.getOpenOrders().get(0).getCurrencyPair().counter).isEqualTo(Currency.EUR);
        assertThat(orders.getOpenOrders().get(0).getType()).isEqualTo(Order.OrderType.BID);
    }
}