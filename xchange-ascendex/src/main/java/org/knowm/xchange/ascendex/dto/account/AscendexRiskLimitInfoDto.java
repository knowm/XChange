package org.knowm.xchange.ascendex.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/16 12:45
 * @author:wodepig
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AscendexRiskLimitInfoDto {
    private String ip;
    private WebSocket webSocket;

    @Data
    public static class Level2ReqThreshold {

        private Integer place_order;
        private Integer cancel_order;
        private Integer cancel_all;
        private Integer batch_place_order;
        private Integer batch_cancel_order;
        private Integer depth_snapshot;
        private Integer depth_snapshot_top100;
        private Integer market_trades;
        private Integer balance;
        private Integer open_order;
        private Integer margin_risk;
        private Integer futures_account_snapshot;
        private Integer futures_open_orders;

    }

    @Data
    public static class MessageThreshold {

        private Level1OpThreshold level1OpThreshold;
        private Level2OpThreshold level2OpThreshold;
        private Level1ReqThreshold level1ReqThreshold;
        private Level2ReqThreshold level2ReqThreshold;

    }

    @Data
    public static class WebSocket {

        private Status status;
        private Limits limits;
        private MessageThreshold messageThreshold;

    }


    @Data
    public static class Level1ReqThreshold {

        private Integer place_order;
        private Integer cancel_order;
        private Integer cancel_all;
        private Integer batch_place_order;
        private Integer batch_cancel_order;
        private Integer depth_snapshot;
        private Integer depth_snapshot_top100;
        private Integer market_trades;
        private Integer balance;
        private Integer open_order;
        private Integer margin_risk;
        private Integer futures_account_snapshot;
        private Integer futures_open_orders;

    }


    @Data
    public static class Level2OpThreshold {

        private Integer auth;
        private Integer ping;
        private Integer pong;
        private Integer sub;
        private Integer unsub;
        private Integer req;

    }


    @Data
    public static class Level1OpThreshold {

        private Integer auth;
        private Integer ping;
        private Integer pong;
        private Integer sub;
        private Integer unsub;
        private Integer req;

    }

    @Data
    public static class Limits {

        private Integer maxWebSocketSessionsPerIpAccountGroup;
        private Integer maxWebSocketSessionsPerIpTotal;

    }

    @Data
    public static class Status {

        private Boolean isBanned;
        private Integer bannedUntil;
        private Integer violationCode;
        private String reason;

    }
}
