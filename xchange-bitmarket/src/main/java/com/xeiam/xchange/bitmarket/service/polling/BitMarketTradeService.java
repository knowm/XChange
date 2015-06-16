package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketAdapters;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrderResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author yarkh
 */
public class BitMarketTradeService extends BitMarketTradeServiceRaw implements PollingTradeService {

    public BitMarketTradeService(Exchange exchange) {
        super(exchange);
    }


    @Override
    public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        BitMarketBaseResponse<Map<String, BitMarketOrdersResponse>> response = getBitMarketOrders(null);

        if (isSuccess(response)) {
            return new OpenOrders(BitMarketAdapters.adaptOrders(response.getData()));
        } else if (isError(response)) {
            throw new ExchangeException(response.getErrorMsg());
        }
        return null;
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        BitMarketBaseResponse<BitMarketOrderResponse> response = sendBitMarketOrder(limitOrder);
        if (isSuccess(response)) {
            BitMarketOrderResponse orderResponse = response.getData();
            if (orderResponse.getId() != null) {
                return orderResponse.getId();
            } else {
                return "0";
            }
        } else if (isError(response)) {
            throw new ExchangeException(response.getErrorMsg());
        }
        return null;
    }

    @Override
    public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        BitMarketBaseResponse<BitMarketOrderResponse> response = cancelBitMarketOrder(orderId);
        if (isSuccess(response)) {
            return true;
        } else if (isError(response)) {
            throw new ExchangeException(response.getErrorMsg());
        }
        return false;
    }

    @Override
    public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        return null;
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return null;
    }
}
