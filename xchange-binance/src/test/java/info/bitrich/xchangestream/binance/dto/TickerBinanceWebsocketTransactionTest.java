package info.bitrich.xchangestream.binance.dto;

import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.TICKER_24_HR;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class TickerBinanceWebsocketTransactionTest {

    private static ObjectMapper mapper;

    @BeforeClass
    public static void setupClass() {
        mapper = new ObjectMapper();
    }

    @Test
    public void test_deserialization_of_transaction_message() throws IOException {
        InputStream stream = TickerBinanceWebsocketTransactionTest.class.getResourceAsStream("testTickerEvent.json");
        BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction> transaction = mapper.readValue(stream,
            new TypeReference<BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction>>() {});

        TickerBinanceWebsocketTransaction tickerTransaction = transaction.getData();

        assertThat(tickerTransaction).isNotNull();
        assertThat(tickerTransaction.eventType).isEqualTo(TICKER_24_HR);
        assertThat(tickerTransaction.getEventTime().getTime()).isEqualTo(1516135684559L);
        assertThat(tickerTransaction.getCurrencyPair()).isEqualTo(CurrencyPair.ETH_BTC);

        BinanceTicker24h ticker = tickerTransaction.getTicker();
        assertThat(ticker.getPriceChange()).isEqualByComparingTo(BigDecimal.valueOf(-0.00271700));
        assertThat(ticker.getPriceChangePercent()).isEqualByComparingTo(BigDecimal.valueOf(-2.875));
        assertThat(ticker.getWeightedAvgPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09158373));

        assertThat(ticker.getPrevClosePrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09448900));
        assertThat(ticker.getLastPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09177200));
        assertThat(ticker.getLastQty()).isEqualByComparingTo(BigDecimal.valueOf(0.06100000));
        assertThat(ticker.getBidPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09155300));
        assertThat(ticker.getBidQty()).isEqualByComparingTo(BigDecimal.valueOf(1.75300000));
        assertThat(ticker.getAskPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09177200));
        assertThat(ticker.getAskQty()).isEqualByComparingTo(BigDecimal.valueOf(1.83900000));
        assertThat(ticker.getOpenPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09448900));
        assertThat(ticker.getHighPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.09575800));
        assertThat(ticker.getLowPrice()).isEqualByComparingTo(BigDecimal.valueOf(0.08100000));
        assertThat(ticker.getVolume()).isEqualByComparingTo(BigDecimal.valueOf(287542.79200000));
        assertThat(ticker.getQuoteVolume()).isEqualByComparingTo(BigDecimal.valueOf(26334.24227973));
        assertThat(ticker.getOpenTime().getTime()).isEqualTo(1516049284557L);
        assertThat(ticker.getCloseTime().getTime()).isEqualTo(1516135684557L);
        assertThat(ticker.getFirstTradeId()).isEqualTo(21702040L);
        assertThat(ticker.getLastTradeId()).isEqualTo(22120714L);
        assertThat(ticker.getTradeCount()).isEqualTo(418675L);
    }

}