package org.sonar.java.rule;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction;
import org.sonar.api.server.rule.RuleParamType;
import org.sonar.api.server.rule.RulesDefinition;

public class MyJavaRulesDefinitionTest {

  @Test
  public void test() {
    MyJavaRulesDefinition rulesDefinition = new MyJavaRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository(MyJavaRulesDefinition.REPOSITORY_KEY);

    assertThat(repository.key()).isEqualTo("finger-java-custom-rules");
    assertThat(repository.name()).isEqualTo("Finger Java Custom Rules");
    assertThat(repository.language()).isEqualTo("java");
    assertThat(repository.rules()).hasSize(RulesList.getChecks().size());

    assertRuleProperties(repository);
//    assertParameterProperties(repository);
//    assertAllRuleParametersHaveDescription(repository);
  }

  private void assertParameterProperties(RulesDefinition.Repository repository) {
    // TooManyLinesInFunctionCheck
    RulesDefinition.Param max = repository.rule("AvoidAnnotation").param("name");
    assertThat(max).isNotNull();
    assertThat(max.defaultValue()).isEqualTo("Inject");
    assertThat(max.description()).isEqualTo("Name of the annotation to avoid, without the prefix @, for instance 'Override'");
    assertThat(max.type()).isEqualTo(RuleParamType.STRING);
  }

  private void assertRuleProperties(RulesDefinition.Repository repository) {
    RulesDefinition.Rule rule = repository.rule("UnderlineDollarNameCheck");
    assertThat(rule).isNotNull();
    assertThat(rule.name()).isEqualTo("Can't use underline or Dollar first or end As Name");
    assertThat(rule.debtRemediationFunction().type()).isEqualTo(DebtRemediationFunction.Type.CONSTANT_ISSUE);
    assertThat(rule.type()).isEqualTo(RuleType.BUG);
  }

  private void assertAllRuleParametersHaveDescription(RulesDefinition.Repository repository) {
    for (RulesDefinition.Rule rule : repository.rules()) {
      for (RulesDefinition.Param param : rule.params()) {
        assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
      }
    }
  }
}
