
create table todo_items (
  id serial,
  todo_list_id integer not null,
  text varchar(255) not null,
  checked boolean not null,
  primary key(id)
);

update schema_info set version = 2;

