package org.knowm.xchange.coinsetter.dto.financialtransaction;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.Test;

import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterFinancialTransactionTest {

  @Test
  public void test() throws IOException {

    CoinsetterFinancialTransaction financialTransaction = ObjectMapperHelper.readValue(getClass().getResource("financialTransaction.json"),
        CoinsetterFinancialTransaction.class);
    assertEquals(UUID.fromString("ef178baa-46f0-441d-a97d-c92a848f4f29"), financialTransaction.getUuid());
    assertEquals(UUID.fromString("5ba865b8-cd46-4da5-a99a-fdf7bcbc37b3"), financialTransaction.getCustomerUuid());
    assertEquals(UUID.fromString("3b1a82ce-e632-4281-b1bd-eb9bf3aeb60c"), financialTransaction.getAccountUuid());
    assertEquals(new BigDecimal("1000.00000000"), financialTransaction.getAmount());
    assertEquals("USD", financialTransaction.getAmountDenomination());
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
    format.setTimeZone(TimeZone.getTimeZone("EST"));
    assertEquals("12/05/2013 11:03:31.000", format.format(financialTransaction.getCreateDate()));
    assertEquals("Unit test", financialTransaction.getReferenceNumber());
    assertEquals("Deposit description", financialTransaction.getTransactionCategoryDescription());
    assertEquals("Deposit", financialTransaction.getTransactionCategoryName());
  }

}
