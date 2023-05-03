package org.knowm.xchange.krakenfutures;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class KrakenFuturesPublicDataTest {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(KrakenFuturesExchange.class);
    Instrument instrument = new FuturesContract("BTC/USD/PERP");

    @Test
    public void checkInstrumentsMetaData() {
        Map<Instrument, InstrumentMetaData> instrumentInstrumentMetaDataMap = exchange.getExchangeMetaData().getInstruments();
        System.out.println(instrumentInstrumentMetaDataMap.toString());
        assertThat(instrumentInstrumentMetaDataMap.get(instrument).getMinimumAmount()).isNotNull();
        assertThat(instrumentInstrumentMetaDataMap.get(instrument).getVolumeScale()).isNotNull();
        assertThat(instrumentInstrumentMetaDataMap.get(instrument).getPriceScale()).isNotNull();
    }

    @Test
    public void checkOrderBook() throws IOException {
        OrderBook orderbook = exchange.getMarketDataService().getOrderBook(instrument);
        System.out.println(orderbook.toString());
        assertThat(orderbook.getBids().get(0).getInstrument()).isEqualTo(instrument);
        assertThat(orderbook.getBids().get(0).getLimitPrice()).isLessThan(orderbook.getAsks().get(0).getLimitPrice());
    }

    @Test
    public void checkTicker() throws IOException {
        Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
        System.out.println(ticker);
        assertThat(ticker.getInstrument()).isEqualTo(instrument);
    }

    @Test
    public void checkTrades() throws IOException {
        Trades trades = exchange.getMarketDataService().getTrades(instrument);
        System.out.println(trades);
        assertThat(trades.getTrades().get(0).getInstrument()).isEqualTo(instrument);
        assertThat(trades.getTrades().get(0).getTimestamp()).isBefore(trades.getTrades().get(50).getTimestamp());
    }

    @Test
    public void checkFundingRates() throws IOException {
        FundingRates fundingRates = exchange.getMarketDataService().getFundingRates();
        System.out.println(fundingRates);
        assertThat(fundingRates.getFundingRates().size()).isEqualTo(exchange.getExchangeMetaData().getInstruments().size());
        fundingRates.getFundingRates().forEach(fundingRate -> {
            assertThat(fundingRate.getFundingRateEffectiveInMinutes()).isLessThan(61);
            assertThat(fundingRate.getFundingRate1h()).isNotNull();
            assertThat(fundingRate.getFundingRate8h()).isNotNull();
            assertThat(fundingRate.getFundingRateDate()).isNotNull();
        });
    }

    @Test
    public void checkFundingRate() throws IOException {
        FundingRate fundingRate = exchange.getMarketDataService().getFundingRate(instrument);
        assertThat(fundingRate.getInstrument()).isEqualTo(instrument);
        assertThat(fundingRate.getFundingRateEffectiveInMinutes()).isLessThan(61);
        assertThat(fundingRate.getFundingRate1h()).isNotNull();
        assertThat(fundingRate.getFundingRate8h()).isNotNull();
        assertThat(fundingRate.getFundingRateDate()).isNotNull();
        System.out.println(fundingRate);
    }
}
