package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.marketdata
 * Description:
 *
 * @date:2022/7/14 18:07
 * @author:wodepig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexTickerDto {
    private   String	symbol;
    private   BigDecimal	open;
    private   BigDecimal	close;
    private   BigDecimal	high;
    private   BigDecimal	low;
    private   BigDecimal	volume;
    private   List<BigDecimal> ask;
    private   List<BigDecimal>	bid;

}
