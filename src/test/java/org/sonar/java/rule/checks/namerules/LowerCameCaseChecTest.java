package org.sonar.java.rule.checks.namerules;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

/**
 * Created by huqingen on 2017/3/17.
 */
public class LowerCameCaseChecTest {
    @Test
    public void test() {
        try {
            JavaCheckVerifier.verify("src/test/files/LowerCameCaseMapper.java", new LowerCameCaseCheck());
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/Result.java", new LowerCameCaseCheck());
    }

}
