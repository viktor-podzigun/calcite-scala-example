
[![Build Status](https://travis-ci.org/viktor-podzigun/calcite-scala-example.svg?branch=master)](https://travis-ci.org/viktor-podzigun/calcite-scala-example)
[![Coverage Status](https://coveralls.io/repos/github/viktor-podzigun/calcite-scala-example/badge.svg?branch=master)](https://coveralls.io/github/viktor-podzigun/calcite-scala-example?branch=master)

## calcite-scala-example
Using Apache Calcite library with Scala example

### How to use it

Example queries are written for [foodmart](https://github.com/OSBI/foodmart-data)
dataset, so please, make sure you have it in your local db before
running them.

#### Query 1

What's the sum of `unit_sales` from `the sales_fact_1998`
where the Customer's `city` is "Albany":

[Query1](src/main/scala/scalcite/example/Query1.scala) => [tests](src/test/scala/scalcite/example/Query1Spec.scala)

#### Query 2

Drill-down `query 1` to get the sum of sales by each month
(use the `time_by_day` table):

[Query2](src/main/scala/scalcite/example/Query2.scala) => [tests](src/test/scala/scalcite/example/Query2Spec.scala)

#### Query 3

Get the top 5 customer names from "Albany" which have
the highest `unit_sales`:

[Query3](src/main/scala/scalcite/example/Query3.scala) => [tests](src/test/scala/scalcite/example/Query3Spec.scala)

### How to build

To build and run all integration tests, use the following command:
```bash
sbt test
```

**Note**: you should have `docker` installed on your system

### How to run

To run example queries locally, use the following command:
```bash
sbt run
```

and then choose a query to run:
```bash
Multiple main classes detected, select one to run:

 [1] scalcite.example.Query1
 [2] scalcite.example.Query2
 [3] scalcite.example.Query3
 
Enter number: ...
```

**Note**: before running the queries, please, make sure
that you have the test `foodmart` dataset
in your local Postgres DB: https://github.com/OSBI/foodmart-data

