drop table if exists users cascade;
drop table if exists authorities cascade;

create table users (
    username varchar(50) not null,
    password varchar(100) not null,
    enabled boolean not null,
    primary key (username)
);

insert into users
values
    ('user1', '{noop}123', true),
    ('user2', '{noop}123', true);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint authorities_idx unique (username, authority),
    constraint authorities_fk_1 foreign key (username) references users (username)
);

insert into authorities
values
    ('user1', 'ROLE_USER'),
    ('user2', 'ROLE_USER');