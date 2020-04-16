--table customer
--create table customer(custid varchar(50) primary key not null,
--firstname varchar(50),
--lastname varchar(50),
-- email varchar(100) not null,
-- address varchar(200),
-- gender varchar(10),
-- age int,
-- contact varchar(20));
desc customer;

-- table userdetails1
--create table userdetails1(userid varchar(50) primary key not null, custid varchar(50),
--username varchar(100) not null , password varchar(100) not null,
--foreign key (custid) references customer(custid));
--select userid,custid from userdetails1 where username = 'Prabhatkumar' and password = 'Prabhat12345';
desc userdetails1;

create table product(id varchar(20) primary key not null,
name varchar(50) not null,
price int not null,
description varchar(200));
desc product;



create table stock(stockid varchar(20) primary key,
productid varchar(20), 
quantity number(20) not null ,
FOREIGN key (productid) references product(id));
desc stock;
insert into stock(stockid,quantity) values('st2',10);


create table orders(orderno varchar(20) primary key not null,
custid int not null,
orderdate date,
FOREIGN key (custid) references customer(custid));
desc orders;


create table  orderdetails(productid varchar(50),
orderid varchar(20),
quantity number(20),
FOREIGN key (orderid) references orders(orderno),
FOREIGN key (productid) references product(id));
desc orderdetails;

create table shipment(id varchar(50) primary key not null,
address varchar(200) not null,
shipdate date,
contact varchar(20) not null,
orderid varchar(20),
FOREIGN key (orderid)REFERENCES orders(orderno));
desc shipment;

create table payment(payno varchar(20) primary key,
type varchar(20),
paydate date,
amount varchar(20) not null,
orderid varchar(20),
FOREIGN key (orderid) REFERENCES orders(orderno));
desc payment;

select * from customer;
select * from userdetails1;
select * from product;
select * from stock;
select * from orders;
select * from orderdetails;
select * from shipment;
select * from payment;

select product.id,product.name,product.price,product.description,stock.quantity from product inner join stock on product.name = stock.stockid
where product.name='rice';
delete orders  where custid = 1;