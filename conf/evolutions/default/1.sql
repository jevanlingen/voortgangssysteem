# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dashboard (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  project_manager           varchar(255),
  constraint pk_dashboard primary key (id))
;

create table project (
  id                        bigint auto_increment not null,
  fuo_id                    integer,
  name                      varchar(255),
  owner_id                  integer,
  dashboard_id              bigint,
  constraint pk_project primary key (id))
;

create table settings (
  id                        bigint auto_increment not null,
  project_manager           varchar(255),
  constraint pk_settings primary key (id))
;

create table widget (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  update_time               integer,
  activated                 tinyint(1) default 0,
  constraint pk_widget primary key (id))
;


create table settings_project (
  settings_id                    bigint not null,
  project_id                     bigint not null,
  constraint pk_settings_project primary key (settings_id, project_id))
;



alter table settings_project add constraint fk_settings_project_settings_01 foreign key (settings_id) references settings (id) on delete restrict on update restrict;

alter table settings_project add constraint fk_settings_project_project_02 foreign key (project_id) references project (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table dashboard;

drop table project;

drop table settings;

drop table settings_project;

drop table widget;

SET FOREIGN_KEY_CHECKS=1;

