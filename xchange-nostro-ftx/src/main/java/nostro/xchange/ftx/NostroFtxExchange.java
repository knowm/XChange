package nostro.xchange.ftx;

import info.bitrich.xchangestream.ftx.FtxStreamingExchange;
import nostro.xchange.persistence.TransactionFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ftx.service.FtxAccountService;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NostroFtxExchange extends FtxStreamingExchange {
    private static final Logger LOG = LoggerFactory.getLogger(NostroFtxExchange.class);
    
    private volatile TransactionFactory txFactory;
    private volatile NostroFtxAccountService nostroAccountService;

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {
        ExchangeSpecification spec = super.getDefaultExchangeSpecification();
        spec.setExchangeName("NostroFtx");
        return spec;
    }

    @Override
    public String getMetaDataFileName(ExchangeSpecification exchangeSpecification) {
        return "ftx";
    }

    @Override
    public AccountService getAccountService() {
        return nostroAccountService;
    }

    @Override
    protected void initServices() {
        super.initServices();
        
        if (isAuthenticated()) {
            try {
                this.txFactory = TransactionFactory.get("Ftx", exchangeSpecification.getUserName());
                this.nostroAccountService = new NostroFtxAccountService((FtxAccountService) this.accountService, txFactory, this);
            } catch (Exception e) {
                throw new ExchangeException("Unable to init", e);
            }
        }
    }

    private boolean isAuthenticated() {
        return exchangeSpecification != null
                && exchangeSpecification.getApiKey() != null
                && exchangeSpecification.getSecretKey() != null;
    }
}
