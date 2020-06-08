package scalcite.example

import java.sql.Statement

import org.apache.calcite.rel.core.JoinRelType
import org.apache.calcite.sql.fun.SqlStdOperatorTable
import org.apache.calcite.tools.RelBuilder
import scalcite.example.db.PostgresDatabase

class Query3(db: PostgresDatabase) extends BaseQuery(db) {

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
        builder.groupKey("customer_id", "fullname"),
        builder.sum(false, "sum", builder.field("unit_sales"))
      )
      .project(
        builder.field("fullname"),
        builder.field("sum")
      )
      .sort(builder.desc(builder.field("sum")))
      .limit(0, 5)
      .build

    val sql = toSql(node)
    val resultSet = statement.executeQuery(sql)

    val results = new StringBuilder
    while (resultSet.next()) {
      val date = resultSet.getObject(1)
      val sum = resultSet.getObject(2)
      results ++= s"$date: $sum\n"
    }
    
    results.toString()
  }
}

object Query3 extends Main[Query3]
