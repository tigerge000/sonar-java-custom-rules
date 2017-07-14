package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/17.
 */
public class AbstractClassNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/HiClass.java", new AbstractClassNameCheck());
    }

    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/MyNameIsAbstract.java", new AbstractClassNameCheck());
    }

    @Test
    public void test2() {
        JavaCheckVerifier.verify("src/test/files/BaseMysql.java", new AbstractClassNameCheck());
    }
    @Test
    public void test3() {
        JavaCheckVerifier.verify("src/test/files/AbstractMysql.java", new AbstractClassNameCheck());
    }
}
