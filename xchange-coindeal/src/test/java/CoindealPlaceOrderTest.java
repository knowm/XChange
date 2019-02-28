import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import org.knowm.xchange.coindeal.service.CoindealTradeService;
import org.knowm.xchange.coindeal.service.CoindealTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoindealPlaceOrderTest {

    @Test
    public void testUnmarshall() throws IOException {

        InputStream is = CoindealTradeHistoryTest.class
                .getResourceAsStream("/org/knowm/xchange/coindeal/dto/example-tradehistory.json");

        ObjectMapper mapper = new ObjectMapper();
        List<CoindealTradeHistory> coindealTradeHistory = Arrays.asList(mapper.readValue(is, CoindealTradeHistory[].class));

        //verify that the example data was unmarshalled correctly
        assertThat(coindealTradeHistory.get(0).getId()).isEqualTo(0);
        assertThat(coindealTradeHistory.get(0).getClientOrderId()).isEqualTo("string");
        assertThat(coindealTradeHistory.get(0).getOrderId()).isEqualTo("0");
        assertThat(coindealTradeHistory.get(0).getSymbol()).isEqualTo("string");
        assertThat(coindealTradeHistory.get(1).getId()).isEqualTo(1);
        assertThat(coindealTradeHistory.get(1).getClientOrderId()).isEqualTo("string");
        assertThat(coindealTradeHistory.get(1).getOrderId()).isEqualTo("1");
        assertThat(coindealTradeHistory.get(1).getSymbol()).isEqualTo("string");
    }
}
