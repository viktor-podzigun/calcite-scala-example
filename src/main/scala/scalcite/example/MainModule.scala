package scalcite.example

import scalcite.example.db._

trait MainModule extends DBModule {

  bind[Query1] to new Query1(inject[PostgresDatabase])
  
  bind[Query2] to new Query2(inject[PostgresDatabase])
  
  bind[Query3] to new Query3(inject[PostgresDatabase])
}
