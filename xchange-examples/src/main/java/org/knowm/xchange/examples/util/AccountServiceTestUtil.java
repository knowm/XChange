package org.knowm.xchange.examples.util;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.dto.account.FundingRecord;

/**
 * Created by joseph on 3/20/17.
 */
public class AccountServiceTestUtil {
  public static void printFundingHistory(List<FundingRecord> fundingRecords) throws IOException {

    if (fundingRecords != null) {
      for (final FundingRecord fundingRecord : fundingRecords) {
        System.out.println(fundingRecord);
      }
    } else {
      System.out.println("No Funding History Found.");
    }
  }
}
