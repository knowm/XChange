package info.bitrich.xchangestream.bitfinex;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexAuthRequestStatus;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuth;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthPreTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.knowm.xchange.service.BaseParamsDigest.HMAC_SHA_384;

public class BitfinexStreamingRawService extends JsonNettyStreamingService {
    private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingRawService.class);

    private static final String AUTH = "auth";
    private static final String STATUS = "status";
    private static final String MESSAGE = "msg";
    private static final String EVENT = "event";

    private String apiKey;
    private String apiSecret;

    private PublishSubject<BitfinexWebSocketAuthPreTrade> subjectPreTrade = PublishSubject.create();
    private PublishSubject<BitfinexWebSocketAuthTrade> subjectTrade = PublishSubject.create();
    private PublishSubject<BitfinexWebSocketAuthOrder> subjectOrder = PublishSubject.create();
    private PublishSubject<BitfinexWebSocketAuthBalance> subjectBalance = PublishSubject.create();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BitfinexStreamingRawService(String apiUrl) {
        super(apiUrl, Integer.MAX_VALUE);
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) throws IOException {
        return null;
    }

    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return null;
    }

    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        return null;
    }

    @Override
    public void messageHandler(String message) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            LOG.error("Error parsing incoming message to JSON: {}", message);
            subjectOrder.onError(e);
            return;
        }
        handleMessage(jsonNode);
    }

    @Override
    protected void handleMessage(JsonNode message) {
        LOG.debug("Receiving message: {}", message);

        if (message.isArray()) {
            String type = message.get(1).asText();
            if (type.equals("hb")) {
                return;
            }
        }

        JsonNode event = message.get(EVENT);
        if (event != null && event.textValue().equalsIgnoreCase("info"))
            auth();

        if (event != null && event.textValue().equals(AUTH) && message.get(STATUS).textValue().equals(BitfinexAuthRequestStatus.FAILED.name())) {
            LOG.error("Authentication error: {}", message.get(MESSAGE));
            return;
        }

        if (message.isArray() && message.size() == 3) {
            String type = message.get(1).asText();
            JsonNode object = message.get(2);
            switch (type) {
                case "te":
                    addPreTrade(object);
                    break;
                case "tu":
                    addTrade(object);
                    break;
                case "os":
                    addOrder(object);
                    break;
                case "on":
                case "ou":
                case "oc":
                    updateOrder(object);
                    break;
                case "ws":
                    updateBalances(object);
                    break;
                case "wu":
                    updateBalance(object);
                    break;
            }
        }
    }

    private void addPreTrade(JsonNode preTrade) {
        if (preTrade.size() < 12) {
            LOG.error("addPreTrade unexpected record size={}, record={}", preTrade.size(), preTrade.toString());
            return;
        }
        long id = preTrade.get(0).longValue();
        String pair = preTrade.get(1).textValue();
        long mtsCreate = preTrade.get(2).longValue();
        long orderId = preTrade.get(3).longValue();
        BigDecimal execAmount = preTrade.get(4).decimalValue();
        BigDecimal execPrice = preTrade.get(5).decimalValue();
        String orderType = preTrade.get(6).textValue();
        BigDecimal orderPrice = preTrade.get(7).decimalValue();
        int maker = preTrade.get(8).intValue();
        BitfinexWebSocketAuthPreTrade preTradeObject = new BitfinexWebSocketAuthPreTrade(id, pair, mtsCreate, orderId,
                execAmount, execPrice, orderType, orderPrice, maker);
        LOG.debug("New pre trade: {}", preTradeObject);
        subjectPreTrade.onNext(preTradeObject);
    }

    private void addTrade(JsonNode trade) {
        if (trade.size() < 11) {
            LOG.error("addTrade unexpected record size={}, record={}", trade.size(), trade.toString());
            return;
        }
        long id = trade.get(0).longValue();
        String pair = trade.get(1).textValue();
        long mtsCreate = trade.get(2).longValue();
        long orderId = trade.get(3).longValue();
        BigDecimal execAmount = trade.get(4).decimalValue();
        BigDecimal execPrice = trade.get(5).decimalValue();
        String orderType = trade.get(6).textValue();
        BigDecimal orderPrice = trade.get(7).decimalValue();
        int maker = trade.get(8).intValue();
        BigDecimal fee = trade.get(9).decimalValue();
        String currency = trade.get(10).textValue();
        BitfinexWebSocketAuthTrade tradeObject = new BitfinexWebSocketAuthTrade(
                id, pair, mtsCreate, orderId, execAmount, execPrice, orderType, orderPrice, maker, fee, currency
        );
        LOG.debug("New trade: {}", tradeObject);
        subjectTrade.onNext(tradeObject);
    }

    private void addOrder(JsonNode orders) {
        for (final JsonNode order : orders) {
            BitfinexWebSocketAuthOrder orderObject = createOrderObject(order);
            if (orderObject != null) {
                LOG.debug("New order: {}", orderObject);
                subjectOrder.onNext(orderObject);
            }
        }
    }

    private void updateOrder(JsonNode order) {
        BitfinexWebSocketAuthOrder orderObject = createOrderObject(order);
        if (orderObject != null) {
            LOG.debug("Updated order: {}", orderObject);
            subjectOrder.onNext(orderObject);
        }
    }

    private void updateBalance(JsonNode balance) {
        BitfinexWebSocketAuthBalance balanceObject = createBalanceObject(balance);
        if (balanceObject != null) {
            LOG.debug("Balance: {}", balanceObject);
            subjectBalance.onNext(balanceObject);
        }
    }

    private void updateBalances(JsonNode balances) {
        for (final JsonNode balance : balances) {
            BitfinexWebSocketAuthBalance balanceObject = createBalanceObject(balance);
            if (balanceObject != null) {
                LOG.debug("Balance: {}", balanceObject);
                subjectBalance.onNext(balanceObject);
            }
        }
    }

    private BitfinexWebSocketAuthBalance createBalanceObject(JsonNode balance) {
        if (balance.size() < 5) {
            LOG.error("createBalanceObject unexpected record size={}, record={}", balance.size(), balance.toString());
            return null;
        }

        String walletType = balance.get(0).textValue();
        String currency = balance.get(1).textValue();
        BigDecimal balanceValue = balance.get(2).decimalValue();
        BigDecimal unsettledInterest = balance.get(3).decimalValue();
        BigDecimal balanceAvailable = balance.get(4).asText().equals("null") ? null : balance.get(4).decimalValue();

        return new BitfinexWebSocketAuthBalance(walletType, currency, balanceValue, unsettledInterest, balanceAvailable);
    }

    private BitfinexWebSocketAuthOrder createOrderObject(JsonNode order) {
        if (order.size() < 32) {
            LOG.error("createOrderObject unexpected record size={}, record={}", order.size(), order.toString());
            return null;
        }

        long id = order.get(0).longValue();
        long groupId = order.get(1).longValue();
        long cid = order.get(2).longValue();
        String symbol = order.get(3).textValue();
        long mtsCreate = order.get(4).longValue();
        long mtsUpdate = order.get(5).longValue();
        BigDecimal amount = order.get(6).decimalValue();
        BigDecimal amountOrig = order.get(7).decimalValue();
        String type = order.get(8).textValue();
        String typePrev = order.get(9).textValue();
        int flags = order.get(12).intValue();
        String orderStatus = order.get(13).textValue();
        BigDecimal price = order.get(16).decimalValue();
        BigDecimal priceAvg = order.get(17).decimalValue();
        BigDecimal priceTrailing = order.get(18).decimalValue();
        BigDecimal priceAuxLimit = order.get(19).decimalValue();
        long placedId = order.get(25).longValue();

        return new BitfinexWebSocketAuthOrder(
                id, groupId, cid, symbol, mtsCreate, mtsUpdate, amount, amountOrig,
                type, typePrev, orderStatus, price, priceAvg, priceTrailing,
                priceAuxLimit, placedId, flags
        );
    }

    public void auth() {
        long nonce = System.currentTimeMillis() * 1000;
        String payload = "AUTH" + nonce;
        String signature;
        try {
            Mac macEncoder = Mac.getInstance(HMAC_SHA_384);
            SecretKeySpec secretKeySpec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA_384);
            macEncoder.init(secretKeySpec);
            byte[] result = macEncoder.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            signature = DatatypeConverter.printHexBinary(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            LOG.error("auth. Sign failed error={}", e.getMessage());
            return;
        }
        BitfinexWebSocketAuth message = new BitfinexWebSocketAuth(
                apiKey, payload, String.valueOf(nonce), signature.toLowerCase()
        );
        sendMessage(message);
    }


    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    private void sendMessage(Object message) {
        try {
            sendMessage(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            LOG.error("Error creating json message: {}", e.getMessage());
        }
    }

    public Observable<BitfinexWebSocketAuthOrder> getAuthenticatedOrders() {
        return subjectOrder.share();
    }

    public Observable<BitfinexWebSocketAuthPreTrade> getAuthenticatedPreTrades() {
        return subjectPreTrade.share();
    }

    public Observable<BitfinexWebSocketAuthTrade> getAuthenticatedTrades() {
        return subjectTrade.share();
    }

    public Observable<BitfinexWebSocketAuthBalance> getAuthenticatedBalances() {
        return subjectBalance.share();
    }
}
