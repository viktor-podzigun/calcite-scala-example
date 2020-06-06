
CREATE TABLE customer
(
  customer_id integer NOT NULL PRIMARY KEY,
  city character varying(30),
  fullname character varying(60) NOT NULL
);

CREATE TABLE time_by_day
(
  time_id integer NOT NULL PRIMARY KEY,
  the_date timestamp WITHOUT TIME ZONE,
  the_month character varying(30),
  month_of_year smallint
);

CREATE TABLE sales_fact_1998
(
  time_id integer NOT NULL,
  customer_id integer NOT NULL,
  unit_sales numeric(10,4) NOT NULL,
  CONSTRAINT sales_fact_1998_time_id_fk FOREIGN KEY (time_id)
    REFERENCES time_by_day (time_id) ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT sales_fact_1998_customer_id_fk FOREIGN KEY (customer_id)
    REFERENCES customer (customer_id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Fill in test data

INSERT INTO customer (customer_id, city, fullname) VALUES
  (1, 'Berlin', 'Max Musterman'),
  (2, 'Albany', 'Customer 1'),
  (3, 'Albany', 'Customer 2'),
  (4, 'Albany', 'Customer 3'),
  (5, 'Albany', 'Customer 4'),
  (6, 'Albany', 'Customer 5'),
  (7, 'Albany', 'Customer 6');

INSERT INTO time_by_day (time_id, the_date, the_month, month_of_year) VALUES
  (1, '2020-01-01 00:00:00', 'January', 1),
  (2, '2020-01-02 00:00:00', 'January', 1),
  (3, '2020-01-03 00:00:00', 'January', 1),
  (4, '2020-01-04 00:00:00', 'January', 1),
  (5, '2020-01-05 00:00:00', 'January', 1),
  (6, '2020-02-01 00:00:00', 'February', 2),
  (7, '2020-02-02 00:00:00', 'February', 2),
  (8, '2020-02-03 00:00:00', 'February', 2),
  (9, '2020-02-04 00:00:00', 'February', 2);

INSERT INTO sales_fact_1998 (time_id, customer_id, unit_sales) VALUES
  (1, 1, 0.1),
  (2, 1, 0.2),
  (1, 2, 0.1),
  (2, 3, 0.2),
  (3, 4, 0.3),
  (4, 5, 0.4),
  (5, 6, 0.5),
  (5, 7, 0.6),
  (6, 2, 0.7),
  (7, 3, 0.8),
  (8, 4, 0.9),
  (9, 5, 1.0),
  (9, 6, 1.1),
  (9, 7, 1.2);
