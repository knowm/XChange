package org.knowm.xchange.ripple.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.ripple.dto.RippleAmount;

public class RippleOrderBookTest {

  @Test
  public void unmarshalTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/marketdata/example-order-book.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderBook orderBook = mapper.readValue(is, RippleOrderBook.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(orderBook.getOrderBook()).isEqualTo("XRP/BTC+rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(orderBook.getLedger()).isEqualTo(13796967);
    assertThat(orderBook.isValidated()).isEqualTo(true);
    assertThat(orderBook.isSuccess()).isEqualTo(true);

    // check number of bid orders and values in first order
    final List<RippleOrder> bids = orderBook.getBids();
    assertThat(bids).hasSize(10);
    final RippleOrder firstBid = bids.get(0);
    assertThat(firstBid.getOrderMaker()).isEqualTo("rwmnMXpRXFqHLYzwyeggJQ8fu5bPyxqup1");
    assertThat(firstBid.getPassive()).isEqualTo(false);
    assertThat(firstBid.getSell()).isEqualTo(false);
    assertThat(firstBid.getSequence()).isEqualTo(409019);

    final RippleAmount bidPrice = firstBid.getPrice();
    assertThat(bidPrice.getCurrency()).isEqualTo("BTC");
    assertThat(bidPrice.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(bidPrice.getValue()).isEqualTo("0.00003373502057421809");

    final RippleAmount bidGetsFunded = firstBid.getTakerGetsFunded();
    assertThat(bidGetsFunded.getCurrency()).isEqualTo("BTC");
    assertThat(bidGetsFunded.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(bidGetsFunded.getValue()).isEqualTo("1.346439063213329");

    final RippleAmount bidGetsTotal = firstBid.getTakerGetsTotal();
    assertThat(bidGetsTotal.getCurrency()).isEqualTo("BTC");
    assertThat(bidGetsTotal.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(bidGetsTotal.getValue()).isEqualTo("1.346439063213329");

    final RippleAmount bidPaysFunded = firstBid.getTakerPaysFunded();
    assertThat(bidPaysFunded.getCurrency()).isEqualTo("XRP");
    assertThat(bidPaysFunded.getCounterparty()).isEqualTo("");
    assertThat(bidPaysFunded.getValue()).isEqualTo("39912.205189");

    final RippleAmount bidPaysTotal = firstBid.getTakerPaysTotal();
    assertThat(bidPaysTotal.getCurrency()).isEqualTo("XRP");
    assertThat(bidPaysTotal.getCounterparty()).isEqualTo("");
    assertThat(bidPaysTotal.getValue()).isEqualTo("39912.205189");

    // check number of ask orders and values in last order
    final List<RippleOrder> asks = orderBook.getAsks();
    assertThat(asks).hasSize(10);

    final RippleOrder lastAsk = asks.get(9);
    assertThat(lastAsk.getOrderMaker()).isEqualTo("rwBYyfufTzk77zUSKEu4MvixfarC35av1J");
    assertThat(lastAsk.getPassive()).isEqualTo(false);
    assertThat(lastAsk.getSell()).isEqualTo(false);
    assertThat(lastAsk.getSequence()).isEqualTo(966096);

    final RippleAmount askPrice = lastAsk.getPrice();
    assertThat(askPrice.getCurrency()).isEqualTo("BTC");
    assertThat(askPrice.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(askPrice.getValue()).isEqualTo("0.00003426853990917158");

    final RippleAmount askGetsFunded = lastAsk.getTakerGetsFunded();
    assertThat(askGetsFunded.getCurrency()).isEqualTo("XRP");
    assertThat(askGetsFunded.getCounterparty()).isEqualTo("");
    assertThat(askGetsFunded.getValue()).isEqualTo("27199.15512");

    final RippleAmount askGetsTotal = lastAsk.getTakerGetsTotal();
    assertThat(askGetsTotal.getCurrency()).isEqualTo("XRP");
    assertThat(askGetsTotal.getCounterparty()).isEqualTo("");
    assertThat(askGetsTotal.getValue()).isEqualTo("27199.15512");

    final RippleAmount askPaysFunded = lastAsk.getTakerPaysFunded();
    assertThat(askPaysFunded.getCurrency()).isEqualTo("BTC");
    assertThat(askPaysFunded.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(askPaysFunded.getValue()).isEqualTo("0.9320753327254685");

    final RippleAmount askPaysTotal = lastAsk.getTakerPaysTotal();
    assertThat(askPaysTotal.getCurrency()).isEqualTo("BTC");
    assertThat(askPaysTotal.getCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(askPaysTotal.getValue()).isEqualTo("0.9320753327254685");
  }
}
