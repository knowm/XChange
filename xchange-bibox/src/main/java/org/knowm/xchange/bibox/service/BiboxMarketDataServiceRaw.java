package org.knowm.xchange.bibox.service;

import static org.knowm.xchange.bibox.dto.BiboxAdapters.toBiboxPair;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.BiboxException;
import org.knowm.xchange.bibox.dto.BiboxAdapters;
import org.knowm.xchange.bibox.dto.BiboxCommand;
import org.knowm.xchange.bibox.dto.BiboxCommands;
import org.knowm.xchange.bibox.dto.BiboxMultipleResponses;
import org.knowm.xchange.bibox.dto.BiboxResponse;
import org.knowm.xchange.bibox.dto.marketdata.BiboxMarket;
import org.knowm.xchange.bibox.dto.marketdata.BiboxOrderBookCommand;
import org.knowm.xchange.bibox.dto.marketdata.BiboxTicker;
import org.knowm.xchange.bibox.dto.trade.BiboxOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author odrotleff */
public class BiboxMarketDataServiceRaw extends BiboxBaseService {

  private static final String TICKER_CMD = "ticker";
  private static final String DEPTH_CMD = "depth";
  private static final String ALL_TICKERS_CMD = "marketAll";

  protected BiboxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BiboxTicker getBiboxTicker(CurrencyPair currencyPair) throws IOException {
    try {
      BiboxResponse<BiboxTicker> response = bibox.mdata(TICKER_CMD, toBiboxPair(currencyPair));
      throwErrors(response);
      return response.getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BiboxOrderBook getBiboxOrderBook(CurrencyPair currencyPair, Integer depth)
      throws IOException {
    try {
      BiboxResponse<BiboxOrderBook> response =
          bibox.orderBook(DEPTH_CMD, BiboxAdapters.toBiboxPair(currencyPair), depth);
      throwErrors(response);
      return response.getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<BiboxMarket> getAllBiboxMarkets() throws IOException {
    try {
      BiboxResponse<List<BiboxMarket>> response = bibox.marketAll(ALL_TICKERS_CMD);
      throwErrors(response);
      return response.getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<BiboxOrderBook> getBiboxOrderBooks(
      Integer depth, Collection<CurrencyPair> currencyPairs) {
    try {
      List<BiboxCommand<?>> allCommands =
          currencyPairs
              .stream()
              .distinct()
              .filter(Objects::nonNull)
              .map(BiboxAdapters::toBiboxPair)
              .map(pair -> new BiboxOrderBookCommand(pair, depth))
              .collect(Collectors.toList());
      BiboxMultipleResponses<BiboxOrderBook> response =
          bibox.orderBooks(BiboxCommands.of(allCommands).json());
      throwErrors(response);
      return response
          .getResult()
          .stream()
          .map(BiboxResponse::getResult)
          .collect(Collectors.toList());
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
