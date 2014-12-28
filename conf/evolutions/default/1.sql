# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table my_activity (
  id                        bigint not null,
  type                      varchar(255),
  location                  varchar(255),
  distance                  float,
  date                      timestamp,
  duration                  varchar(255),
  constraint pk_my_activity primary key (id))
;

create table my_user (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  nationality               varchar(255),
  constraint pk_my_user primary key (id))
;

create sequence my_activity_seq;

create sequence my_user_seq;




# --- !Downs

drop table if exists my_activity cascade;

drop table if exists my_user cascade;

drop sequence if exists my_activity_seq;

drop sequence if exists my_user_seq;

