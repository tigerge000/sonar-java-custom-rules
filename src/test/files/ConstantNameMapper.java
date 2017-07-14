package org.finger.java.rule.checks.namerules;

/**
 * Created by huqingen on 2017/3/17.
 */
public class ConstantNameMapper {

    private String Biz ;

    private static String WH = "2221";

    private static final String NO_BB_CC_DD_CC_SSS = "2212313213";

    private static final String V = "122333";

    private static final String HELLOWORLDMYNAMEISLILEI = "Hello WOLD MY NAME IS LI LEI"; // Noncompliant

    private static final String Hello_WORLD = "Hello WOLD"; // Noncompliant

    private static final String NO_123_ZZZ = "sdddsaa";// Noncompliant
}
