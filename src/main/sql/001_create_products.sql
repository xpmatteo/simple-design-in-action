
create table products (
  code varchar(255) not null,
  price integer not null,
  primary key(code)
);

update schema_info set version = 1;

