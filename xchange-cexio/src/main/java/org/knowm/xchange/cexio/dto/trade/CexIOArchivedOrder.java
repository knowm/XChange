package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
  examples
  {
    "id": "1",
    "type": "sell",
    "time": "2017-06-19T12:13:28.883Z",
    "lastTxTime": "2017-06-19T12:25:09.900Z",
    "lastTx": "3912741373",
    "pos": null,
    "status": "d",
    "symbol1": "BTC",
    "symbol2": "GBP",
    "amount": "0.01000000",
    "price": "2059.7258",
    "tfacf": "1",
    "fa:GBP": "0.00",
    "ta:GBP": "1.62",
    "remains": "0.00000000",
    "tfa:GBP": "0.04",
    "tta:GBP": "18.97",
    "a:BTC:cds": "0.01000000",
    "a:GBP:cds": "20.59",
    "f:GBP:cds": "0.04",
    "tradingFeeMaker": "0",
    "tradingFeeTaker": "0.20",
    "tradingFeeUserVolumeAmount": "75631402",
    "orderId": "3912722201"
  }
  Avg. Execution price: 2059.0000

  {
    "id": "2",
    "type": "sell",
    "time": "2017-06-19T10:19:44.750Z",
    "lastTxTime": "2017-06-19T10:19:44.750Z",
    "lastTx": "3912541687",
    "pos": null,
    "status": "d",
    "symbol1": "BTC",
    "symbol2": "GBP",
    "amount": "0.22000000",
    "price": "1976.0001",
    "tfacf": "1",
    "remains": "0.00000000",
    "tfa:GBP": "0.92",
    "tta:GBP": "457.60",
    "a:BTC:cds": "0.22000000",
    "a:GBP:cds": "457.60",
    "f:GBP:cds": "0.92",
    "tradingFeeMaker": "0",
    "tradingFeeTaker": "0.20",
    "tradingFeeUserVolumeAmount": "41281402",
    "orderId": "3912541681"
  }
  Avg. Execution price: 2080.0000

  Example for a market order
  {
    "id": "5241152072",
    "type": "buy",
    "time": "2017-12-20T17:15:18.169Z",
    "lastTxTime": "2017-12-20T17:15:18.169Z",
    "lastTx": "5241152082",
    "pos": null,
    "status": "d",
    "symbol1": "BCH",
    "symbol2": "BTC",
    "amount": "0.00000000",
    "amount2": "0.50000000",
    "remains": "0.00000000",
    "tfa:BTC": "0.00114737",
    "tta:BTC": "0.49885262",
    "a:BCH:cds": "2.15022778",
    "a:BTC:cds": "0.50000000",
    "f:BTC:cds": "0.00114737",
    "tradingFeeTaker": "0.23",
    "tradingFeeUserVolumeAmount": "1315189290",
    "orderId": "5241152072"
  },

  status - "d" — done (fully executed), "c" — canceled (not executed), "cd" — cancel-done (partially executed)
  ta:USD/tta:USD – total amount in current currency (Maker/Taker)
  fa:USD/tfa:USD – fee amount in current currency (Maker/Taker)
  a:BTC:cds – credit, debit and saldo merged amount in current currency
  tradingFeeMaker,tradingFeeTaker – fee % value of Maker/Taker transactions

  what is tfacf??
*/

@JsonDeserialize(using = CexIOArchivedOrder.Deserializer.class)
public class CexIOArchivedOrder {
  public final String id;
  public final String type;
  public final String time;
  public final String lastTxTime;
  public final String lastTx;
  public final String pos;
  public final String status;
  public final String symbol1;
  public final String symbol2;
  public final BigDecimal amount;
  public final BigDecimal orderPrice;
  public final BigDecimal averageExecutionPrice;
  public final String remains;
  public final String tradingFeeMaker;
  public final String tradingFeeTaker;
  public final String tradingFeeUserVolumeAmount;
  public final String orderId;
  public final BigDecimal feeValue;
  public final String feeCcy;

  public CexIOArchivedOrder(
      String id,
      String type,
      String time,
      String lastTxTime,
      String lastTx,
      String pos,
      String status,
      String symbol1,
      String symbol2,
      BigDecimal amount,
      BigDecimal orderPrice,
      BigDecimal averageExecutionPrice,
      String remains,
      String tradingFeeMaker,
      String tradingFeeTaker,
      String tradingFeeUserVolumeAmount,
      String orderId,
      BigDecimal feeValue,
      String feeCcy) {
    this.id = id;
    this.type = type;
    this.time = time;
    this.lastTxTime = lastTxTime;
    this.lastTx = lastTx;
    this.pos = pos;
    this.status = status;
    this.symbol1 = symbol1;
    this.symbol2 = symbol2;
    this.amount = amount;
    this.orderPrice = orderPrice;
    this.averageExecutionPrice = averageExecutionPrice;
    this.remains = remains;
    this.tradingFeeMaker = tradingFeeMaker;
    this.tradingFeeTaker = tradingFeeTaker;
    this.tradingFeeUserVolumeAmount = tradingFeeUserVolumeAmount;
    this.orderId = orderId;
    this.feeValue = feeValue;
    this.feeCcy = feeCcy;
  }

  static class Deserializer extends JsonDeserializer<CexIOArchivedOrder> {

    @Override
    public CexIOArchivedOrder deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException {
      JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

      try {

        Map<String, String> map = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> tradesResultIterator = jsonNode.fields();
        while (tradesResultIterator.hasNext()) {
          Map.Entry<String, JsonNode> entry = tradesResultIterator.next();
          map.put(entry.getKey(), entry.getValue().asText());
        }

        BigDecimal feeValue = null;
        String feeCcy = null;
        Pattern pattern = Pattern.compile("([af])\\:(.*?)\\:cds");

        Map<String, BigDecimal> filled = new HashMap<>();

        for (String key : map.keySet()) {
          Matcher matcher = pattern.matcher(key);
          if (matcher.matches()) {
            String feeOrAmount = matcher.group(1);

            String ccy = matcher.group(2);
            BigDecimal value = new BigDecimal(map.get(key));

            if (feeOrAmount.equals("a")) {
              filled.put(ccy, value);
            } else if (feeOrAmount.equals("f")) {
              feeValue = value;
              feeCcy = ccy;
            } else {
              throw new IllegalStateException("Cannot parse " + key);
            }
          }
        }

        String counter = map.get("symbol2");
        String base = map.get("symbol1");

        BigDecimal orderPrice = null;
        // market orders don't have a price
        if (map.containsKey("price")) orderPrice = new BigDecimal(map.get("price"));

        int priceScale = 8; // todo: check if this is correct for all
        BigDecimal counterAmount = filled.get(counter);
        BigDecimal baseAmount = filled.get(base);

        BigDecimal averageExecutionPrice = null;
        if (baseAmount != null && baseAmount.compareTo(BigDecimal.ZERO) > 0)
          averageExecutionPrice =
              counterAmount.divide(baseAmount, priceScale, RoundingMode.HALF_UP);

        BigDecimal amount = new BigDecimal(map.get("amount"));

        if (amount.compareTo(BigDecimal.ZERO) == 0 && map.containsKey("amount2")) {
          // the 'amount' field changes name for market orders
          // and represents the amount in the counter ccy instead
          // of the base ccy
          BigDecimal amount2 = new BigDecimal(map.get("amount2"));

          amount = amount2.divide(averageExecutionPrice, 8, RoundingMode.HALF_UP);
        }

        return new CexIOArchivedOrder(
            map.get("id"),
            map.get("type"),
            map.get("time"),
            map.get("lastTxTime"),
            map.get("lastTx"),
            map.get("pos"),
            map.get("status"),
            base,
            counter,
            amount,
            orderPrice,
            averageExecutionPrice,
            map.get("remains"),
            map.get("tradingFeeMaker"),
            map.get("tradingFeeTaker"),
            map.get("tradingFeeUserVolumeAmount"),
            map.get("orderId"),
            feeValue,
            feeCcy);
      } catch (Exception e) {
        throw new IllegalStateException("Failed to parse " + jsonNode, e);
      }
    }
  }
}
