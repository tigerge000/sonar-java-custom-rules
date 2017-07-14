package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/17.
 */
public class ConstantNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/ConstantNameMapper.java", new ConstantNameCheck());
    }
}
