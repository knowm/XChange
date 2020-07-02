package org.knowm.xchange.bitcoinde.v4.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeTrustLevel;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;

public class BitcoindeMyTradesWrapperTest {

  @Test
  public void testBitcoindeMyTradesWrapper() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeMyTradesWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/my_trades.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    BitcoindeMyTradesWrapper myTradesWrapper = mapper.readValue(is, BitcoindeMyTradesWrapper.class);

    // Make sure trade values are correct
    assertThat(myTradesWrapper.getTrades()).isNotEmpty();
    assertThat(myTradesWrapper.getTrades().size()).isEqualTo(1);

    BitcoindeMyTrade trade = myTradesWrapper.getTrades().get(0);
    assertThat(trade.getTradeId()).isEqualTo("2EDYNS");
    assertThat(trade.getTradingPair()).isEqualByComparingTo(CurrencyPair.BTC_EUR);
    assertThat(trade.getIsExternalWalletTrade()).isFalse();
    assertThat(trade.getType()).isEqualByComparingTo(BitcoindeType.SELL);
    assertThat(trade.getAmountCurrencyToTrade()).isEqualByComparingTo("0.5");
    assertThat(trade.getAmountCurrencyToTradeAfterFee()).isEqualByComparingTo("0.4975");
    assertThat(trade.getPrice()).isEqualByComparingTo("250.55");
    assertThat(trade.getVolumeCurrencyToPay()).isEqualByComparingTo("125.28");
    assertThat(trade.getVolumeCurrencyToPayAfterFee()).isEqualByComparingTo("124.68");
    assertThat(trade.getFeeCurrencyToPay()).isEqualByComparingTo("0.6");
    assertThat(trade.getFeeCurrencyToTrade()).isEqualByComparingTo("0.0025");
    assertThat(trade.getNewOrderIdForRemainingAmount()).isEqualTo("C4Y8HD");
    assertThat(trade.getState()).isEqualByComparingTo(BitcoindeMyTrade.State.SUCCESSFUL);
    assertThat(trade.getIsTradeMarkedAsPaid()).isFalse();
    assertThat(trade.getTradeMarkedAsPaidAt()).isNull();
    assertThat(trade.getMyRatingForTradingPartner())
        .isEqualByComparingTo(BitcoindeMyTrade.Rating.POSITIVE);
    assertThat(trade.getTradingPartnerInformation()).isNotNull();
    assertThat(trade.getTradingPartnerInformation().getUserName()).isEqualTo("testuser");
    assertThat(trade.getTradingPartnerInformation().getKycFull()).isTrue();
    assertThat(trade.getTradingPartnerInformation().getTrustLevel())
        .isEqualByComparingTo(BitcoindeTrustLevel.GOLD);
    assertThat(trade.getTradingPartnerInformation().getDepositor()).isEqualTo("Max Musterman");
    assertThat(trade.getTradingPartnerInformation().getIban()).isEqualTo("DE02370501980001802057");
    assertThat(trade.getTradingPartnerInformation().getBankName()).isEqualTo("sparkasse");
    assertThat(trade.getTradingPartnerInformation().getBic()).isEqualTo("HASPDEHHXXX");
    assertThat(trade.getTradingPartnerInformation().getRating()).isEqualTo(99);
    assertThat(trade.getTradingPartnerInformation().getAmountTrades()).isEqualTo(42);
    assertThat(trade.getTradingPartnerInformation().getSeatOfBank()).isEqualTo("DE");
    assertThat(trade.getCreatedAt()).isEqualTo("2015-01-10T15:00:00+02:00");
    assertThat(trade.getSuccessfullyFinishedAt()).isEqualTo("2015-01-10T15:00:00+02:00");
    assertThat(trade.getPayment_method()).isEqualByComparingTo(BitcoindeMyTrade.PaymentMethod.SEPA);
  }
}
