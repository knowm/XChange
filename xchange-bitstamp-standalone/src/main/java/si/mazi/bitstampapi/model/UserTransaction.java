package si.mazi.bitstampapi.model;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 7:33 PM
 */
public class UserTransaction {
    private String datetime;
    private long id;
    private int type;
    private double usd;
    private double btc;
    private double fee;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /** (0 - deposit; 1 - withdrawal; 2 - market trade) */
    public int getType() {
        return type;
    }

    public boolean isDeposit() {
        return type == 0;
    }

    public boolean isWithdrawal() {
        return type == 1;
    }

    public boolean isMarketTrade() {
        return type == 2;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }

    public double getBtc() {
        return btc;
    }

    public void setBtc(double btc) {
        this.btc = btc;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return String.format("UserTransaction{datetime=%s, id=%d, type=%d, usd=%s, btc=%s, fee=%s}",
            datetime, id, type, usd, btc, fee);
    }
}
