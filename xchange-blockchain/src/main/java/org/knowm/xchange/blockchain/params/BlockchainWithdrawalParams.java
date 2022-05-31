package org.knowm.xchange.blockchain.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainWithdrawalParams implements WithdrawFundsParams {

    /**
     * UUID that represent a whitelisted address
     * to get the UUIDs of the whitelisted addresses, call this endpoint
     * @see <a href="https://api.blockchain.info/v3/#/payments/getWhitelist">https://api.blockchain.info/v3/#/payments/getWhitelist</a>
     */
    public final String beneficiary;
    public final Currency currency;
    public final BigDecimal amount;
    /**
     * if this is set to true, the amount will be ignored and a withdrawal of all funds will be made
     */
    public final Boolean sendMax;
}
