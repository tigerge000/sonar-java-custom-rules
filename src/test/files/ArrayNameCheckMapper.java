package org.finger.java.rule.checks.namerules;

/**
 * Created by huqingen on 2017/3/18.
 */
public class ArrayNameCheckMapper {

    private String zzzz;

    private String errorTemp[];// Noncompliant

    private String[] rightTemp;

    public void getTemp(String noTemp[]){// Noncompliant
        int intHi[];// Noncompliant
        int[] intNo;
    }


}
