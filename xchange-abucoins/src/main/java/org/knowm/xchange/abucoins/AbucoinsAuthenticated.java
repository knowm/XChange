package org.knowm.xchange.abucoins;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;

import si.mazi.rescu.ParamsDigest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AbucoinsAuthenticated extends Abucoins {
  @GET
  @Path("accounts")
  AbucoinsAccount[] getAccounts(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
  
  @GET
  @Path("accounts/{account-id}")
  AbucoinsAccount getAccount(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp, @PathParam("account-id") String accountID) throws IOException;
  
  @GET
  @Path("payment-methods")
  AbucoinsPaymentMethod[] getPaymentMethods(@HeaderParam("AC-ACCESS-KEY") String accessKey, @HeaderParam("AC-ACCESS-SIGN") ParamsDigest sign, @HeaderParam("AC-ACCESS-PASSPHRASE") String passphrase, @HeaderParam("AC-ACCESS-TIMESTAMP") String timestamp) throws IOException;
}
