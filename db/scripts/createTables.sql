-- main tables

create table if not exists worktime (
	id serial primary key,
	start timestamp,
	finish timestamp
);