package org.knowm.xchange.getbtc.dto.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetbtcAccountInformation {

    @JsonProperty("balances-and-info")
    private BalancesAndInfo balancesAndInfo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetbtcAccountInformation() {
    }

    /**
     * 
     * @param balancesAndInfo
     */
    public GetbtcAccountInformation(BalancesAndInfo balancesAndInfo) {
        super();
        this.balancesAndInfo = balancesAndInfo;
    }

    @JsonProperty("balances-and-info")
    public BalancesAndInfo getBalancesAndInfo() {
        return balancesAndInfo;
    }

    @JsonProperty("balances-and-info")
    public void setBalancesAndInfo(BalancesAndInfo balancesAndInfo) {
        this.balancesAndInfo = balancesAndInfo;
    }

 
    //------------------------------------
    public static class BalancesAndInfo {

        @JsonProperty("on_hold")
        private List<Object> onHold = null;
        
        @JsonProperty("available")
        private Map<String, BigDecimal> available;
        
        @JsonProperty("usd_volume")
        private long usdVolume;
        @JsonProperty("fee_bracket")
        private FeeBracket feeBracket;
        @JsonProperty("global_btc_volume")
        private String globalBtcVolume;

        /**
         * No args constructor for use in serialization
         * 
         */
        public BalancesAndInfo() {
        }

        /**
         * 
         * @param usdVolume
         * @param feeBracket
         * @param globalBtcVolume
         * @param available
         * @param onHold
         */
        public BalancesAndInfo(List<Object> onHold, Map<String, BigDecimal> available, long usdVolume, FeeBracket feeBracket, String globalBtcVolume) {
            super();
            this.onHold = onHold;
            this.available = available;
            this.usdVolume = usdVolume;
            this.feeBracket = feeBracket;
            this.globalBtcVolume = globalBtcVolume;
        }

        @JsonProperty("on_hold")
        public List<Object> getOnHold() {
            return onHold;
        }

        @JsonProperty("on_hold")
        public void setOnHold(List<Object> onHold) {
            this.onHold = onHold;
        }

        @JsonProperty("available")
        public Map<String, BigDecimal> getAvailable() {
            return available;
        }

        @JsonProperty("available")
        public void setAvailable(Map<String, BigDecimal> available) {
            this.available = available;
        }

        @JsonProperty("usd_volume")
        public long getUsdVolume() {
            return usdVolume;
        }

        @JsonProperty("usd_volume")
        public void setUsdVolume(long usdVolume) {
            this.usdVolume = usdVolume;
        }

        @JsonProperty("fee_bracket")
        public FeeBracket getFeeBracket() {
            return feeBracket;
        }

        @JsonProperty("fee_bracket")
        public void setFeeBracket(FeeBracket feeBracket) {
            this.feeBracket = feeBracket;
        }

        @JsonProperty("global_btc_volume")
        public String getGlobalBtcVolume() {
            return globalBtcVolume;
        }

        @JsonProperty("global_btc_volume")
        public void setGlobalBtcVolume(String globalBtcVolume) {
            this.globalBtcVolume = globalBtcVolume;
        }
        //----------------------------------------------
//        public class Available {
//
//            @JsonProperty("BTC")
//            private double bTC;
//            @JsonProperty("ETH")
//            private double eTH;
//
//            /**
//             * No args constructor for use in serialization
//             * 
//             */
//            public Available() {
//            }
//
//            /**
//             * 
//             * @param eTH
//             * @param bTC
//             */
//            public Available(double bTC, double eTH) {
//                super();
//                this.bTC = bTC;
//                this.eTH = eTH;
//            }
//
//            @JsonProperty("BTC")
//            public double getBTC() {
//                return bTC;
//            }
//
//            @JsonProperty("BTC")
//            public void setBTC(double bTC) {
//                this.bTC = bTC;
//            }
//
//            @JsonProperty("ETH")
//            public double getETH() {
//                return eTH;
//            }
//
//            @JsonProperty("ETH")
//            public void setETH(double eTH) {
//                this.eTH = eTH;
//            }         
//        }
        //-------------------------
        public class FeeBracket {

            @JsonProperty("maker")
            private String maker;
            @JsonProperty("taker")
            private String taker;

            /**
             * No args constructor for use in serialization
             * 
             */
            public FeeBracket() {
            }

            /**
             * 
             * @param taker
             * @param maker
             */
            public FeeBracket(String maker, String taker) {
                super();
                this.maker = maker;
                this.taker = taker;
            }

            @JsonProperty("maker")
            public String getMaker() {
                return maker;
            }

            @JsonProperty("maker")
            public void setMaker(String maker) {
                this.maker = maker;
            }

            @JsonProperty("taker")
            public String getTaker() {
                return taker;
            }

            @JsonProperty("taker")
            public void setTaker(String taker) {
                this.taker = taker;
            }

        }        
    }
}