
package org.knowm.xchange.kucoin.dto.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "datas",
    "total",
    "limit",
    "pageNos",
    "currPageNo",
    "navigatePageNos",
    "coinType",
    "type",
    "userOid",
    "status",
    "firstPage",
    "lastPage",
    "startRow"
})
public class KucoinWalletRecords {

    @JsonProperty("datas")
    private List<KucoinWalletRecord> records = new ArrayList<KucoinWalletRecord>();
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("pageNos")
    private Integer pageNos;
    @JsonProperty("currPageNo")
    private Integer currPageNo;
    @JsonProperty("navigatePageNos")
    private List<Integer> navigatePageNos = new ArrayList<Integer>();
    @JsonProperty("coinType")
    private String coinType;
    @JsonProperty("type")
    private KucoinWalletOperation type;
    @JsonProperty("userOid")
    private String userOid;
    @JsonProperty("status")
    private String status;
    @JsonProperty("firstPage")
    private Boolean firstPage;
    @JsonProperty("lastPage")
    private Boolean lastPage;
    @JsonProperty("startRow")
    private Integer startRow;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public KucoinWalletRecords() {
    }

    /**
     * 
     * @param coinType
     * @param navigatePageNos
     * @param lastPage
     * @param startRow
     * @param userOid
     * @param records
     * @param currPageNo
     * @param type
     * @param total
     * @param firstPage
     * @param limit
     * @param pageNos
     * @param status
     */
    public KucoinWalletRecords(List<KucoinWalletRecord> records, Integer total, Integer limit, Integer pageNos, Integer currPageNo, List<Integer> navigatePageNos, String coinType, KucoinWalletOperation type, String userOid, String status, Boolean firstPage, Boolean lastPage, Integer startRow) {
        super();
        this.records = records;
        this.total = total;
        this.limit = limit;
        this.pageNos = pageNos;
        this.currPageNo = currPageNo;
        this.navigatePageNos = navigatePageNos;
        this.coinType = coinType;
        this.type = type;
        this.userOid = userOid;
        this.status = status;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.startRow = startRow;
    }

    /**
     * 
     * @return
     *     The records
     */
    @JsonProperty("datas")
    public List<KucoinWalletRecord> getRecords() {
        return records;
    }

    /**
     * 
     * @param records
     *     The records
     */
    @JsonProperty("datas")
    public void setRecords(List<KucoinWalletRecord> records) {
        this.records = records;
    }

    /**
     * 
     * @return
     *     The total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The limit
     */
    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    /**
     * 
     * @param limit
     *     The limit
     */
    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 
     * @return
     *     The pageNos
     */
    @JsonProperty("pageNos")
    public Integer getPageNos() {
        return pageNos;
    }

    /**
     * 
     * @param pageNos
     *     The pageNos
     */
    @JsonProperty("pageNos")
    public void setPageNos(Integer pageNos) {
        this.pageNos = pageNos;
    }

    /**
     * 
     * @return
     *     The currPageNo
     */
    @JsonProperty("currPageNo")
    public Integer getCurrPageNo() {
        return currPageNo;
    }

    /**
     * 
     * @param currPageNo
     *     The currPageNo
     */
    @JsonProperty("currPageNo")
    public void setCurrPageNo(Integer currPageNo) {
        this.currPageNo = currPageNo;
    }

    /**
     * 
     * @return
     *     The navigatePageNos
     */
    @JsonProperty("navigatePageNos")
    public List<Integer> getNavigatePageNos() {
        return navigatePageNos;
    }

    /**
     * 
     * @param navigatePageNos
     *     The navigatePageNos
     */
    @JsonProperty("navigatePageNos")
    public void setNavigatePageNos(List<Integer> navigatePageNos) {
        this.navigatePageNos = navigatePageNos;
    }

    /**
     * 
     * @return
     *     The coinType
     */
    @JsonProperty("coinType")
    public String getCoinType() {
        return coinType;
    }

    /**
     * 
     * @param coinType
     *     The coinType
     */
    @JsonProperty("coinType")
    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public Object getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(KucoinWalletOperation type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The userOid
     */
    @JsonProperty("userOid")
    public String getUserOid() {
        return userOid;
    }

    /**
     * 
     * @param userOid
     *     The userOid
     */
    @JsonProperty("userOid")
    public void setUserOid(String userOid) {
        this.userOid = userOid;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public Object getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The firstPage
     */
    @JsonProperty("firstPage")
    public Boolean getFirstPage() {
        return firstPage;
    }

    /**
     * 
     * @param firstPage
     *     The firstPage
     */
    @JsonProperty("firstPage")
    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    /**
     * 
     * @return
     *     The lastPage
     */
    @JsonProperty("lastPage")
    public Boolean getLastPage() {
        return lastPage;
    }

    /**
     * 
     * @param lastPage
     *     The lastPage
     */
    @JsonProperty("lastPage")
    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    /**
     * 
     * @return
     *     The startRow
     */
    @JsonProperty("startRow")
    public Integer getStartRow() {
        return startRow;
    }

    /**
     * 
     * @param startRow
     *     The startRow
     */
    @JsonProperty("startRow")
    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
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
