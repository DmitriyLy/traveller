alter table ACCOUNT drop foreign key FK9rxadd7hdlmarov6qbvaot0po;
alter table ACCOUNT drop foreign key FK4cfkq2sywnlcu6fxm9xbrmsjs;
alter table CITY drop foreign key FKg20pyto8we1mjpxdf0ixmfkgd;
alter table CITY drop foreign key FKi4i6u7b4qdx3pjr1j6qq6y2vm;
alter table STATION drop foreign key FKanu16qi926w9omslreoj7smq1;
alter table STATION drop foreign key FKp7x70rdqw4fidaslc492bnfgk;
alter table STATION drop foreign key FKl8s21emvhb86aubh7m389hrlo;
drop table if exists ACCOUNT;
drop table if exists CITY;
drop table if exists STATION;
create table ACCOUNT (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, CREATED_BY integer, MODIFIED_BY integer, primary key (id));
create table CITY (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, DISTRICT varchar(32) not null, NAME varchar(32) not null, REGION varchar(32) not null, CREATED_BY integer, MODIFIED_BY integer, primary key (id));
create table STATION (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, APARTMENT varchar(16), HOUSE_NO varchar(16), STREET varchar(32), ZIP_CODE varchar(10), X double precision, Y double precision, PHONE varchar(16), TRANSPORT_TYPE varchar(255) not null, CREATED_BY integer, MODIFIED_BY integer, CITY_ID integer, primary key (id));
alter table ACCOUNT add constraint FK9rxadd7hdlmarov6qbvaot0po foreign key (CREATED_BY) references ACCOUNT (id);
alter table ACCOUNT add constraint FK4cfkq2sywnlcu6fxm9xbrmsjs foreign key (MODIFIED_BY) references ACCOUNT (id);
alter table CITY add constraint FKg20pyto8we1mjpxdf0ixmfkgd foreign key (CREATED_BY) references ACCOUNT (id);
alter table CITY add constraint FKi4i6u7b4qdx3pjr1j6qq6y2vm foreign key (MODIFIED_BY) references ACCOUNT (id);
alter table STATION add constraint FKanu16qi926w9omslreoj7smq1 foreign key (CREATED_BY) references ACCOUNT (id);
alter table STATION add constraint FKp7x70rdqw4fidaslc492bnfgk foreign key (MODIFIED_BY) references ACCOUNT (id);
alter table STATION add constraint FKl8s21emvhb86aubh7m389hrlo foreign key (CITY_ID) references CITY (id);
drop table if exists CITY;
drop table if exists ROUTE;
drop table if exists STATION;
drop table if exists TRIP;
drop table if exists USER;
create table CITY (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, DISTRICT varchar(32) not null, NAME varchar(32) not null, REGION varchar(32) not null, CREATED_BY integer, MODIFIED_BY integer, primary key (id)) engine=MyISAM;
create table ROUTE (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, END_TIME time not null, PRICE double precision, START_TIME time not null, CREATED_BY integer, MODIFIED_BY integer, DESTINATION_ID integer not null, START_ID integer not null, primary key (id)) engine=MyISAM;
create table STATION (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, APARTMENT varchar(16), HOUSE_NO varchar(16), STREET varchar(32), ZIP_CODE varchar(10), X double precision, Y double precision, PHONE varchar(16), TRANSPORT_TYPE varchar(255) not null, CREATED_BY integer, MODIFIED_BY integer, CITY_ID integer, primary key (id)) engine=MyISAM;
create table TRIP (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, AVAILABLE_SEATS integer not null, END_TIME time not null, MAX_SEATS integer not null, PRICE double precision not null, START_TIME time not null, CREATED_BY integer, MODIFIED_BY integer, ROUTE_ID integer, primary key (id)) engine=MyISAM;
create table USER (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, PASSWORD varchar(100) not null, USERNAME varchar(24) not null, CREATED_BY integer, MODIFIED_BY integer, primary key (id)) engine=MyISAM;
alter table USER add constraint UK_lb5yrvw2c22im784wwrpwuq06 unique (USERNAME);
alter table CITY add constraint FKrp4063udw4ydttpqldcj65nq7 foreign key (CREATED_BY) references USER (id);
alter table CITY add constraint FKi8vkwqtlq8rhbcavhmr0v6xj9 foreign key (MODIFIED_BY) references USER (id);
alter table ROUTE add constraint FKdb37r4x90q8m815paaa4d0072 foreign key (CREATED_BY) references USER (id);
alter table ROUTE add constraint FKda03f8aakq59r9ykogjqc0n5q foreign key (MODIFIED_BY) references USER (id);
alter table ROUTE add constraint FKkxbotjy2tc82a8qj59ugkdtaw foreign key (DESTINATION_ID) references STATION (id);
alter table ROUTE add constraint FK1fspkx7sdu8anad8y563cnwon foreign key (START_ID) references STATION (id);
alter table STATION add constraint FKm9tddjn8mtr15gti7ih585cve foreign key (CREATED_BY) references USER (id);
alter table STATION add constraint FKoq0m47ngd6e5lgwlmw1d2n4ry foreign key (MODIFIED_BY) references USER (id);
alter table STATION add constraint FKl8s21emvhb86aubh7m389hrlo foreign key (CITY_ID) references CITY (id);
alter table TRIP add constraint FKp73il4qnueu3ufj4hfkg61uck foreign key (CREATED_BY) references USER (id);
alter table TRIP add constraint FK7toq0gkbq4xawm48a9fxs2wev foreign key (MODIFIED_BY) references USER (id);
alter table TRIP add constraint FKt2m5dcgww4violuewk41w9961 foreign key (ROUTE_ID) references ROUTE (id);
alter table USER add constraint FKn1fv6g65ocut1knocvdt341cf foreign key (CREATED_BY) references USER (id);
alter table USER add constraint FKe57lfmyrymkha8t1ef17i93hd foreign key (MODIFIED_BY) references USER (id);
drop table if exists CITY;
drop table if exists ORDERS;
drop table if exists ROUTE;
drop table if exists STATION;
drop table if exists TICKETS;
drop table if exists TRIP;
drop table if exists USER;
create table CITY (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, DISTRICT varchar(32) not null, NAME varchar(32) not null, REGION varchar(32) not null, CREATED_BY integer, MODIFIED_BY integer, primary key (id)) engine=MyISAM;
create table ORDERS (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, CANCELLATION_REASON varchar(128), CLIENT_NAME varchar(32) not null, CLIENT_PHONE varchar(24) not null, DUE_DATE datetime not null, ORDER_STATE integer not null, CREATED_BY integer, MODIFIED_BY integer, TICKET_ID integer, TRIP_ID integer not null, primary key (id)) engine=MyISAM;
create table ROUTE (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, END_TIME time not null, PRICE double precision, START_TIME time not null, CREATED_BY integer, MODIFIED_BY integer, DESTINATION_ID integer not null, START_ID integer not null, primary key (id)) engine=MyISAM;
create table STATION (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, APARTMENT varchar(16), HOUSE_NO varchar(16), STREET varchar(32), ZIP_CODE varchar(10), X double precision, Y double precision, PHONE varchar(16), TRANSPORT_TYPE varchar(255) not null, CREATED_BY integer, MODIFIED_BY integer, CITY_ID integer, primary key (id)) engine=MyISAM;
create table TICKETS (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, name varchar(32) not null, uid varchar(60) not null, CREATED_BY integer, MODIFIED_BY integer, TRIP_ID integer, primary key (id)) engine=MyISAM;
create table TRIP (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, AVAILABLE_SEATS integer not null, END_TIME time not null, MAX_SEATS integer not null, PRICE double precision not null, START_TIME time not null, CREATED_BY integer, MODIFIED_BY integer, ROUTE_ID integer not null, primary key (id)) engine=MyISAM;
create table USER (id integer not null auto_increment, CREATED_AT datetime not null, MODIFIED_AT datetime, PASSWORD varchar(100) not null, USERNAME varchar(24) not null, CREATED_BY integer, MODIFIED_BY integer, primary key (id)) engine=MyISAM;
alter table USER add constraint UK_lb5yrvw2c22im784wwrpwuq06 unique (USERNAME);
alter table CITY add constraint FKrp4063udw4ydttpqldcj65nq7 foreign key (CREATED_BY) references USER (id);
alter table CITY add constraint FKi8vkwqtlq8rhbcavhmr0v6xj9 foreign key (MODIFIED_BY) references USER (id);
alter table ORDERS add constraint FKqu1kfkss1ps3vbwr5r7polsv3 foreign key (CREATED_BY) references USER (id);
alter table ORDERS add constraint FKscmyk3h95mns2rwh5j8kr51xf foreign key (MODIFIED_BY) references USER (id);
alter table ORDERS add constraint FKstqs7160nvg92620732g0or5l foreign key (TICKET_ID) references TICKETS (id);
alter table ORDERS add constraint FKcxtb2y2c52scsnpxnhn186pfu foreign key (TRIP_ID) references TRIP (id);
alter table ROUTE add constraint FKdb37r4x90q8m815paaa4d0072 foreign key (CREATED_BY) references USER (id);
alter table ROUTE add constraint FKda03f8aakq59r9ykogjqc0n5q foreign key (MODIFIED_BY) references USER (id);
alter table ROUTE add constraint FKkxbotjy2tc82a8qj59ugkdtaw foreign key (DESTINATION_ID) references STATION (id);
alter table ROUTE add constraint FK1fspkx7sdu8anad8y563cnwon foreign key (START_ID) references STATION (id);
alter table STATION add constraint FKm9tddjn8mtr15gti7ih585cve foreign key (CREATED_BY) references USER (id);
alter table STATION add constraint FKoq0m47ngd6e5lgwlmw1d2n4ry foreign key (MODIFIED_BY) references USER (id);
alter table STATION add constraint FKl8s21emvhb86aubh7m389hrlo foreign key (CITY_ID) references CITY (id);
alter table TICKETS add constraint FKgqsbv3py0dbokglwxg68g5n3i foreign key (CREATED_BY) references USER (id);
alter table TICKETS add constraint FKchnrqrq0qd0x74r87kuc63bj2 foreign key (MODIFIED_BY) references USER (id);
alter table TICKETS add constraint FKj8mboonllnli25on2erdypp9y foreign key (TRIP_ID) references TRIP (id);
alter table TRIP add constraint FKp73il4qnueu3ufj4hfkg61uck foreign key (CREATED_BY) references USER (id);
alter table TRIP add constraint FK7toq0gkbq4xawm48a9fxs2wev foreign key (MODIFIED_BY) references USER (id);
alter table TRIP add constraint FKt2m5dcgww4violuewk41w9961 foreign key (ROUTE_ID) references ROUTE (id);
alter table USER add constraint FKn1fv6g65ocut1knocvdt341cf foreign key (CREATED_BY) references USER (id);
alter table USER add constraint FKe57lfmyrymkha8t1ef17i93hd foreign key (MODIFIED_BY) references USER (id);
