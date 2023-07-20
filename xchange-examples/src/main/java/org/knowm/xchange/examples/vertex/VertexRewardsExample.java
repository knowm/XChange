package org.knowm.xchange.examples.vertex;

import com.knowm.xchange.vertex.VertexStreamingExchange;
import com.knowm.xchange.vertex.dto.RewardsList;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import java.io.IOException;
import org.knowm.xchange.ExchangeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

public class VertexRewardsExample {

    private static final Logger log = LoggerFactory.getLogger(VertexRewardsExample.class);


    public static void main(String[] args) throws IOException, InterruptedException {

        ExchangeSpecification exchangeSpecification = StreamingExchangeFactory.INSTANCE
                .createExchangeWithoutSpecification(VertexStreamingExchange.class)
                .getDefaultExchangeSpecification();


        ECKeyPair ecKeyPair = Credentials.create(System.getProperty("WALLET_PRIVATE_KEY")).getEcKeyPair();
        String address = "0x" + Keys.getAddress(ecKeyPair.getPublicKey());
        String subAccount = "default";

        exchangeSpecification.setApiKey(address);
        exchangeSpecification.setSecretKey(Numeric.toHexStringNoPrefix(ecKeyPair.getPrivateKey()));
        exchangeSpecification.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);
        exchangeSpecification.setExchangeSpecificParametersItem(VertexStreamingExchange.USE_LEVERAGE, true);

        exchangeSpecification.setUserName(subAccount); //subaccount name

        VertexStreamingExchange exchange = (VertexStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

        exchange.connect().blockingAwait();

        log.info("Querying rewards for address: " + address);
        RewardsList rewardsList = exchange.queryRewards(address);
        log.info("Response: " + rewardsList);

        exchange.disconnect().blockingAwait();

    }
}
