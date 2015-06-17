
create table todo_lists (
  id serial,
  name varchar(255) not null,
  primary key(id)
);

update schema_info set version = 1;

