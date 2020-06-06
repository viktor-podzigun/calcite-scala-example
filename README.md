
[![Build Status](https://travis-ci.org/viktor-podzigun/calcite-scala-example.svg?branch=master)](https://travis-ci.org/viktor-podzigun/calcite-scala-example)
[![Coverage Status](https://coveralls.io/repos/github/viktor-podzigun/calcite-scala-example/badge.svg?branch=master)](https://coveralls.io/github/viktor-podzigun/calcite-scala-example?branch=master)

## calcite-scala-example
Using Apache Calcite library with Scala example

### How to Build

To build and run all integration tests, use the following command:
```bash
sbt test
```

**Note**: you should have `docker` installed on your system

### How to Run

To run example queries locally, use the following command:
```bash
sbt run
```

and then choose a query to run:
```bash
Multiple main classes detected, select one to run:

 [1] scalcite.example.Task1
 [2] scalcite.example.Task2
 [3] scalcite.example.Task3
 
Enter number: ...
```

**Note**: before running the queries, please, make sure
that you have the test `foodmart` dataset
in your local Postgres DB: https://github.com/OSBI/foodmart-data

