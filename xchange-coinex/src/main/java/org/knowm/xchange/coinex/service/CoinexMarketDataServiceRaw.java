package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.marketdata.CoinexAllMarketStatisticsV1;
import org.knowm.xchange.coinex.dto.marketdata.CoinexChainInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexCurrencyPairInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexMaintainInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexMarketDepth;
import org.knowm.xchange.coinex.dto.marketdata.CoinexSingleMarketStatisticsV1;
import org.knowm.xchange.coinex.service.params.CoinexOrderBookParams;
import org.knowm.xchange.instrument.Instrument;

public class CoinexMarketDataServiceRaw extends CoinexBaseService {

  public CoinexMarketDataServiceRaw(CoinexExchange exchange) {
    super(exchange);
  }


  public List<CoinexMaintainInfo> getCoinexMaintainInfo() throws IOException {
    return coinex.maintainInfo().getData();
  }


  public List<CoinexChainInfo> getAllCoinexChainInfos() throws IOException {
    return new ArrayList<>(coinex.allChainInfos().getData().values());
  }

  public CoinexAllMarketStatisticsV1 getCoinexAllMarketStatisticsV1() throws IOException {
    return coinex.allMarketStatistics().getData();
  }

  public CoinexSingleMarketStatisticsV1 getCoinexSingleMarketStatisticsV1(Instrument instrument)
      throws IOException {
    String market = CoinexAdapters.toString(instrument);
    return coinex.singleMarketStatistics(market).getData();
  }

  public List<CoinexCurrencyPairInfo> getCoinexCurrencyPairInfos(Collection<Instrument> instruments)
      throws IOException {
    String marketsParam = CoinexAdapters.instrumentsToString(instruments);
    return coinex.marketStatus(marketsParam).getData();
  }

  public CoinexMarketDepth getCoinexOrderBook(CoinexOrderBookParams coinexOrderBookParams)
      throws IOException {
    String instrument = CoinexAdapters.toString(coinexOrderBookParams.getInstrument());
    Integer limit =
        Optional.ofNullable(coinexOrderBookParams.getLimit())
            .orElse(CoinexOrderBookParams.DEFAULT_LIMIT);
    Integer interval =
        Optional.ofNullable(coinexOrderBookParams.getInterval())
            .orElse(CoinexOrderBookParams.DEFAULT_INTERVAL);

    return coinex.marketDepth(instrument, limit, interval).getData();
  }
}
