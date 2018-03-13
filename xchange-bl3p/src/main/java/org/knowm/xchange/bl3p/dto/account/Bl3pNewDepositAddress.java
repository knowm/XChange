package org.knowm.xchange.bl3p.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bl3p.dto.Bl3pResult;

public class Bl3pNewDepositAddress extends Bl3pResult<Bl3pNewDepositAddress.Bl3pNewDepositAddressData> {

    public static class Bl3pNewDepositAddressData {

        @JsonProperty("address")
        private String address;

        public String getAddress() {
            return address;
        }
    }
}
