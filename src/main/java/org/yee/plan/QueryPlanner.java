package org.yee.plan;

import com.google.common.collect.Lists;

import org.apache.calcite.config.Lex;
import org.apache.calcite.interpreter.BindableConvention;
import org.apache.calcite.plan.Contexts;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelTraitDef;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelCollationTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelConversionException;
import org.apache.calcite.tools.ValidationException;
import org.yee.rules.TestRuleSets;

import java.util.List;

public class QueryPlanner {
  private Planner planner;

  public QueryPlanner(SchemaPlus schema) {
    final List<RelTraitDef> traitDefs = Lists.newArrayList();
    traitDefs.add(ConventionTraitDef.INSTANCE);
    traitDefs.add(RelCollationTraitDef.INSTANCE);

    SqlParser.Config configBuilder =
        SqlParser.configBuilder().setLex(Lex.MYSQL).build();
    FrameworkConfig config = Frameworks.newConfigBuilder()
        .defaultSchema(schema)
        .parserConfig(configBuilder)
        .traitDefs(traitDefs)
        .ruleSets(TestRuleSets.getRuleSets())
        .context(Contexts.EMPTY_CONTEXT)
        .costFactory(null)
        .build();

    this.planner = Frameworks.getPlanner(config);
  }

  public RelNode getPlan(String sql)
      throws SqlParseException, ValidationException, RelConversionException {
    SqlNode sqlNode = this.planner.parse(sql);
    SqlNode validated = this.planner.validate(sqlNode);
    RelNode converted = this.planner.rel(validated).rel;

    RelTraitSet traitSet = converted.getTraitSet();
    traitSet = traitSet.replace(BindableConvention.INSTANCE).simplify();
    return this.planner.transform(0, traitSet, converted);
  }
}
