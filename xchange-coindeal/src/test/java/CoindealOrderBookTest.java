import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coindeal.CoindealExchange;
import org.knowm.xchange.coindeal.dto.marketdata.CoindealOrderBook;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoindealOrderBookTest {

    ExchangeSpecification exSpec = new CoindealExchange().getDefaultExchangeSpecification();
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);


    @Test
    public void coindealOrderbookDtoTest() throws IOException{

        InputStream is = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("org/knowm/xchange/coindeal/dto/example-orderbook.json");

        ObjectMapper mapper = new ObjectMapper();
        CoindealOrderBook coindealOrderBook = mapper.readValue(is, CoindealOrderBook.class);

        //verify that the example data was unmarshalled correctly
        assertThat(coindealOrderBook.getAsks().get(0).getPrice()).isEqualTo("5636.99000000");
        assertThat(coindealOrderBook.getAsks().get(0).getAmount()).isEqualTo("2.07368963");
        assertThat(coindealOrderBook.getBids().get(0).getPrice()).isEqualTo("5598.67000000");
        assertThat(coindealOrderBook.getBids().get(0).getAmount()).isEqualTo("0.10000000");

    }

}
