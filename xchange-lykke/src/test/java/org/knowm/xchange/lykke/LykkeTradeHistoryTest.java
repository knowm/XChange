package org.knowm.xchange.lykke;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.lykke.dto.trade.LykkeFeeType;
import org.knowm.xchange.lykke.dto.trade.LykkeTradeHistory;
import org.knowm.xchange.lykke.dto.trade.LykkeTradeState;
import org.knowm.xchange.lykke.service.LykkeTradeServiceRaw;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LykkeTradeHistoryTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUnmarshal() throws IOException{
        InputStream is =
                LykkeTradeHistoryTest.class.getResourceAsStream(
                        "/org/knowm/xchange/lykke/example-lykkeTradeHistory.json");
        LykkeTradeHistory[] lykkeTradeHistory = mapper.readValue(is, LykkeTradeHistory[].class);

        assertThat(lykkeTradeHistory[0].getId()).isEqualTo("string");
        assertThat(lykkeTradeHistory[0].getDatetime()).isEqualTo("2018-07-19T19:21:12.411Z");
        assertThat(lykkeTradeHistory[0].getState()).isEqualTo(LykkeTradeState.InProgress);
        assertThat(lykkeTradeHistory[0].getAmount()).isEqualTo(0);
        assertThat(lykkeTradeHistory[0].getAsset()).isEqualTo("string");
        assertThat(lykkeTradeHistory[0].getAssetPair()).isEqualTo("string");
        assertThat(lykkeTradeHistory[0].getPrice()).isEqualTo(0);
        assertThat(lykkeTradeHistory[0].getFee().getAmount()).isEqualTo(0);
        assertThat(lykkeTradeHistory[0].getFee().getType()).isEqualTo(LykkeFeeType.Unknown);
    }

    @Test
    public void lastOrdersTradeHistory() throws IOException {
        Exchange exchange = LykkeKeys.getExchange();
        TradeHistoryParamsAll paramsAll = new TradeHistoryParamsAll();
        paramsAll.setPageLength(500);
        paramsAll.setCurrencyPair(CurrencyPair.ETH_EUR);
        List<UserTrade> userTrades = exchange.getTradeService().getTradeHistory(paramsAll).getUserTrades();
        Collections.reverse(userTrades);
        userTrades.forEach(userTrade -> {
            System.out.println(userTrade);
        });

    }

    @Test
    public void tradehistory() throws IOException{
        LykkeTradeServiceRaw raw = new LykkeTradeServiceRaw(LykkeKeys.getExchange());
        TradeHistoryParamsAll paramsAll = new TradeHistoryParamsAll();
        paramsAll.setPageLength(500);
        paramsAll.setCurrencyPair(CurrencyPair.BTC_EUR);
        raw.getLykkeTradeHistory(paramsAll).forEach(tradeHistory -> {
            System.out.println(tradeHistory);
        });
    }

    @Test
    public void lastOrdersRaw() throws IOException{
        LykkeTradeServiceRaw raw = new LykkeTradeServiceRaw(LykkeKeys.getExchange());
        TradeHistoryParamsAll paramsAll = new TradeHistoryParamsAll();
        paramsAll.setPageLength(100);
        paramsAll.setCurrencyPair(CurrencyPair.BTC_EUR);
        raw.getMathedOrders(100).forEach(tradeHistory -> {
            System.out.println(tradeHistory);
        });
    }
}
