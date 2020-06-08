package scalcite.example

import java.sql.Statement

import org.apache.calcite.rel.core.JoinRelType
import org.apache.calcite.sql.fun.SqlStdOperatorTable
import org.apache.calcite.tools.RelBuilder
import scalcite.example.db.PostgresDatabase

class Query2(db: PostgresDatabase) extends BaseQuery(db) {

  protected def run(statement: Statement, builder: RelBuilder): String = {
    val node = builder
      .scan("sales_fact_1998")
      .scan("customer")
      .join(JoinRelType.INNER, "customer_id")
      .scan("time_by_day")
      .join(JoinRelType.INNER, "time_id")
      .filter(
        builder.call(
          SqlStdOperatorTable.EQUALS,
          builder.field("city"),
          builder.literal("Albany")
        )
      )
      .aggregate(
        builder.groupKey("month_of_year", "the_month"),
        builder.sum(false, "sum", builder.field("unit_sales"))
      )
      .project(
        builder.field("month_of_year"),
        builder.field("the_month"),
        builder.field("sum")
      )
      .sort(builder.field("month_of_year"))
      .build

    val sql = toSql(node)
    val resultSet = statement.executeQuery(sql)

    val results = new StringBuilder
    while (resultSet.next()) {
      val date = resultSet.getObject(2)
      val sum = resultSet.getObject(3)
      results ++= s"$date: $sum\n"
    }
    
    results.toString()
  }
}

object Query2 extends Main[Query2]
