#创建库存数据库
create database tcc_inventory default character set utf8 collate utf8_general_ci;

#创建库存表
create table inventory
(
	id bigint auto_increment
		primary key,
	product_code varchar(100) not null,
	left_num int null,
	version bigint not null,
	create_time datetime default CURRENT_TIMESTAMP not null,
	constraint inventory_product_code_uindex
		unique (product_code)
);
#初始化表中库存数据
INSERT INTO tcc_inventory.inventory (id, product_code, left_num, version, create_time) VALUES (1, 'tcc', 100, 1, '2019-09-05 14:56:01');
#创建库存冻结表
create table froze_request
(
	tx_id varchar(200) not null,
	status int default 0 not null,
	product_code varchar(100) not null,
	frozen_num int not null,
	version bigint not null,
	create_time datetime default CURRENT_TIMESTAMP not null,
	constraint froze_request_tx_id_uindex
	unique (tx_id)
);
