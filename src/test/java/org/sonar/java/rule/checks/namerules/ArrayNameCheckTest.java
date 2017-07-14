package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/18.
 */
public class ArrayNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/ArrayNameCheckMapper.java", new ArrayNameCheck());
    }

}
