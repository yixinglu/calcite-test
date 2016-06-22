package org.yee.schema;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.logical.LogicalTableScan;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelProtoDataType;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.schema.FilterableTable;
import org.apache.calcite.schema.StreamableTable;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.Pair;

import java.util.List;

public class TestTable extends AbstractTable
    implements StreamableTable, TranslatableTable, FilterableTable {

  private final String name;
  private final RelProtoDataType protoDataType;

  public TestTable(String name, final List<Pair<String, SqlTypeName>> fields) {
    this.name = name;
    protoDataType = new RelProtoDataType() {
      public RelDataType apply(RelDataTypeFactory relDataTypeFactory) {
        RelDataTypeFactory.FieldInfoBuilder builder = relDataTypeFactory.builder();
        for (Pair<String, SqlTypeName> field : fields) {
          builder.add(field.left, field.right);
        }
        return builder.build();
      }
    };
  }

  public Table stream() {
    return new TestStreamTable(protoDataType);
  }

  public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
    return protoDataType.apply(relDataTypeFactory);
  }

  @Override
  public RelNode toRel(RelOptTable.ToRelContext toRelContext, RelOptTable relOptTable) {
    return LogicalTableScan.create(toRelContext.getCluster(), relOptTable);
  }

  @Override
  public Enumerable<Object[]> scan(DataContext dataContext, List<RexNode> list) {
    return null;
  }
}

class TestStreamTable extends AbstractTable
    implements TranslatableTable, FilterableTable {
  private final RelProtoDataType proto;

  public TestStreamTable(final RelProtoDataType proto) {
    this.proto = proto;
  }

  public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
    return proto.apply(relDataTypeFactory);
  }

  public RelNode toRel(RelOptTable.ToRelContext toRelContext, RelOptTable relOptTable) {
    return LogicalTableScan.create(toRelContext.getCluster(), relOptTable);
  }

  @Override
  public Enumerable<Object[]> scan(DataContext dataContext, List<RexNode> list) {
    return null;
  }
}
