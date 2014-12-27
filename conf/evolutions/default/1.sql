# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table my_user (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_my_user primary key (id))
;

create sequence my_user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists my_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists my_user_seq;

