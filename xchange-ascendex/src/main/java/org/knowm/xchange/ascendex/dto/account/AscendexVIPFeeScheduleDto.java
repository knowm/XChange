package org.knowm.xchange.ascendex.dto.account;


import lombok.Data;


/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 11:00
 * @author:wodepig
 */
@Data
public class AscendexVIPFeeScheduleDto {
    private String	domain;
    private String	userUID;
    private Integer	vipLevel;
    private GenericFee	genericFee;

    @Data
    public static class GenericFee{

        private LargeCap	largeCap;
        private SmallCap	smallCap;

    }

    @Data
    public static class SmallCap{

        private String	maker;
        private String	taker;

    }

    @Data
    public static class LargeCap{

        private String	maker;
        private String	taker;

    }
}
