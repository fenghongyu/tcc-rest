#创建订单数据库
create database tcc_order default character set utf8 collate utf8_general_ci;

#创建用户的订单表
create table user_order
(
	id bigint auto_increment primary key,
	tx_id varchar(200) not null,
	user_id bigint not null,
	product_code varchar(100) not null,
	quantity int default 0 not null,
	state enum('ORDERED', 'CONFIRMED', 'CANCELED') not null,
	expire_time datetime not null,
	version bigint not null,
	create_time datetime default CURRENT_TIMESTAMP not null,
	constraint user_order_tx_id_uindex
	unique (tx_id)
);

