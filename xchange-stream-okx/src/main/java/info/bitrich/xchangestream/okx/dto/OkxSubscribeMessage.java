package info.bitrich.xchangestream.okx.dto;

import info.bitrich.xchangestream.okx.dto.enums.OkxInstType;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OkxSubscribeMessage {
    private String op;
    List<SubscriptionTopic> args = new LinkedList<>();

    @Data
    @AllArgsConstructor
    public static class SubscriptionTopic {
        private String channel;

        private OkxInstType instType;

        private String uly;

        private String instId;
    }
}
