create schema if not exists soa;

create table if not exists soa.student(
albumno serial primary key,
firstname varchar(30) not null,
lastname varchar(30) not null); 
