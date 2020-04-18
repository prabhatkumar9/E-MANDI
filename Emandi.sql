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

--create table payment(payno varchar(20) primary key,
--type varchar(20),
--paydate date,
--amount varchar(20) not null,
--orderid varchar(20),
--FOREIGN key (orderid) REFERENCES orders(orderno));
desc payment;
--
--
--delete from product where id = 'prod2';
--insert into product values('prod1','cheese',90,'cheesse');
--select quantity from stock where stockid='rice';



desc orders;
ALTER TABLE orders
 drop column quantity; 
--delete from orders where custid=1;
--delete from stock;

select * from orders;
select * from customer;
select * from userdetails1;
select * from product;
select * from orderdetails;
select * from shipment;
select * from payment;
select * from stock;

--select product.id,product.name,product.price,product.description,stock.quantity from product inner join stock on product.name = stock.stockid
--where product.name='rice';
--
--select product.price,stock.quantity from product inner join stock on product.name=stock.stockid where product.name='rice';
--
--delete orders  where custid = 1;


--user details 1st step
select userdetails1.username, userdetails1.password,
customer.firstname,customer.lastname,customer.email,customer.age,customer.gender,customer.address
from userdetails1
inner join customer on userdetails1.customerid = customer.custid
where custid = 1;
--
--ALTER table payment
--RENAME COLUMN orderid TO ordid;

--2nd step
select orders.orderno,orderdate
from orders where customerid = 1;
--use rs.next and inner loop

--3rd step
--product details
select orderdetails.productid,product.name,product.price,orderdetails.quantity
from orderdetails
inner join product on orderdetails.productid = product.id 
where orderdetails.orderid='order5';

--4th step
--shipment
select payment.amount,payment.payno,payment.type,payment.paydate,
shipment.contact,shipment.address,shipment.shipdate
from shipment inner join payment on payment.ordid = shipment.ordrid
where ordrid='order5';


----payment details
--select payno,type,paydate,amount from payment where ordid = 'order5';

--order details
select orders.orderno,payment.amount,
payment.type,payment.payno, payment.paydate,
shipment.shipdate, shipment.contact
from orders
inner join orderdetails on orders.orderno = orderdetails.orderid
inner join product on orderdetails.productid = product.id
inner join shipment on orderdetails.orderid = shipment.ordrid
inner join payment on shipment.ordrid = payment.ordid
where customerid = 1;

desc orders;
