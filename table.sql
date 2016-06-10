drop table plan_sheet;
drop table employee;
drop table device;
drop table plan;
drop table device_type;

create table device_type (
	id int not null auto_increment primary key,
	code varchar(128),
	name varchar(255) not null,
	description text
);

create table plan (
	id int not null auto_increment primary key,
	days int not null,
	name enum('SMALL', 'MIDDLE', 'BIG') not null,
	description text,
	device_type_id int not null,
	foreign key (device_type_id) references device_type(id)
);

create table device (
	id int not null auto_increment primary key,
	equip_time date not null,
	location varchar(255) not null,
	device_type_id int not null,
	foreign key (device_type_id) references device_type(id)
);

create table employee (
	id int not null auto_increment primary key,
	name varchar(64) not null,
	gender enum('M', 'F') not null,
	age int not null
);

create table plan_sheet (
	id int not null auto_increment primary key,
	device_id int not null,
	plan_id int not null,
	state enum('WAITING', 'FINISHED', 'CANCELED') not null default 'WAITING',
	should_do_time date not null,
	do_time date default null,
	employee_id int default null,
	hours float default null,
	foreign key (device_id) references device(id),
	foreign key (plan_id) references plan(id),
	foreign key (employee_id) references employee(id)
);
