package org.knowm.xchange.ascendex.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 12:54
 * @author:wodepig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexExchangeLatencyInfoDto {
    private Long	requestTimeEcho;
    private Long	requestReceiveAt;
    private Integer	latency;
}
