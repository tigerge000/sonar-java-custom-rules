package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/17.
 */
public class UpperCameCaseCheckTest {
    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/macroPolo.java", new UpperCameCaseCheck());
    }

    @Test
    public void test2(){
        JavaCheckVerifier.verify("src/test/files/UserDo.java", new UpperCameCaseCheck());
    }
    @Test
    public void test3(){
        JavaCheckVerifier.verify("src/test/files/AbstractMysql.java", new UpperCameCaseCheck());
    }
    @Test
    public void test4(){
        JavaCheckVerifier.verify("src/test/files/ActionTestCase.java", new UpperCameCaseCheck());
    }
}
