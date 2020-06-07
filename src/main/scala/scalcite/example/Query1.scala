package scalcite.example

import java.sql.Statement

import org.apache.calcite.rel.core.JoinRelType
import org.apache.calcite.sql.fun.SqlStdOperatorTable
import org.apache.calcite.tools.RelBuilder
import scalcite.example.db.PostgresDatabase

class Query1(db: PostgresDatabase) extends BaseQuery(db) {
  
  protected def run(statement: Statement, builder: RelBuilder): String = {
    val node = builder
      .scan("sales_fact_1998")
      .scan("customer")
      .join(JoinRelType.INNER, "customer_id")
      .filter(
        builder.call(
          SqlStdOperatorTable.EQUALS,
          builder.field("city"),
          builder.literal("Albany")
        )
      )
      .aggregate(
        builder.groupKey("city"),
        builder.sum(false, "sum", builder.field("unit_sales"))
      )
      .build

    val sql = toSql(node)
    val resultSet = statement.executeQuery(sql)

    val results = new StringBuilder
    while (resultSet.next()) {
      val sum = resultSet.getObject("sum")
      results ++= s"sum: $sum"
    }
    
    results.toString()
  }
}

object Query1 extends Main[Query1]
