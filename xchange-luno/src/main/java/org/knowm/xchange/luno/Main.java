package org.knowm.xchange.luno;

import java.io.IOException;

import org.knowm.xchange.luno.dto.LunoException;
import org.knowm.xchange.luno.dto.trade.LunoOrders;
import org.knowm.xchange.luno.dto.trade.State;

public class Main {

    public static void main(String[] args) throws LunoException, IOException {
        //LunoAuthenticated luno = RestProxyFactory.createProxy(LunoAuthenticated.class, "https://staging.mybitx.com");        //https://api.mybitx.com  https://staging.mybitx.com
        //this.auth = new BasicAuthCredentials("jjuunycm6kusb", "QqmLDYIS0dm76FccAk4E6T7Sywc7XB7fMPfoOmSfLfI"); // prod
        //ParamsDigest auth = new BasicAuthCredentials("d3jt2brhvyngu", "FxWB2GdAa1eUZ33iTW016TAp8xDaVjW_HWwXzAFUnHE");   // staging
        
        LunoAPI luno = new LunoAPIImpl("jjuunycm6kusb", "QqmLDYIS0dm76FccAk4E6T7Sywc7XB7fMPfoOmSfLfI");
        
        //LunoTicker ticker = luno.ticker("XBTZAR");
        //System.out.println(ticker);
        
        //LunoTickers tickers = luno.tickers();
        //System.out.println(tickers);
        
        //LunoOrderBook orderbook = luno.orderbook("XBTZAR");
        //System.out.println(orderbook);
        
        //LunoTrades trades = luno.trades("XBTZAR", /*System.currentTimeMillis() - TimeUnit.HOURS.toMillis(4)*/ null);
        //System.out.println(trades);
        
        //LunoAccount createAccount = luno.createAccount(auth, "XBT", "Test acc XBT");
        //System.out.println(createAccount);
        
        //LunoBalance balance = luno.balance(auth);
        //System.out.println(balance);
        
        // EUR 6050782087874252277
        // XBT 1063338354564963140
        
        
        // Staging: 
        //Balance [accountId=5555250856612986502, asset=XBT, balance=1.00, reserved=0.00, unconfirmed=0.00, name=null], 
        //Balance [accountId=1598213544044474753, asset=XBT, balance=0.00, reserved=0.00, unconfirmed=0.00, name=Test acc XBT], 
        //Balance [accountId=3877890324048557142, asset=ZAR, balance=20000.00, reserved=0.00, unconfirmed=0.00, name=null]]]
        // XBT 5555250856612986502, name=null, 1XBT
        // XBT 1598213544044474753, name=Test acc XBT
        // ZAR 3877890324048557142, 2k
        
        //LunoAccountTransactions transactions = luno.transactions(auth, "5555250856612986502", -100, 0);
        //System.out.println(transactions);
        
        //LunoPendingTransactions pendingTransactions = luno.pendingTransactions(auth, "3877890324048557142");
        //System.out.println(pendingTransactions);
        
        LunoOrders listorders = luno.listOrders(State.PENDING, null);
        System.out.println(listorders);
        
        //LunoPostOrder postOrder = luno.postLimitOrder(auth, "XBTZAR", OrderType.BID, new BigDecimal("0.1"), new BigDecimal("555"), null, null);
        //System.out.println(postOrder);          // BXFE8DQTG645PDA, BXKYHVSU5FD36JM, BXFJVXNQR5TBXD5
        
        //LunoPostOrder postMarketOrder = luno.postMarketOrder(auth, "XBTZAR", OrderType.BUY, new BigDecimal("10"), null, null, null);
        //System.out.println(postMarketOrder);    // BXEUZF2WCFTY3EE      BXJJZ3E3KTD6WNF
        // ErrMinVolumeLimit: This order volume is below the minimum of 0.0005.
        
        //LunoBoolean stopOrder = luno.stopOrder(auth, "BXFJVXNQR5TBXD5");
        //System.out.println(stopOrder);
        
        //Order order = luno.getOrder(auth, "BXFE8DQTG645PDA");
        //System.out.println(order);
        
        //System.out.println(luno.listTrades(auth, "XBTZAR", null, null));
        
        //System.out.println(luno.feeInfo(auth, "XBTZAR"));
        
        //System.out.println(luno.getFundingAddress(auth, "XBT", "mneV5LBjUw22c2mqAGTUkBHzb3zQjEVCZL"));
        
        //System.out.println(luno.createFundingAddress(auth, "XBT"));
        
        //System.out.println(luno.withdrawals(auth));
        
        //System.out.println(luno.requestWithdrawal(auth, "ZAR_EFT", new BigDecimal("10"), null));        // 206
        //System.out.println(luno.getWithdrawal(auth, "206"));
        //System.out.println(luno.cancelWithdrawal(auth, "206"));
        
        //System.out.println(luno.send(auth, new BigDecimal("0.001"), "XBT", "mrccg4YngYk4hbeGR4z6dtU955ZNT9xfxJ", "Just a test sending to a btc address", "another message"));
        
        //System.out.println(luno.createQuote(auth, "BUY", new BigDecimal("0.001"), "XBTZAR"));   // 1794, 1795
        //System.out.println(luno.discardQuote(auth, "1795"));
        //System.out.println(luno.exerciseQuote(auth, "1795"));
        //System.out.println(luno.getQuote(auth, "1795"));
    }
}
