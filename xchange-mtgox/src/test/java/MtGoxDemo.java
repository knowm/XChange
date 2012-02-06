import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.MarketDataService;
import com.xeiam.xchange.dto.marketdata.Tick;

import java.util.Collection;

/**
 * <p>Example showing the following:</p>
 * <ul>
 * <li>Connecting to Mt Gox Bitcoin exchange</li>
 * <li>Retrieving market data</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class MtGoxDemo {
  
  public static void main(String[] args) {

    // Use the factory to get the version 1 MtGox exchange API
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");
    
    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = mtGox.getMarketDataService();

    // Get the latest data
    Collection<Tick> ticks = marketDataService.getLatestMarketData();
    
    for (Tick tick : ticks) {
      System.out.println(tick);
    }

  }
}
