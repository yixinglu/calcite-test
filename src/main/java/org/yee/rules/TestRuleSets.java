package org.yee.rules;

import com.google.common.collect.ImmutableList;

import org.apache.calcite.interpreter.Bindables;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.stream.StreamRules;
import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;

public class TestRuleSets {

  public static final RuleSet[] getRuleSets() {
    ImmutableList<RelOptRule> rules = ImmutableList.<RelOptRule>builder()
        .addAll(StreamRules.RULES)
        .addAll(Bindables.RULES)
        .build();

    return new RuleSet[] {RuleSets.ofList(rules)};
  }
}
