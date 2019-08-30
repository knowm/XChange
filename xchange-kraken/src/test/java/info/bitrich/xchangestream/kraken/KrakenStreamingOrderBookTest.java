package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class KrakenStreamingOrderBookTest {

    private static final Logger log = LoggerFactory.getLogger(KrakenStreamingOrderBookTest.class);

    @Test
    public void orderBookTest() throws InterruptedException{
        StreamingExchange krakenExchange = StreamingExchangeFactory.INSTANCE.createExchange(KrakenStreamingExchange.class.getName());
        krakenExchange.connect(ProductSubscription.create().addAll(CurrencyPair.ETH_BTC).addAll(CurrencyPair.BTC_USD).addAll(CurrencyPair.BTC_EUR).build()).blockingAwait();
        Disposable dis = krakenExchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.ETH_BTC).subscribe(s->{

            assertThat(s).isEqualTo(KrakenOrderBookUtils.verifyKrakenOrderBook(s));
            assertThat(s.getBids().get(0).getLimitPrice()).isLessThan(s.getAsks().get(0).getLimitPrice());

            for(int i=0;i<s.getBids().size();i++){
                if(i>0){
                    LimitOrder previousLimitOrder = s.getBids().get(i-1);
                    assertThat(previousLimitOrder.getLimitPrice()).isGreaterThan(s.getBids().get(i).getLimitPrice());
                }
            }
            for(int i=0;i<s.getAsks().size();i++){
                if(i>0){
                    LimitOrder previousLimitOrder = s.getAsks().get(i-1);
                    assertThat(previousLimitOrder.getLimitPrice()).isLessThan(s.getAsks().get(i).getLimitPrice());
                }
            }
        });
        Disposable dis2 = krakenExchange.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_EUR).subscribe(s->{

            assertThat(s).isEqualTo(KrakenOrderBookUtils.verifyKrakenOrderBook(s));
            assertThat(s.getBids().get(0).getLimitPrice()).isLessThan(s.getAsks().get(0).getLimitPrice());

            for(int i=0;i<s.getBids().size();i++){
                if(i>0){
                    LimitOrder previousLimitOrder = s.getBids().get(i-1);
                    assertThat(previousLimitOrder.getLimitPrice()).isGreaterThan(s.getBids().get(i).getLimitPrice());
                }
            }
            for(int i=0;i<s.getAsks().size();i++){
                if(i>0){
                    LimitOrder previousLimitOrder = s.getAsks().get(i-1);
                    assertThat(previousLimitOrder.getLimitPrice()).isLessThan(s.getAsks().get(i).getLimitPrice());
                }
            }
        });
        TimeUnit.SECONDS.sleep(10);
        dis.dispose();
        dis2.dispose();
        log.info("Both Orderbooks are correct!");
    }
}
