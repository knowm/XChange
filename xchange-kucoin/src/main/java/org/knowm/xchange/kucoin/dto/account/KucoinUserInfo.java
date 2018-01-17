
package org.knowm.xchange.kucoin.dto.account;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "referrer_code",
    "photoCredentialValidated",
    "videoValidated",
    "language",
    "currency",
    "oid",
    "baseFeeRate",
    "hasCredential",
    "credentialNumber",
    "phoneValidated",
    "phone",
    "credentialValidated",
    "googleTwoFaBinding",
    "nickname",
    "name",
    "hasTradePassword",
    "emailValidated",
    "email",
    "loginRecord"
})
public class KucoinUserInfo {

    @JsonProperty("referrer_code")
    private String referrerCode;
    @JsonProperty("photoCredentialValidated")
    private Boolean photoCredentialValidated;
    @JsonProperty("videoValidated")
    private Boolean videoValidated;
    @JsonProperty("language")
    private String language;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("oid")
    private String oid;
    @JsonProperty("baseFeeRate")
    private Integer baseFeeRate;
    @JsonProperty("hasCredential")
    private Boolean hasCredential;
    @JsonProperty("credentialNumber")
    private String credentialNumber;
    @JsonProperty("phoneValidated")
    private Boolean phoneValidated;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("credentialValidated")
    private Boolean credentialValidated;
    @JsonProperty("googleTwoFaBinding")
    private Boolean googleTwoFaBinding;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("name")
    private String name;
    @JsonProperty("hasTradePassword")
    private Boolean hasTradePassword;
    @JsonProperty("emailValidated")
    private Boolean emailValidated;
    @JsonProperty("email")
    private String email;
    @JsonProperty("loginRecord")
    private KucoinLoginRecord loginRecord;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinUserInfo() {
    }

    /**
     * 
     * @param photoCredentialValidated
     * @param videoValidated
     * @param referrerCode
     * @param language
     * @param loginRecord
     * @param oid
     * @param baseFeeRate
     * @param hasCredential
     * @param credentialNumber
     * @param phoneValidated
     * @param phone
     * @param credentialValidated
     * @param googleTwoFaBinding
     * @param nickname
     * @param name
     * @param hasTradePassword
     * @param currency
     * @param emailValidated
     * @param email
     */
    public KucoinUserInfo(String referrerCode, Boolean photoCredentialValidated, Boolean videoValidated, String language, String currency, String oid, Integer baseFeeRate, Boolean hasCredential, String credentialNumber, Boolean phoneValidated, String phone, Boolean credentialValidated, Boolean googleTwoFaBinding, String nickname, String name, Boolean hasTradePassword, Boolean emailValidated, String email, KucoinLoginRecord loginRecord) {
        super();
        this.referrerCode = referrerCode;
        this.photoCredentialValidated = photoCredentialValidated;
        this.videoValidated = videoValidated;
        this.language = language;
        this.currency = currency;
        this.oid = oid;
        this.baseFeeRate = baseFeeRate;
        this.hasCredential = hasCredential;
        this.credentialNumber = credentialNumber;
        this.phoneValidated = phoneValidated;
        this.phone = phone;
        this.credentialValidated = credentialValidated;
        this.googleTwoFaBinding = googleTwoFaBinding;
        this.nickname = nickname;
        this.name = name;
        this.hasTradePassword = hasTradePassword;
        this.emailValidated = emailValidated;
        this.email = email;
        this.loginRecord = loginRecord;
    }

    /**
     * 
     * @return
     *     The referrerCode
     */
    @JsonProperty("referrer_code")
    public String getReferrerCode() {
        return referrerCode;
    }

    /**
     * 
     * @param referrerCode
     *     The referrer_code
     */
    @JsonProperty("referrer_code")
    public void setReferrerCode(String referrerCode) {
        this.referrerCode = referrerCode;
    }

    /**
     * 
     * @return
     *     The photoCredentialValidated
     */
    @JsonProperty("photoCredentialValidated")
    public Boolean getPhotoCredentialValidated() {
        return photoCredentialValidated;
    }

    /**
     * 
     * @param photoCredentialValidated
     *     The photoCredentialValidated
     */
    @JsonProperty("photoCredentialValidated")
    public void setPhotoCredentialValidated(Boolean photoCredentialValidated) {
        this.photoCredentialValidated = photoCredentialValidated;
    }

    /**
     * 
     * @return
     *     The videoValidated
     */
    @JsonProperty("videoValidated")
    public Boolean getVideoValidated() {
        return videoValidated;
    }

    /**
     * 
     * @param videoValidated
     *     The videoValidated
     */
    @JsonProperty("videoValidated")
    public void setVideoValidated(Boolean videoValidated) {
        this.videoValidated = videoValidated;
    }

    /**
     * 
     * @return
     *     The language
     */
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     *     The currency
     */
    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The oid
     */
    @JsonProperty("oid")
    public String getOid() {
        return oid;
    }

    /**
     * 
     * @param oid
     *     The oid
     */
    @JsonProperty("oid")
    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * 
     * @return
     *     The baseFeeRate
     */
    @JsonProperty("baseFeeRate")
    public Integer getBaseFeeRate() {
        return baseFeeRate;
    }

    /**
     * 
     * @param baseFeeRate
     *     The baseFeeRate
     */
    @JsonProperty("baseFeeRate")
    public void setBaseFeeRate(Integer baseFeeRate) {
        this.baseFeeRate = baseFeeRate;
    }

    /**
     * 
     * @return
     *     The hasCredential
     */
    @JsonProperty("hasCredential")
    public Boolean getHasCredential() {
        return hasCredential;
    }

    /**
     * 
     * @param hasCredential
     *     The hasCredential
     */
    @JsonProperty("hasCredential")
    public void setHasCredential(Boolean hasCredential) {
        this.hasCredential = hasCredential;
    }

    /**
     * 
     * @return
     *     The credentialNumber
     */
    @JsonProperty("credentialNumber")
    public String getCredentialNumber() {
        return credentialNumber;
    }

    /**
     * 
     * @param credentialNumber
     *     The credentialNumber
     */
    @JsonProperty("credentialNumber")
    public void setCredentialNumber(String credentialNumber) {
        this.credentialNumber = credentialNumber;
    }

    /**
     * 
     * @return
     *     The phoneValidated
     */
    @JsonProperty("phoneValidated")
    public Boolean getPhoneValidated() {
        return phoneValidated;
    }

    /**
     * 
     * @param phoneValidated
     *     The phoneValidated
     */
    @JsonProperty("phoneValidated")
    public void setPhoneValidated(Boolean phoneValidated) {
        this.phoneValidated = phoneValidated;
    }

    /**
     * 
     * @return
     *     The phone
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The credentialValidated
     */
    @JsonProperty("credentialValidated")
    public Boolean getCredentialValidated() {
        return credentialValidated;
    }

    /**
     * 
     * @param credentialValidated
     *     The credentialValidated
     */
    @JsonProperty("credentialValidated")
    public void setCredentialValidated(Boolean credentialValidated) {
        this.credentialValidated = credentialValidated;
    }

    /**
     * 
     * @return
     *     The googleTwoFaBinding
     */
    @JsonProperty("googleTwoFaBinding")
    public Boolean getGoogleTwoFaBinding() {
        return googleTwoFaBinding;
    }

    /**
     * 
     * @param googleTwoFaBinding
     *     The googleTwoFaBinding
     */
    @JsonProperty("googleTwoFaBinding")
    public void setGoogleTwoFaBinding(Boolean googleTwoFaBinding) {
        this.googleTwoFaBinding = googleTwoFaBinding;
    }

    /**
     * 
     * @return
     *     The nickname
     */
    @JsonProperty("nickname")
    public Object getNickname() {
        return nickname;
    }

    /**
     * 
     * @param nickname
     *     The nickname
     */
    @JsonProperty("nickname")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The hasTradePassword
     */
    @JsonProperty("hasTradePassword")
    public Boolean getHasTradePassword() {
        return hasTradePassword;
    }

    /**
     * 
     * @param hasTradePassword
     *     The hasTradePassword
     */
    @JsonProperty("hasTradePassword")
    public void setHasTradePassword(Boolean hasTradePassword) {
        this.hasTradePassword = hasTradePassword;
    }

    /**
     * 
     * @return
     *     The emailValidated
     */
    @JsonProperty("emailValidated")
    public Boolean getEmailValidated() {
        return emailValidated;
    }

    /**
     * 
     * @param emailValidated
     *     The emailValidated
     */
    @JsonProperty("emailValidated")
    public void setEmailValidated(Boolean emailValidated) {
        this.emailValidated = emailValidated;
    }

    /**
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The loginRecord
     */
    @JsonProperty("loginRecord")
    public KucoinLoginRecord getLoginRecord() {
        return loginRecord;
    }

    /**
     * 
     * @param loginRecord
     *     The loginRecord
     */
    @JsonProperty("loginRecord")
    public void setLoginRecord(KucoinLoginRecord loginRecord) {
        this.loginRecord = loginRecord;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
