package org.knowm.xchange.cexio.dto;

public class CexioSingleOrderIdRequest extends CexIORequest {
    public final String orderId;

    public CexioSingleOrderIdRequest(String orderId) {
        this.orderId = orderId;
    }
}
