
CREATE TABLE customer
(
  customer_id integer NOT NULL PRIMARY KEY,
  city character varying(30),
  fullname character varying(60) NOT NULL
);

CREATE TABLE time_by_day
(
  time_id integer NOT NULL PRIMARY KEY,
  the_date timestamp WITHOUT TIME ZONE
);

CREATE TABLE sales_fact_1998
(
  time_id integer NOT NULL,
  customer_id integer NOT NULL,
  unit_sales numeric(10,4) NOT NULL
);

-- Fill in test data

INSERT INTO customer (customer_id, city, fullname) VALUES
  (1, 'Berlin', 'Max Musterman'),
  (2, 'Albany', 'Customer 1'),
  (3, 'Albany', 'Customer 2');

INSERT INTO time_by_day (time_id, the_date) VALUES
  (1, now() - interval '1' day),
  (2, now() - interval '2' day);

INSERT INTO sales_fact_1998 (time_id, customer_id, unit_sales) VALUES
  (1, 1, 0.1),
  (1, 2, 0.1),
  (1, 3, 0.2),
  (2, 2, 0.3),
  (2, 3, 0.4);
