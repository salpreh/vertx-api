create table product (
  id          serial primary key,
  name        varchar(63) not null,
  description varchar(255),
  price       double precision,
  stock       int
);
