package org.knowm.xchange.globitex;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.globitex.service.GlobitexAccountServiceRaw;
import org.knowm.xchange.globitex.service.GlobitexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FunctionTestingClass {

    Logger logger = LoggerFactory.getLogger(FunctionTestingClass.class);

    GlobitexMarketDataServiceRaw raw = (GlobitexMarketDataServiceRaw) getExchange().getMarketDataService();
    GlobitexAccountServiceRaw accountServiceRaw = (GlobitexAccountServiceRaw) getExchange().getAccountService();

    public Exchange getExchange(){
        final String globitexApiKey = "920e2db0b8341b3180377c89dc6d9b22";
        final String globitexSecret = "4a893dfccb6ad80bf19a554678e2fa0539ef6a6ce8edd0afd586cab748904b87";

        Exchange globitexExchange = ExchangeFactory.INSTANCE.createExchange(GlobitexExchange.class.getName());

        globitexExchange.getExchangeSpecification().setApiKey(globitexApiKey);
        globitexExchange.getExchangeSpecification().setSecretKey(globitexSecret);
        globitexExchange.applySpecification(globitexExchange.getExchangeSpecification());

        return globitexExchange;
    }

   @Test
   public void getSymbolsTest() throws IOException {
       try{
            raw.getGlobitexSymbols().getSymbols().forEach(globitexSymbol -> {
                System.out.println(globitexSymbol);
            });
        }catch (ExchangeException e){
           e.printStackTrace();
        }
   }

   @Test
    public void getTickerTest() throws IOException{
       try{
            System.out.println(raw.getGlobitexTickerBySymbol(CurrencyPair.BTC_EUR).getTimestamp());
       }catch (ExchangeException e){
           e.printStackTrace();
       }
   }

    @Test
    public void getOrderBookRawTest() throws IOException{
        try{
            System.out.println(raw.getGlobitexOrderBookBySymbol(CurrencyPair.BTC_EUR));
        }catch (ExchangeException e){
            e.printStackTrace();
        }
    }

    @Test
    public void exchangeMetaDataTest(){
        System.out.println(getExchange().getExchangeMetaData().toString());
    }

    @Test
    public void globitexTickerTest() throws IOException{
        System.out.println(getExchange().getMarketDataService().getTicker(CurrencyPair.BTC_EUR));
    }

    @Test
    public void globitexTickersTest() throws IOException{
        Params params = new Params() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
        System.out.println(getExchange().getMarketDataService().getTickers(params));
    }

    @Test
    public void getOrderBookTest() throws IOException{
        System.out.println(getExchange().getMarketDataService().getOrderBook(CurrencyPair.ETH_EUR));
    }

    @Test
    public void getTradesTest() throws IOException{
        System.out.println(getExchange().getMarketDataService().getTrades(CurrencyPair.ETH_EUR));
    }

    @Test
    public void getTradesRawTest() throws IOException{
        System.out.println(raw.getGlobitexTradesBySymbol(CurrencyPair.BTC_EUR).getRecentTrades());
    }

    @Test
    public void getAccountsRawTest() throws IOException{

        System.out.println(accountServiceRaw.getGlobitexAccounts().getAccounts());
    }

    @Test
    public void getAccountsTest() throws IOException{
        System.out.println(getExchange().getAccountService().getAccountInfo().getWallet().toString());
    }

    @Test
    public void getTradeHistoryTest() throws IOException{
        TradeHistoryParamsAll paramsAll = new TradeHistoryParamsAll();
        paramsAll.setLimit(30);
        logger.info(getExchange().getTradeService().getTradeHistory(paramsAll).getUserTrades().toString());
    }
}
