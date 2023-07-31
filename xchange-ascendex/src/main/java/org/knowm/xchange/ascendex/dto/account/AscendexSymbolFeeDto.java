package org.knowm.xchange.ascendex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 11:10
 * @author:wodepig
 */
@Data
public class AscendexSymbolFeeDto {
    private String	domain;
    private String	userUID;
    private Integer	vipLevel;
    @JsonProperty("fees")
    private List<ProductFee> productFee;

    @Data
    public static class  ProductFee{

        private Fee	fee;
        private String	symbol;

    }

    @Data
    public static class Fee{

        private BigDecimal maker;
        private BigDecimal	taker;

    }
}
