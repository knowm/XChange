package org.knowm.xchange.ascendex.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Package:org.knowm.xchange.ascendex.dto.account
 * Description:
 *
 * @date:2022/7/14 21:15
 * @author:wodepig
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexAccountInfoDto {
    private Integer	accountGroup;
    private String	email;
    /**
     *  If -1, the api key will not expire
     */
    private Long	expireTime;
    private List<String> allowedIps;
    private List<String>	cashAccount;
    private List<String>	marginAccount;
    private String	userUID;
    private Boolean	tradePermission;
    private Boolean	transferPermission;
    private Boolean	viewPermission;

}
