package org.yee.schema;

import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.util.Pair;

import java.util.List;

public class TestSchema {

  private final SchemaPlus schema = Frameworks.createRootSchema(true);

  public TestSchema() {
  }

  public SchemaPlus getSchema() {
    return schema;
  }

  public void registerTable(String tableName,
                            final List<String> names,
                            final List<SqlTypeName> types) {
    Table table = new TestTable(tableName, Pair.zip(names, types));
    schema.add(tableName, table);
  }
}
