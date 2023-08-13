package org.knowm.xchange.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class AddressWithTag {

  String address;

  String addressTag;

}
