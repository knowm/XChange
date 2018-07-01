package cz.simplecoin.exchanges.exchangewithdraw;

import cz.simplecoin.exchanges.SimpleApi;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.BitbayDigest;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.service.BitstampAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.math.BigDecimal;
import java.util.List;

public class WithdrawApi {
    private static Logger logger = LoggerFactory.getLogger(WithdrawApi.class);

    public static String withdrawRipple(String exchangeName, Currency currency, BigDecimal amount, String address, String destinationTag) throws Exception {
        SimpleApi simpleApi = SimpleApi.get();
        Exchange exchange = simpleApi.getExchange(exchangeName);
        if (exchangeName.equals("BitbayExchange")) {
            throw new Exception("Ripple withdraw from bitbay not implemented yet");
        }
        WithdrawFundsParams withdrawFundsParams = new RippleWithdrawFundsParams(address, currency, amount, destinationTag);
        return exchange.getAccountService().withdrawFunds(withdrawFundsParams);
    }

    public static String withdraw(String exchangeName, Currency currency, BigDecimal amount, String address, String destinationTag) throws Exception {
        logger.info("Going to withdraw from {} currency {} amount {} to address {} tag {}", exchangeName, currency, amount, address, destinationTag);
        String ret;
        if (currency.equals(Currency.XRP)) {
            ret = withdrawRipple(exchangeName, currency, amount.stripTrailingZeros(), address, destinationTag);
        } else {
            ret = withdraw(exchangeName, currency, amount.stripTrailingZeros(), address);
        }
        logger.info("Withdraw request id {} ", ret);
        return ret;
    }

    public static String withdraw(String exchangeName, Currency currency, BigDecimal amount, String address) throws Exception {

        SimpleApi simpleApi = SimpleApi.get();
        Exchange exchange = simpleApi.getExchange(exchangeName);
        switch (exchangeName){
            case "BitbayExchange":
                return withdrawBitBay(currency, amount, address);
            case "BitstampExchange":
                return withdrawBitstamp(currency, amount, address);
            default:
                return exchange.getAccountService().withdrawFunds(currency, amount, address);
        }
    }

    public static String  withdrawBitstamp(Currency currency, BigDecimal amount, String address) throws Exception{
        Exchange exchange = SimpleApi.get().getExchange("BitstampExchange");
        String id = exchange.getAccountService().withdrawFunds(currency, amount, address);

        BitstampAccountService accountService = (BitstampAccountService)exchange.getAccountService();
        List<WithdrawalRequest> requests = accountService.getWithdrawalRequests(86400L);
        return id;
    }

    public static String  withdrawBitBay(Currency currency, BigDecimal amount, String address) throws Exception{
        Exchange exchange = SimpleApi.get().getExchange("BitbayExchange");
        BitbayAuthenticatedWithdraw bitbayAuthenticatedWithdraw
                = RestProxyFactory.createProxy(BitbayAuthenticatedWithdraw.class, exchange.getExchangeSpecification().getSslUri());

        String secret = exchange.getExchangeSpecification().getSecretKey();
        String key = exchange.getExchangeSpecification().getApiKey();

        String moment = String.valueOf(exchange.getNonceFactory().createValue());
        ParamsDigest sign = BitbayDigest.createInstance(secret);

        return bitbayAuthenticatedWithdraw.transfer(key, sign, moment, currency.getCurrencyCode(), String.valueOf(amount), address).toString();
    }
}
