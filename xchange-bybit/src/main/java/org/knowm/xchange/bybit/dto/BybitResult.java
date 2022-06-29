package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BybitResult<V> {

    private final int retCode;
    private final String retMsg;
    private final String extCode;
    private final String extInfo;
    private final V result;

    @JsonCreator
    public BybitResult(@JsonProperty("ret_code") int retCode,
                       @JsonProperty("ret_msg") String retMsg,
                       @JsonProperty("ext_code") String extCode,
                       @JsonProperty("ext_info") String extInfo,
                       @JsonProperty("result") V result) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.extCode = extCode;
        this.extInfo = extInfo;
        this.result = result;
    }

    public V getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "BybitResult{" +
                "retCode=" + retCode +
                ", retMsg='" + retMsg + '\'' +
                ", extCode='" + extCode + '\'' +
                ", extInfo='" + extInfo + '\'' +
                ", result=" + result +
                '}';
    }
}
