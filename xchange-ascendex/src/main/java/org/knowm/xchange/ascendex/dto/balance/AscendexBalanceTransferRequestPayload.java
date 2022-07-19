package org.knowm.xchange.ascendex.dto.balance;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 20:20
 * @author:wodepig
 */
@Data
@AllArgsConstructor
public class AscendexBalanceTransferRequestPayload {

    String amount;
    String asset;
    AccountCategory fromAccount;
    AccountCategory toAccount;

    public void setAsset(String asset) {
        this.asset =asset==null?null: asset.toUpperCase();
    }
}
