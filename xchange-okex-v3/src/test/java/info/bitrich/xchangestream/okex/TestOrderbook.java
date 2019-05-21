package info.bitrich.xchangestream.okex;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.okex.dto.OkCoinOrderbook;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.marketdata.OkCoinDepth;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class TestOrderbook {

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, OkCoinOrderbook> orderbooks = new HashMap<>();

    @Test
    public void testParse() throws IOException, ParseException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Read in the JSON from the example resources
        InputStream is =
                TestOrderbook.class.getResourceAsStream(
                        "/order-book-partial.json");
        JsonNode s = mapper.readTree(is);
        String instrumentId = "BTC-USD-190628";
        OkCoinOrderbook okCoinOrderbook;
        for (JsonNode data : s.get("data")) {
            if (instrumentId.equals(data.get("instrument_id").asText())) {
                if ("partial".equals(s.get("action").asText())) {
                    if (!"partial".equals(s.get("action").asText())) {

                    }
                    OkCoinDepth okCoinDepth = this.mapper.treeToValue(data, OkCoinDepth.class);
                    okCoinOrderbook = new OkCoinOrderbook(okCoinDepth);
                    this.orderbooks.put(instrumentId, okCoinOrderbook);
                } else {
                    okCoinOrderbook = this.orderbooks.get(instrumentId);
                    BigDecimal[][] bidLevels;
                    if (data.has("asks") && data.get("asks").size() > 0) {
                        bidLevels = this.mapper.treeToValue(data.get("asks"), BigDecimal[][].class);
                        okCoinOrderbook.updateLevels(bidLevels, Order.OrderType.ASK);
                    }

                    if (data.has("bids") && data.get("bids").size() > 0) {
                        bidLevels = this.mapper.treeToValue(data.get("bids"), BigDecimal[][].class);
                        okCoinOrderbook.updateLevels(bidLevels, Order.OrderType.BID);
                    }
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                long epoch = format.parse(data.get("timestamp").asText()).getTime();
                OkCoinAdapters.adaptOrderBook(okCoinOrderbook.toOkCoinDepth(epoch), CurrencyPair.XBT_USD);
            }
        }
    }

}
