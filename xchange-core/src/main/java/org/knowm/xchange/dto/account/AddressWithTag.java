package org.knowm.xchange.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressWithTag {

  String address;

  String addressTag;

}
