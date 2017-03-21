package org.knowm.xchange.examples.util;

import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.dto.account.FundsRecord;

import java.io.IOException;

/**
 * Created by joseph on 3/20/17.
 */
public class AccountServiceTestUtil {
    public static void printFundsInfo(FundsInfo fundsInfo) throws IOException {

        if (fundsInfo != null && fundsInfo.getFundsRecordList()!=null){
            for (final FundsRecord fundsRecord : fundsInfo.getFundsRecordList()){
                System.out.println(fundsRecord);
            }
        } else {
            System.out.println("No Funding History Found.");
        }
    }
}
