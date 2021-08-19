package info.bitrich.xchangestream.coinbasepro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.coinbasepro.cache.OpenOrdersCache;
import info.bitrich.xchangestream.coinbasepro.cache.ProductOpenOrders;
import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBook;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.util.*;

public class CoinbaseProCacheTest {
    @Test
    public void test1() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[" +
                "{\"id\":\"order1\",\"price\":\"0.06746\",\"size\":\"1.0\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[" +
                "{\"id\":\"order1\",\"price\":\"0.06746\",\"size\":\"1.0\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";

        test(orderBook, openOrders, messages, result);
    }

    @Test
    public void test2() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[" +
                "{\"id\":\"order2\",\"price\":\"0.06746\",\"size\":\"1.0\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[]";

        test(orderBook, openOrders, messages, result);
    }

    @Test
    public void test3() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +
                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order3\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order3\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order3\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:45.995385Z\",\"sequence\":5,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490325,\"maker_order_id\":\"order3\",\"taker_order_id\":\"57cd0369-1bd8-4d74-b107-59855ed8f2c6\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"done\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:45.995385Z\",\"sequence\":6,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order3\",\"reason\":\"filled\",\"price\":\"0.06746\",\"remaining_size\":\"0\"}," +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[]";

        test(orderBook, openOrders, messages, result);
    }

    @Test
    public void test4() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06746\",\"0.01\",\"order4\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +
                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order4\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order4\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order4\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[" +
                "{\"id\":\"order4\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";

        test(orderBook, openOrders, messages, result);
    }

    @Test
    public void test5() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06746\",\"0.01\",\"order4\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[" +
                "{\"id\":\"order5\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +
                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order5\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order5\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order5\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[]";

        test(orderBook, openOrders, messages, result);
    }

    @Test
    public void test6() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06746\",\"0.01\",\"order4\"],\n" +
                "[\"0.06746\",\"0.01\",\"order6\"],\n" +
                "[\"0.06746\",\"0.01\",\"order7\"],\n" +
                "[\"0.06746\",\"0.01\",\"order8\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[" +
                "{\"id\":\"order6\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order7\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000000000\",\"filled_size\":\"0.0\",\"executed_value\":\"0.000\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order8\",\"price\":\"0.06746\",\"size\":\"0.03\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +
                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order6\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order6\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order6\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":5,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order7\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":6,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order7\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":7,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order7\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":8,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order8\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":9,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order8\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":10,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order8\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":11,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order8\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[" +
                "{\"id\":\"order6\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order7\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order8\",\"price\":\"0.06746\",\"size\":\"0.03\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000013492\",\"filled_size\":\"0.02\",\"executed_value\":\"0.0013492\",\"status\":\"open\",\"settled\":false}" +
                "]";

        test(orderBook, openOrders, messages, result);
    }

    @Test
    public void testAll() throws IOException {
        final String orderBook = "{\n" +
                "\"bids\":[\n" +
                "[\"0.06746\",\"0.99\",\"order1\"],\n" +
                "[\"0.06746\",\"0.01\",\"order4\"],\n" +
                "[\"0.06746\",\"0.01\",\"order6\"],\n" +
                "[\"0.06746\",\"0.01\",\"order7\"],\n" +
                "[\"0.06746\",\"0.01\",\"order8\"],\n" +
                "[\"0.06761\",\"301029\",\"a005ae86-0d50-4932-ad76-5d05ef0be930\"],\n" +
                "[\"0.0676\",\"477121\",\"64504753-e53b-4e92-ac4a-ef891cec229c\"]\n" +
                "],\n" +
                "\"asks\":[\n" +
                "[\"0.10499\",\"22.78196172\",\"97be0ea9-9e8c-487a-9ee2-244635111fd4\"],\n" +
                "[\"0.1074\",\"0.02\",\"a30a76c4-45f8-416d-825c-0b500a6742ca\"],\n" +
                "[\"0.10741\",\"0.02\",\"ab012b59-03fa-492e-b52d-9dbdb649a866\"]\n" +
                "],\n" +
                "\"sequence\":90\n" +
                "}";
        final String openOrders = "[" +
                "{\"id\":\"order1\",\"price\":\"0.06746\",\"size\":\"1.0\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order2\",\"price\":\"0.06746\",\"size\":\"1.0\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order5\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order6\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order7\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000000000\",\"filled_size\":\"0.0\",\"executed_value\":\"0.000\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order8\",\"price\":\"0.06746\",\"size\":\"0.03\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}" +
                "]";
        final String messages = "[" +
                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":1,\"time\":\"\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order3\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order3\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order3\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:45.995385Z\",\"sequence\":5,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490325,\"maker_order_id\":\"order3\",\"taker_order_id\":\"57cd0369-1bd8-4d74-b107-59855ed8f2c6\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"done\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:45.995385Z\",\"sequence\":6,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order3\",\"reason\":\"filled\",\"price\":\"0.06746\",\"remaining_size\":\"0\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order5\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order5\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order5\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order4\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order4\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order4\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":2,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order6\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":3,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order6\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":4,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order6\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":5,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order7\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":6,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order7\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":7,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order7\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"received\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":8,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"order_id\":\"order8\",\"order_type\":\"limit\",\"size\":\"0.02\",\"price\":\"0.06746\",\"client_oid\":\"408f83e5-0d06-46f5-cd18-494efece696f\"}," +
                "{\"type\":\"open\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:35.450567Z\",\"sequence\":9,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"price\":\"0.06746\",\"order_id\":\"order8\",\"remaining_size\":\"0.02\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":10,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order8\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +
                "{\"type\":\"match\",\"side\":\"buy\",\"product_id\":\"ETH-BTC\",\"time\":\"2021-08-18T21:53:36.606783Z\",\"sequence\":11,\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"user_id\":\"60a3c2fcc7348600ab8cd510\",\"trade_id\":4490323,\"maker_order_id\":\"order8\",\"taker_order_id\":\"76628b5b-4749-4880-b08c-ff1abe9137ef\",\"size\":\"0.01\",\"price\":\"0.06746\",\"maker_profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"maker_user_id\":\"60a3c2fcc7348600ab8cd510\",\"maker_fee_rate\":\"0.001\"}," +

                "{\"type\":\"heartbeat\",\"last_trade_id\":4498714,\"product_id\":\"ETH-BTC\",\"sequence\":100,\"time\":\"\"}" +
                "]";

        final String result = "[" +
                "{\"id\":\"order1\",\"price\":\"0.06746\",\"size\":\"1.0\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order4\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order6\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order7\",\"price\":\"0.06746\",\"size\":\"0.02\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000006746\",\"filled_size\":\"0.01\",\"executed_value\":\"0.0006746\",\"status\":\"open\",\"settled\":false}," +
                "{\"id\":\"order8\",\"price\":\"0.06746\",\"size\":\"0.03\",\"product_id\":\"ETH-BTC\",\"profile_id\":\"2779b2b1-e3d1-4f7e-b0df-28613818293c\",\"side\":\"buy\",\"type\":\"limit\",\"time_in_force\":\"GTC\",\"post_only\":false,\"created_at\":\"\",\"fill_fees\":\"0.0000013492\",\"filled_size\":\"0.02\",\"executed_value\":\"0.0013492\",\"status\":\"open\",\"settled\":false}" +
                "]";

        test(orderBook, openOrders, messages, result);
    }

    private void test(String orderBookString, String openOrdersString, String messagesString, String resultString) throws IOException {
        ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        final CoinbaseProProductBook orderBook = mapper.readValue(mapper.readTree(orderBookString).toString(), CoinbaseProProductBook.class);
        final CoinbaseProOrder[] openOrders = mapper.readValue(mapper.readTree(openOrdersString).toString(), CoinbaseProOrder[].class);
        final CoinbaseProWebSocketTransaction[] messages = mapper.readValue(mapper.readTree(messagesString).toString(), CoinbaseProWebSocketTransaction[].class);
        final CoinbaseProOrder[] result = mapper.readValue(mapper.readTree(resultString).toString(), CoinbaseProOrder[].class);

        ProductOpenOrders cache = new ProductOpenOrders("ETH-BTC", new CoinbaseProMarketDataServiceMock(orderBook), new CoinbaseProTradeServiceMock(openOrders));

        for (CoinbaseProWebSocketTransaction message : messages) {
            cache.processWebSocketTransaction(message);
        }
        Assert.assertTrue(equals(Arrays.asList(result), cache.getCoinbaseProOpenOrders()));
    }

    private boolean equals(List<CoinbaseProOrder> a1, List<CoinbaseProOrder> a2) {
        Comparator<CoinbaseProOrder> comparator = new Comparator<CoinbaseProOrder>() {
            @Override
            public int compare(CoinbaseProOrder o1, CoinbaseProOrder o2) {
                return o1.getId().compareTo(o2.getId());
            }
        };
        Collections.sort(a1, comparator);
        Collections.sort(a2, comparator);

        return a1.equals(a2);
    }

    private static class CoinbaseProMarketDataServiceMock extends CoinbaseProMarketDataService {
        private final CoinbaseProProductBook orderBook;

        public CoinbaseProMarketDataServiceMock(CoinbaseProProductBook orderBook) {
            super((CoinbaseProStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(StreamingExchangeFactory.INSTANCE
                    .createExchange(CoinbaseProStreamingExchange.class)
                    .getDefaultExchangeSpecification()), null);
            this.orderBook = orderBook;
        }

        @Override
        public CoinbaseProProductBook getCoinbaseProProductOrderBook(CurrencyPair currencyPair, int level) throws IOException {
            return orderBook;
        }
    }

    private static class CoinbaseProTradeServiceMock extends CoinbaseProTradeService {
        private final CoinbaseProOrder[] openOrders;

        public CoinbaseProTradeServiceMock(CoinbaseProOrder[] openOrders) {
            super((CoinbaseProStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(StreamingExchangeFactory.INSTANCE
                    .createExchange(CoinbaseProStreamingExchange.class)
                    .getDefaultExchangeSpecification()), null);
            this.openOrders = openOrders;
        }

        @Override
        public CoinbaseProOrder[] getCoinbaseProProductOpenOrders(String productId) throws IOException {
            return openOrders;
        }
    }
}
