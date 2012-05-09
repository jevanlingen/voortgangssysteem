# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dashboard (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  project_manager           varchar(255),
  constraint uq_dashboard_label unique (label),
  constraint pk_dashboard primary key (id))
;

create table dashboard_project (
  id                        bigint auto_increment not null,
  fuo_id                    integer,
  client_name               varchar(255),
  name                      varchar(255),
  owner_id                  integer,
  dashboard_id              bigint,
  projecttype_description   varchar(255),
  constraint pk_dashboard_project primary key (id))
;

create table widget (
  id                        bigint auto_increment not null,
  project_id                bigint,
  name                      varchar(255),
  update_time               integer,
  activated                 tinyint(1) default 0,
  constraint pk_widget primary key (id))
;

create table widget_setting (
  id                        bigint auto_increment not null,
  widget_id                 bigint not null,
  type                      varchar(255),
  name                      varchar(255),
  value                     varchar(255),
  constraint pk_widget_setting primary key (id))
;

alter table widget_setting add constraint fk_widget_setting_widget_1 foreign key (widget_id) references widget (id) on delete restrict on update restrict;
create index ix_widget_setting_widget_1 on widget_setting (widget_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table dashboard;

drop table dashboard_project;

drop table widget;

drop table widget_setting;

SET FOREIGN_KEY_CHECKS=1;

