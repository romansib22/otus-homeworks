create table products (
    id bigserial primary key,
    name varchar(255),
    price numeric
);

insert into products (name, price) values ('Milk', 105.5), ('Bread', 75);
