create table cars (
    id bigserial not null primary key,
    brand varchar(100) not null,
    model varchar(100) not null,
    year integer not null check ( year > 1900 and year < 2025),
    price integer not null check (price > 0)
);