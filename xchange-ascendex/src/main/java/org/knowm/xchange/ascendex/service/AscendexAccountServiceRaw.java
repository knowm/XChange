package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.dto.enums.AccountCategory;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.AscendexResponse;
import org.knowm.xchange.ascendex.dto.account.*;
import org.knowm.xchange.ascendex.dto.balance.*;
import org.knowm.xchange.ascendex.dto.enums.AscendexTransactionType;
import org.knowm.xchange.ascendex.dto.wallet.AscendDepositAddressesDto;
import org.knowm.xchange.ascendex.dto.wallet.AscendexWalletTransactionHistoryDto;

public class AscendexAccountServiceRaw extends AscendexBaseService {

  public AscendexAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public AscendexAccountInfoDto getAscendexAccountInfo() throws AscendexException, IOException {
    return checkResult(
            ascendexAuthenticated.getAscendexAccountInfo(
                    exchange.getExchangeSpecification().getApiKey(),
                    exchange.getNonceFactory().createValue(),
                    signatureCreator
            )
    );

  }

    public AscendexVIPFeeScheduleDto getAscendexVIPFeeSchedule() throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.getAscendexVIPFeeSchedule(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator
                )
        );

    }

    public AscendexSymbolFeeDto getAscendexSymbolFee() throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.getAscendexSymbolFeeSchedule(
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator
                )
        );

    }

    public AscendexRiskLimitInfoDto getAscendexRiskLimitInfo()throws AscendexException, IOException {
      return checkResult(
              ascendexAuthenticated.getAscendexRiskLimitInfo()
      );
    }

    public AscendexExchangeLatencyInfoDto getAscendexExchangeLatencyInfo(Long requestTime)throws AscendexException,IOException{
      return checkResult(
              ascendexAuthenticated.getAscendexExchangeLatencyInfo(requestTime)
      );
    }

    public List<AscendexCashAccountBalanceDto> getAscendexCashAccountBalance()throws AscendexException, IOException {
      return getAscendexCashAccountBalance(null,false);
    }
  public List<AscendexCashAccountBalanceDto> getAscendexCashAccountBalance(
         String asset,
          Boolean showAll
  )
      throws AscendexException, IOException {
    return checkResult(
        ascendexAuthenticated.getAscendexCashAccountBalance(
                asset==null?null:asset.toUpperCase(),
            showAll,
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator));
  }
    public List<AscendexMarginAccountBalanceDto> getAscendexMarginAccountBalance() throws AscendexException, IOException{
      return getAscendexMarginAccountBalance(null,false);
    }

    public List<AscendexMarginAccountBalanceDto> getAscendexMarginAccountBalance(
            String asset,
            Boolean showAll
    )
            throws AscendexException, IOException {
        return checkResult(
                ascendexAuthenticated.getAscendexMarginAccountBalance(
                        asset==null?null:asset.toUpperCase(),
                        showAll,
                        exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator));
    }
    public AscendexMarginRiskDto getAscendexMarginRisk() throws AscendexException, IOException {
      return checkResult(
              ascendexAuthenticated.getAscendexMarginRisk(
                      exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                      exchange.getExchangeSpecification().getApiKey(),
                      exchange.getNonceFactory().createValue(),
                      signatureCreator
              )
      );
    }


    public Boolean getAscendexBalanceTransfer( AscendexBalanceTransferRequestPayload payload) throws AscendexException, IOException {
        AscendexResponse<Void> ascendexBalanceTransfer = ascendexAuthenticated.getAscendexBalanceTransfer(
                payload,
                exchange.getExchangeSpecification().getExchangeSpecificParametersItem("account-group").toString(),
                exchange.getExchangeSpecification().getApiKey(),
                exchange.getNonceFactory().createValue(),
                signatureCreator
        );
        if (ascendexBalanceTransfer.getCode().equals(0)){
            return true;
        }

        return false;
    }


    public AscendexBalanceSnapshotDto getAscendexBalanceSnapshot(String date,AccountCategory accountCategory)throws AscendexException, IOException{
   /*     int days = (int) ((new Date().getTime() - new Date(date).getTime()) / (1000*3600*24));
        if (days>7){
            throw new AscendexException(0,"Data query for most recent 7 days is supported");
        }*/
        AscendexBalanceSnapshotDto BalanceSnapshot = ascendexAuthenticated.getAscendexBalanceSnapshot(
                date,
                accountCategory,
                exchange.getExchangeSpecification().getApiKey(),
                exchange.getNonceFactory().createValue(),
                signatureCreator
        );
  return BalanceSnapshot;
    }

    public AscendexOrderAndBalanceDetailDto getAscendexOrderAndBalanceDetail(String date, AccountCategory accountCategory)throws AscendexException, IOException{

        AscendexOrderAndBalanceDetailDto Detail = ascendexAuthenticated.getAscendexOrderAndBalanceDetail(
                date,
                accountCategory,
                exchange.getExchangeSpecification().getApiKey(),
                exchange.getNonceFactory().createValue(),
                signatureCreator
        );
        return Detail;
    }

    public AscendDepositAddressesDto getAscendDepositAddresses( String asset)throws AscendexException, IOException{
        return getAscendDepositAddresses(asset,null);

    }
    public AscendDepositAddressesDto getAscendDepositAddresses( String asset, String blockchain)throws AscendexException, IOException{
        return checkResult(
                ascendexAuthenticated.getAscendexDepositAddresses(
                        asset.toUpperCase(),blockchain==null?null:blockchain.toUpperCase(),
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator
                )
        );

    }

    /**
     * We do support API withdrawals. Please contact your dedicated sales manager or support@ascendex.com for further information.
     */
    public void getAscendWithdrawal(){

    }
    public AscendexWalletTransactionHistoryDto getAscendexWalletTransactionHistory()throws AscendexException, IOException{
        return getAscendexWalletTransactionHistory(null,null,null,null);
    }
    public AscendexWalletTransactionHistoryDto getAscendexWalletTransactionHistory(String  asset,
                                                                                   AscendexTransactionType txType,
                                                                                   Integer page,Integer pageSize)throws AscendexException, IOException{
        return checkResult(
                ascendexAuthenticated.getAscendexWalletTransactionHistory(
                        asset==null?null:asset.toUpperCase(),
                        txType,
                        page,
                        pageSize,
                        exchange.getExchangeSpecification().getApiKey(),
                        exchange.getNonceFactory().createValue(),
                        signatureCreator
                )
        );

    }
}
