create table plan (
	id int not null auto_increment primary key,
	days int not null,
	name varchar(255),
	description text,
	device_type_id int not null,
	foreign key (device_type_id) references device_type(id)
)

create table device_type (
	id int not null auto_increment primary key,
	code varchar(128),
	name varchar(255) not null,
	description text
)

create table device (
	id int not null auto_increment primary key,
	equip_time date not null,
	location varchar(255) not null,
	device_type_id int not null,
	foreign key (device_type_id) references device_type(id)
)

create table plan_sheet (
	id int not null auto_increment primary key,
	device_id int not null,
	plan_id int not null,
	state enum('wait', 'finished', 'canceled') not null default 'wait',
	employee_id int default null,
	hours float not null,
	foreign key (device_id) references device(id),
	foreign key (plan_id) references plan(id),
	foreign key (employee_id) references employee(id)
)

create table employee (
	id int not null auto_increment primary key,
	name varchar(64) not null,
	gender enum('M', 'F') not null,
	age int not null
)