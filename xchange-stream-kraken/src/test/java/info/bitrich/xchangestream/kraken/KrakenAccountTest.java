package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AddressWithTag;

public class KrakenAccountTest {
    public static void main(String[] args) {
        ExchangeSpecification exchangeSpecification =
                new ExchangeSpecification(KrakenStreamingExchange.class);
        exchangeSpecification.setApiKey(args[0]);
        exchangeSpecification.setSecretKey(args[1]);
        exchangeSpecification.setShouldLoadRemoteMetaData(true);


        StreamingExchange streamingExchange = StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

        try {
            AddressWithTag address = streamingExchange.getAccountService().requestDepositAddressData(new Currency("USDT"));
            System.out.println("Address "+address);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
