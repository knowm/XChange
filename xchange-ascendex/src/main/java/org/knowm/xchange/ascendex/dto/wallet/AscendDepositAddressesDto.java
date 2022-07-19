package org.knowm.xchange.ascendex.dto.wallet;

import lombok.Data;

import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.wallet
 * Description:
 *
 * @date:2022/7/18 11:54
 * @author:wodepig
 */
@Data
public class AscendDepositAddressesDto {
    private String	asset;
    private String	assetName;
    private List<Address> address;
    @Data
    public static class Address{

        private String	address;
        private String	blockchain;
        private String	destTag;

    }

}
