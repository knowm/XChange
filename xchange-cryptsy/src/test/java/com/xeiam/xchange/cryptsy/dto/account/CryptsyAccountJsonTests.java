/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.cryptsy.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyTransfers.CryptsyTrfDirection;
import com.xeiam.xchange.cryptsy.dto.account.CryptsyTxn.CryptsyTxnType;

public class CryptsyAccountJsonTests {

  @Test
  public void testDeserializeCryptsyAccountInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAccountJsonTests.class.getResourceAsStream("/account/Sample_GetInfo_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyAccountInfo accountInfo = mapper.readValue(is, CryptsyAccountInfoReturn.class).getReturnValue();

    assertEquals(accountInfo.getAvailableFunds().keySet().size(), 150);
    assertEquals(accountInfo.getAvailableFunds().get("BTC"), new BigDecimal("0.01527794"));
    assertEquals(accountInfo.getAvailableFunds().get("ZET"), new BigDecimal("1.35094992"));
    assertEquals(accountInfo.getAvailableFunds().get("PXC"), new BigDecimal("0.00002522"));
    assertEquals(accountInfo.getOpenOrders(), 5);
  }

  @Test
  public void testDeserializeCryptsyTransactions() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAccountJsonTests.class.getResourceAsStream("/account/Sample_MyTransactions_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyTxn> cryptsyTxns = mapper.readValue(is, CryptsyTxnHistoryReturn.class).getReturnValue();

    CryptsyTxn transaction = cryptsyTxns.get(0);
    assertEquals(transaction.getType(), CryptsyTxnType.Deposit);
    assertEquals(transaction.getAddress(), "Wkcng1BjyAcbvFV9AyWAaZmdiRoUgojUg9");
    assertEquals(transaction.getTransactionId(), "bb59a582c72c63ba111fd87ad44cca694836158ec751360e64f89df723fa07dc");
    assertEquals(transaction.getAmount(), new BigDecimal("132.25344843"));
    assertEquals(transaction.getCurrency(), "WDC");
  }

  @Test
  public void testDeserializeNewCryptsyDepositAddress() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAccountJsonTests.class.getResourceAsStream("/account/Sample_GenerateNewAddress_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyAddress cryptsyAddress = mapper.readValue(is, CryptsyNewAddressReturn.class).getReturnValue();

    assertEquals(cryptsyAddress.getAddress(), "1FUDFR6Tm28e9CHmCTLGkFZSwPazwXKqPm");
  }

  @Test
  public void testDeserializeCurrentCryptsyDepositAddresses() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAccountJsonTests.class.getResourceAsStream("/account/Sample_GetMyDepositAddresses_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> cryptsyAddressMap = mapper.readValue(is, CryptsyDepositAddressReturn.class).getReturnValue();

    assertEquals(cryptsyAddressMap.get("BTC"), "1FUDFR6Tm28e9CHmCTLGkFZSwPazwXKqPm");
    assertEquals(cryptsyAddressMap.get("LTC"), "LgngXeqNJjJYCWG8rcTZwuMwy7qQoCZt2p");
    assertEquals(cryptsyAddressMap.get("WDC"), "Wkcng1BjyAcbvFV9AyWAaZmdiRoUgojUg9");
  }

  @Test
  public void testDeserializeTransferHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyAccountJsonTests.class.getResourceAsStream("/account/Sample_MyTransfers_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyTransfers> cryptsyTransfers = mapper.readValue(is, CryptsyTransfersReturn.class).getReturnValue();

    CryptsyTransfers transfer = cryptsyTransfers.get(0);
    assertEquals(transfer.getCurrency(), "NET");
    assertEquals(transfer.getOriginator(), "A00010101");
    assertEquals(transfer.getProcessedBoolean(), false);
    assertEquals(transfer.getQuantity(), new BigDecimal("0.00022557"));
    assertEquals(transfer.getRecipient(), "B00191301");
    assertEquals(transfer.getTransferDirection(), CryptsyTrfDirection.out);
  }

  @Test
  public void testDeserializeCryptsyWithdrawal() throws IOException {

    // No withdrawal JSON data
  }

  @Test
  public void testDeserializeWalletStatus() throws IOException {

    // Stub test method for stub method
  }
}
