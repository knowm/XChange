package nostro.xchange.binance.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.futures.dto.OrderTradeUpdateBinanceUserTransaction;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAsset;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesPosition;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.dto.Order;

import java.math.BigDecimal;
import java.util.List;

public class NostroBinanceFuturesDtoUtils {
    private static ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static BinanceFuturesAsset generateAsset(String symbol, BigDecimal walletBalance, BigDecimal availableBalance, long updateTime) throws JsonProcessingException {
        String content = "{\n" +
                "            \"asset\": \"" + symbol + "\", " +
                "            \"walletBalance\": \"" + walletBalance + "\"," +
                "            \"availableBalance\": \"" + availableBalance + "\"," +
                "            \"updateTime\": " + updateTime +
                "        }";
        return mapper.readValue(content, BinanceFuturesAsset.class);
    }

    public static BinanceFuturesPosition generatePosition(String symbol, int leverage, BigDecimal entryPrice, BigDecimal positionAmt, long updateTime) throws JsonProcessingException {
        String content = "{\n" +
                "            \"symbol\": \"" + symbol + "\"," +
                "            \"leverage\": \"" + leverage + "\",    " +
                "            \"entryPrice\": \"" + entryPrice + "\", " +
                "            \"positionAmt\": \"" + positionAmt + "\", " +
                "            \"updateTime\": " + updateTime + " " +
                "        }";
        return mapper.readValue(content, BinanceFuturesPosition.class);
    }

    public static BinanceFuturesAccountInformation generateAccountInformation(long updateTime, List<BinanceFuturesPosition> positions, List<BinanceFuturesAsset> assets) throws JsonProcessingException {
        return new BinanceFuturesAccountInformation(0, true, true, true, updateTime, null, null, null, null, null, null, null, null, null, null, null, assets, positions);
    }

    public static BinanceFuturesOrder generateOrder(long orderId, OrderStatus orderStatus, String symbol, Long time, Long updateTime, BigDecimal executedQty) throws com.fasterxml.jackson.core.JsonProcessingException {
        String content = "{" +
                "\"orderId\": " + orderId + "," +
                "\"clientOrderId\": \"clientOrderId-" + orderId+ "\"," +
                "\"type\": \"MARKET\"," +
                "\"status\": \"" + orderStatus + "\"," +
                "\"time\": " + time +"," +
                "\"updateTime\": " + updateTime +"," +
                "\"symbol\": \"" + symbol + "\"," +
                "\"avgPrice\": 56473.98000," +
                "\"origQty\": 0.001," +
                "\"executedQty\": " + executedQty + "," +
                "\"side\": \"BUY\"" +
                "}";
        return mapper.readValue(content, BinanceFuturesOrder.class);
    }

    public static BinanceFuturesTrade generateTrade(long tradeId, long orderId, Long time) throws JsonProcessingException {
        String content = "{\n" +
                "    \"buyer\": false,\n" +
                "    \"commission\": \"-0.07819010\",\n" +
                "    \"commissionAsset\": \"USDT\",\n" +
                "    \"id\": " + tradeId + ",\n" +
                "    \"maker\": false,\n" +
                "    \"orderId\": " + orderId + ",\n" +
                "    \"price\": \"7819.01\",\n" +
                "    \"qty\": \"0.002\",\n" +
                "    \"quoteQty\": \"15.63802\",\n" +
                "    \"realizedPnl\": \"-0.91539999\",\n" +
                "    \"side\": \"SELL\",\n" +
                "    \"positionSide\": \"SHORT\",\n" +
                "    \"symbol\": \"BTCUSDT\",\n" +
                "    \"time\": " + time + "\n" +
                "  }";
        return mapper.readValue(content, BinanceFuturesTrade.class);
    }

    public static OrderTradeUpdateBinanceUserTransaction orderTradeUpdateBinanceUserTransaction(String orderId, String orderClientId, String tradeId, String symbol, Order.OrderStatus orderStatus, ExecutionReportBinanceUserTransaction.ExecutionType executionType, long timestamp) throws JsonProcessingException {
        String content = "{\n" +
                "  \"E\": " + (timestamp + 2) + ",\n" +
                "  \"T\": " + timestamp + ",\n" +
                "  \"e\": \"ORDER_TRADE_UPDATE\",\n" +
                "  \"o\": {\n" +
                "    \"L\": \"57167.14\",\n" +
                "    \"N\": \"USDT\",\n" +
                "    \"R\": false,\n" +
                "    \"S\": \"BUY\",\n" +
                "    \"T\": " + timestamp + ",\n" +
                "    \"X\": \"" + orderStatus + "\",\n" +
                "    \"a\": \"0\",\n" +
                "    \"ap\": \"57167.14000\",\n" +
                "    \"b\": \"604.66572\",\n" +
                "    \"c\": \"" + orderClientId + "\",\n" +
                "    \"cp\": false,\n" +
                "    \"f\": \"GTC\",\n" +
                "    \"i\": " + orderId + ",\n" +
                "    \"l\": \"0.001\",\n" +
                "    \"m\": false,\n" +
                "    \"n\": \"0.02286685\",\n" +
                "    \"o\": \"MARKET\",\n" +
                "    \"ot\": \"MARKET\",\n" +
                "    \"p\": \"0\",\n" +
                "    \"pP\": false,\n" +
                "    \"ps\": \"BOTH\",\n" +
                "    \"q\": \"0.001\",\n" +
                "    \"rp\": \"0\",\n" +
                "    \"s\": \"" + symbol + "\",\n" +
                "    \"si\": 0,\n" +
                "    \"sp\": \"0\",\n" +
                "    \"ss\": 0,\n" +
                "    \"t\": " + tradeId + ",\n" +
                "    \"wt\": \"CONTRACT_PRICE\",\n" +
                "    \"x\": \"" + executionType + "\",\n" +
                "    \"z\": \"0.001\"\n" +
                "  }\n" +
                "}";
        return mapper.readValue(content, OrderTradeUpdateBinanceUserTransaction.class);
    }
}
