package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/17.
 */
public class UnderlineDollarNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/UnderlineDollarRuleMapper.java", new UnderlineDollarNameCheck());
    }
    @Test
    public void test1(){
        JavaCheckVerifier.verify("src/test/files/ActionTestCase.java", new UnderlineDollarNameCheck());
    }
    @Test
    public void test2(){
        JavaCheckVerifier.verify("src/test/files/ActionTestCaseImpl.java", new UnderlineDollarNameCheck());
    }
}
