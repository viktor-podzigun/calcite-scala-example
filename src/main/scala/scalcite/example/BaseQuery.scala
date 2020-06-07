package scalcite.example

import java.sql.Statement

import org.apache.calcite.adapter.jdbc.JdbcSchema
import org.apache.calcite.plan.{RelTrait, RelTraitDef}
import org.apache.calcite.rel.RelNode
import org.apache.calcite.rel.rel2sql.RelToSqlConverter
import org.apache.calcite.schema.SchemaPlus
import org.apache.calcite.sql.SqlDialect
import org.apache.calcite.sql.parser.SqlParser
import org.apache.calcite.tools._
import scalcite.example.db.PostgresDatabase

abstract class BaseQuery(db: PostgresDatabase) {

  def apply(): String = {
    BaseQuery.execute(db) { statement =>
      val config = getConfig
      val builder = RelBuilder.create(config)
      run(statement, builder)
    }
  }
  
  protected def run(statement: Statement, builder: RelBuilder): String

  private def getConfig: FrameworkConfig = {

    def getSchema: SchemaPlus = {
      val schemaName = "foodmart"
      val dataSource = JdbcSchema.dataSource(db.getUrl, db.getDriver, db.username, db.password)
      val rootSchema = Frameworks.createRootSchema(true)
      rootSchema.add(schemaName, JdbcSchema.create(rootSchema, schemaName, dataSource, null, null))
    }
    
    Frameworks.newConfigBuilder()
      .parserConfig(SqlParser.Config.DEFAULT)
      .defaultSchema(getSchema)
      .traitDefs(null: java.util.List[RelTraitDef[_ <: RelTrait]])
      .programs(Programs.heuristicJoinOrder(Programs.RULE_SET, true, 2))
      .build()
  }

  protected def toSql(root: RelNode): String = {
    val dialect = SqlDialect.DatabaseProduct.POSTGRESQL.getDialect
    val converter = new RelToSqlConverter(dialect)
    val sqlNode = converter.visitChild(0, root).asStatement
    sqlNode.toSqlString(dialect).getSql
  }
}

object BaseQuery {

  def execute(db: PostgresDatabase)(q: Statement => String): String = {
    val conn = db.getConnection
    try {
      q(conn.createStatement())
    }
    finally {
      //TODO: use proper resource closing:
      //  https://medium.com/@dkomanov/scala-try-with-resources-735baad0fd7d
      conn.close()
    }
  }
}
