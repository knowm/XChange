package org.knowm.xchange.lykke.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.lykke.LykkeException;
import org.knowm.xchange.lykke.LykkeAdapter;
import org.knowm.xchange.lykke.dto.trade.LykkeLimitOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeTradeHistory;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.util.List;

public class LykkeTradeServiceRaw extends LykkeBaseService{

    private Logger logger = LoggerFactory.getLogger(LykkeTradeServiceRaw.class);

    public LykkeTradeServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public List<LykkeTradeHistory> getLykkeTradeHistory(TradeHistoryParamsAll paramsAll) throws IOException {
        try {
            return lykke.getTradeHistory(
                    LykkeAdapter.adaptToAssetPair(paramsAll.getCurrencyPair()),
                    paramsAll.getPageLength(),
                    apiKey);
        }catch (LykkeException e){
            throw new ExchangeException(e.getMessage());
        }
    }
    public LykkeTradeHistory getTradeHistoryById(String tradeId) throws IOException{
        try{
            return lykke.getTradeHistoryById(tradeId,apiKey);
        }catch (LykkeException e){
            throw new ExchangeException(e.getMessage());
        }
    }

    public List<LykkeOrder> getLastOrders() throws IOException{
        try{
            //default: 100
            return lykke.getLastOrders("InOrderBook",100,apiKey);
        }catch (LykkeException e){
            throw new ExchangeException(e.getMessage());
        }
    }
    public List<LykkeOrder> getMathedOrders(int limit) throws IOException{
        try{
            return lykke.getLastOrders("All",limit,apiKey);
        }catch (LykkeException e){
            throw new ExchangeException(e.getMessage());
        }
    }

    public String placeLimitOrder(LykkeLimitOrder lykkeLimitOrder) throws IOException {
        try{
            return lykke.postLimitOrder(lykkeLimitOrder,apiKey);
        }catch (LykkeException e){
            throw new ExchangeException(e.getMessage());
        }
    }

    public boolean cancelLykkeOrder(String id) throws IOException,LykkeException {
//        try{
            lykke.cancelOrderById(id,apiKey);
            return true;
//        }catch (LykkeException e){
//            throw new ExchangeException(e.getMessage());
//        }
    }
    public boolean cancelAllLykkeOrders(String assetPairId) throws IOException,HTTPException {
        try{
            lykke.cancelAllOrders(assetPairId,apiKey);
            return true;
        }catch (LykkeException e){
            throw new ExchangeException(e.getMessage());
        }
    }
}
