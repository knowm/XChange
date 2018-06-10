package org.knowm.xchange.cexio.dto.trade;

/*
{
   "a:BTC:cds": "0.10552510",
  "a:EUR:cds": "1001.43",
  "amount": "0.15727460",
  "f:EUR:cds": "0.54",
  "fa:EUR": "0.54",
  "id": "6297534214",
  "kind": "api",
  "lastTx": "6297541591",
  "lastTxTime": "2018-05-25T09:21:28.454Z",
  "orderId": "6297534214",
  "pos": null,
  "price": "6357.8",
  "remains": "0.05174950",
  "status": "cd",
  "symbol1": "BTC",
  "symbol2": "EUR",
  "ta:EUR": "670.90",
  "time": 1527239994980,
  "tradingFeeMaker": "0.08",
  "tradingFeeStrategy": "userVolumeAmount",
  "tradingFeeTaker": "0.15",
  "tradingFeeUserVolumeAmount": "22401319685",
  "type": "buy",
  "user": "----"
}
*/

public class CexIOFullOrder extends CexIOOpenOrder {

  /** ta:{symbol2} string total amount in current currency (Maker) */
  public final String totalAmountMaker;
  /** tta:{symbol2} string total amount in current currency (Taker) */
  public final String totalAmountTaker;
  /** fa:{symbol2} string fee amount in current currency (Maker) */
  public final String feeMaker;

  /** tfa:{symbol2} string fee amount in current currency (Taker) */
  public final String feeTaker;

  public CexIOFullOrder(
      String user,
      String type,
      String symbol1,
      String symbol2,
      String amount,
      String remains,
      String price,
      long time,
      String lastTxTime,
      String tradingFeeStrategy,
      String tradingFeeTaker,
      String tradingFeeMaker,
      String tradingFeeUserVolumeAmount,
      String lastTx,
      String status,
      String orderId,
      String id,
      String totalAmountMaker,
      String totalAmountTaker,
      String feeMaker,
      String feeTaker) {
    super(
        user,
        type,
        symbol1,
        symbol2,
        amount,
        remains,
        price,
        time,
        lastTxTime,
        tradingFeeStrategy,
        tradingFeeTaker,
        tradingFeeMaker,
        tradingFeeUserVolumeAmount,
        lastTx,
        status,
        orderId,
        id);
    this.totalAmountMaker = totalAmountMaker;
    this.totalAmountTaker = totalAmountTaker;
    this.feeMaker = feeMaker;
    this.feeTaker = feeTaker;
  }
}
