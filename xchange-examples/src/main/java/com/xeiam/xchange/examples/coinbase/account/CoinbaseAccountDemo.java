package com.xeiam.xchange.examples.coinbase.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddress;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddresses;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseContacts;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransactions;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseAccountServiceRaw;
import com.xeiam.xchange.examples.coinbase.CoinbaseDemoUtils;

public class CoinbaseAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    CoinbaseAccountServiceRaw accountService = (CoinbaseAccountServiceRaw) coinbase.getPollingAccountService();

//    CoinbaseAmount balance = accountService.getCoinbaseBalance();
//    System.out.println(balance);
    
//    CoinbaseAddress receiveAddress = accountService.getCoinbaseReceiveAddress();
//    System.out.println(receiveAddress);
    
//    CoinbaseAddress generatedReceiveAddress = accountService.generateReceiveAddress("http://www.example.com/callback", "test");
//    System.out.println(generatedReceiveAddress);
    
//    CoinbaseAccountChanges accountChanges = accountService.getCoinbaseAccountChanges();
//    System.out.println(accountChanges);
    
//    CoinbaseAddresses addresses = accountService.getCoinbaseAddresses();
//    System.out.println(addresses);
    
//    CoinbaseContacts contacts = accountService.getCoinbaseContacts();
//    System.out.println(contacts);
    
//    CoinbaseTransfers transfers = accountService.getCoinbaseTransfers(); 
//    System.out.println(transfers);
    
    CoinbaseTransactions transactions = accountService.getCoinbaseTransactions();
    System.out.println(transactions);
  }
}
