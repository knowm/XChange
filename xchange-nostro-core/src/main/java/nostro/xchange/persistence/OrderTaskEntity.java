package nostro.xchange.persistence;

public class OrderTaskEntity {
    private String orderId;
    private String type;
    private boolean finished;
    private String document;

    public OrderTaskEntity(String orderId, String type, boolean finished, String document) {
        this.orderId = orderId;
        this.type = type;
        this.finished = finished;
        this.document = document;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
