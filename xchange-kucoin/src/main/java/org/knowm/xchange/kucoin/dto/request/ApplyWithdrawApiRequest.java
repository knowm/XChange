package org.knowm.xchange.kucoin.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(value = Include.NON_EMPTY, content = Include.NON_NULL)
public class ApplyWithdrawApiRequest {

  /** Currency */
  private final String currency;
  /** Withdrawal address */
  private final String address;
  /**
   * Withdrawal amount, a positive number which is a multiple of the amount precision (fees
   * excluded)
   */
  private final BigDecimal amount;
  /**
   * [optional] The note that is left on the withdrawal address. When you withdraw from KuCoin to
   * other platforms, you need to fill in memo(tag). If you don't fill in memo(tag), your withdrawal
   * may not be available.
   */
  private final String memo;
  /** [optional] Internal withdrawal or not. Default setup: false */
  @Builder.Default private final boolean isInner = false;
  /** [optional] Remark */
  private final String remark;
  /**
   * [optional] The chain name of currency, e.g. The available value for USDT are OMNI, ERC20,
   * TRC20, default is OMNI. This only apply for multi-chain currency, and there is no need for
   * single chain currency.
   */
  private final String chain;
}
