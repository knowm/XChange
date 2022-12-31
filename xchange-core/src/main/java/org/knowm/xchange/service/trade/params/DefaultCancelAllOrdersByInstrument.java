package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.instrument.Instrument;

public class DefaultCancelAllOrdersByInstrument implements CancelOrderByInstrument, CancelAllOrders{

    private final Instrument instrument;

    public DefaultCancelAllOrdersByInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }
}
