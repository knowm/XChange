package org.knowm.xchange.dto.account;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class InternalFundingRecord extends FundingRecord {

  private final String fromAccount;

  private final String toAccount;

  private final String fromSubAccount;

  private final String toSubAccount;
}
