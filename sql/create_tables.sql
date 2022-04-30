drop table if exists discounts cascade;
drop table if exists products cascade;

create table discounts (
    id serial,
    name varchar(255) not null,
    start_date varchar(15) not null,
    end_date varchar(15) not null,
    constraint discounts_pk primary key (id)
);

create table products (
    id serial,
    name varchar(255) not null unique,
    price double precision not null,
    image text,
    discount int,
    constraint products_pk primary key (id),
    constraint products_fk_1 foreign key (discount) references discounts (id)
);

insert into discounts (name, start_date, end_date)
values
    ('Бонусная неделя апреля', '25.04.2022', '01.05.2022'),
    ('Бонусная неделя мая', '02.05.2022', '08.05.2022');

insert into products (name, price, image, discount)
values
    ('13.3" Ноутбук Apple MacBook Air', 144999.0, 'https://c.dns-shop.ru/thumb/st4/fit/200/200/9df564d71131bea63b94ecf568bde297/41fbb0012918eb89be15f209addbb49912ee554576f157be471a9728f040d32c.jpg.webp', 1),
    ('15.6" Ноутбук AORUS 15P XD', 179999.0, 'https://c.dns-shop.ru/thumb/st4/fit/200/200/ec79fbc393ae0ab9739eb7e68cbc5794/2b5677c39580ccbde4617155c2e5bc8acee8cf18fe404fd6060f88e6994bd72f.jpg.webp', 2),
    ('14" Ноутбук ASUS ROG Zephyrus', 174999.0, 'https://c.dns-shop.ru/thumb/st4/fit/200/200/3324a569501216e5fb389e7fd04a2e31/699983feb8da14d8ec0982255c5155f967a685f2a3a074753a54b7ddf6a96827.jpg.webp', 2),
    ('15.6" Ноутбук ASUS TUF Gaming A15', 92999.0, 'https://c.dns-shop.ru/thumb/st4/fit/200/200/26c13421d6b226b46cbaddbb0841fcef/52a595b0a4f829a85e06480eb05df4d40bc937bdf844839cf028becec2ad956c.jpg.webp', 1),
    ('16.1" Ноутбук HUAWEI MateBook D 16', 79999.0, 'https://c.dns-shop.ru/thumb/st1/fit/200/200/50d7fc87fad03dd9616d13062786427b/beeaa59edbf822ea240589b9a279719f5b0f7f1e08e820c82b73d0caf5e9f633.jpg.webp', null),
    ('15.6" Ноутбук HP Laptop', 59999.0, 'https://c.dns-shop.ru/thumb/st4/fit/200/200/713cd63087227f8030e8857ba0a56d71/5d175d8f682188343c08a10b60f067ac73de6a9d75db18fd19df8935ee69dee0.jpg.webp', null),
    ('15.6" Ноутбук Lenovo IdeaPad Gaming', 74999.0, 'https://c.dns-shop.ru/thumb/st4/fit/200/200/b3aedc0164947afaee7798fde4ffd5e9/63add0435dc2ca1f4082d79c725ff635bf24115760a1825efa496a176cf2d807.jpg.webp', null);

