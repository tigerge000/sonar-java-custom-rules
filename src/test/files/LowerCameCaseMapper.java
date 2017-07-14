package org.finger.java.rule.checks.namerules;

/**
 * Created by huqingen on 2017/3/17.
 */
public class LowerCameCaseMapper {
    private String ID;// Noncompliant
    private String UserName;// Noncompliant
    private String password;// Compliant

    private static final String NO_BB_CC_DD_CC_SSS = "2212313213";

    public String getUserInfo(String info) { // Noncompliant
        String Yes = "Yes";// Noncompliant
        return info+Yes;
    }

    public void GetPassword(){} // Noncompliant

    public void getUser(String UserInfo) {// Noncompliant

    }
}
