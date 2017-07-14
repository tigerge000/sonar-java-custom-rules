package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/18.
 */
public class ExceptionClassNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/AgeOutOfBound.java", new ExceptionClassNameCheck());
    }

    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/HiClass.java", new ExceptionClassNameCheck());
    }

    @Test
    public void test2() {
        JavaCheckVerifier.verify("src/test/files/AgeOutOfException.java", new ExceptionClassNameCheck());
    }

    @Test
    public void test3() {
        JavaCheckVerifier.verify("src/test/files/AgeOutOfExceptionBound.java", new ExceptionClassNameCheck());
    }
}
