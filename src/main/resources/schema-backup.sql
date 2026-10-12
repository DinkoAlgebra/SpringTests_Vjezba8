create table if not exists type (
    id IDENTITY PRIMARY KEY,
    name        VARCHAR(50)    NOT NULL,
    description VARCHAR(255)
);

create table if not exists hardware (
    id identity primary key,
    code varchar(100) not null unique,
    name varchar(100) not null,
    typeId int not null,
    stock int not null,
    price numeric not null,
    foreign key (typeId) REFERENCES type(id)
);