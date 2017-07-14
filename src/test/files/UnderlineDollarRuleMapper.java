package com.finger.alirules.checks.namerules;

/**
 * Created by huqingen on 2017/3/16.
 */
public class UnderlineDollarRuleMapper {
    private String _Hi;// Noncompliant
    private String Hi_;// Noncompliant
    private String $Hi;// Noncompliant
    private String Hi$;// Noncompliant

    private String Hi;

    public void test(String _Hi){}// Noncompliant

    public void test(String Hi_){}// Noncompliant

    public void test(String $Hi){}// Noncompliant

    public void test(String Hi$){}// Noncompliant

    public void test_(){}  // Noncompliant

    public void $test(){}  // Noncompliant

    public void test$(){}  // Noncompliant
}
