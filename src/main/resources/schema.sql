drop database traindb_spring;
create database traindb_spring;
use traindb_spring;

create table user (
id integer not null auto_increment,
email varchar(255) not null,
first_name varchar(255) not null,
last_name varchar(255) not null,
pass_encoded varchar(255) not null,
role varchar(255) not null,
primary key (id))
engine=InnoDB;
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

create table station (
id integer not null auto_increment,
code varchar(255) not null,
name varchar(255) not null,
primary key (id))
engine=InnoDB;

create table train_model (
id integer not null auto_increment,
model varchar(255) not null,
primary key (id))
engine=InnoDB;

create table train (
id integer not null auto_increment,
model_id integer not null,
primary key (id))
engine=InnoDB;

create table route (
route_id integer not null,
train_id integer not null,
primary key (route_id))
engine=InnoDB;
alter table route add constraint FK8v3ar2wt47nlc4kq71g5gn78m foreign key (train_id) references train (id);

create table route_point (
id integer not null auto_increment,
arrival datetime(6) not null,
departure datetime(6) not null,
route_id integer,
station_id integer not null, primary key (id))
engine=InnoDB;
alter table route_point add constraint FK4t91b3gwkyqtd81m67mvjmg9f foreign key (route_id) references route (route_id);
alter table route_point add constraint FKcodah2wb3s2dmm81wkrx06fh1 foreign key (station_id) references station (id);

create table wagon (
id integer not null auto_increment,
base_price integer not null,
num_of_seats integer not null,
type varchar(255) not null,
route_id integer not null,
primary key (id))
engine=InnoDB;
alter table wagon add constraint FKhrshm9ju8piouj58kvi6mjpcj foreign key (route_id) references route (route_id);



create table passenger_details
(id integer not null auto_increment,
first_name varchar(255) not null,
last_name varchar(255) not null,
primary key (id)) engine=InnoDB;

create table ticket_details (
id integer not null auto_increment,
duration varchar(255) not null,
total_price integer not null,
status varchar(255) not null,
ticket_type varchar(255) not null,
wagon integer not null,
primary key (id))
engine=InnoDB;

create table ticket (
id integer not null auto_increment,
created_at datetime(6),
arrival_id integer not null,
departure_id integer not null,
passenger_id integer not null,
ticket_details integer not null,
user_id integer, primary key (id)) engine=InnoDB;
alter table ticket add constraint FK4w859x1s2cpluwe7a27ur3gl0 foreign key (arrival_id) references route_point (id);
alter table ticket add constraint FKruci1g617cp8kiyy7eh4qe919 foreign key (departure_id) references route_point (id);
alter table ticket add constraint FKk22vcjia39117ncd2ly4pg4x9 foreign key (passenger_id) references passenger_details (id);
alter table ticket add constraint FK6nhsrl1oc3b7r3bicaentufbf foreign key (ticket_details) references ticket_details (id);
alter table ticket add constraint FKdvt57mcco3ogsosi97odw563o foreign key (user_id) references user (id);
alter table train add constraint FKrutvny8gqxs36vjvhtvd8jo2c foreign key (model_id) references train_model (id);
