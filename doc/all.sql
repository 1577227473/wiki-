select * from ebook;

#电子书表
create table ebook
(
    `id`           bigint not null comment 'id',
    `name`         varchar(50) comment '名称',
    `category1_id` bigint comment '分类1',
    `category2_id` bigint comment '分类2',
    `description`  varchar(200) comment '描述',
    `cover`        varchar(200) comment '封面',
    `doc_count`    int comment '文档数',
    `view_count`   int comment '阅读数',
    `vote_count`   int comment '点赞数',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment ='电子书';

insert into `ebook` (id, name,  description) VALUES (1,'Spring Boot 入门教程','零基础入门Java开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name,  description) VALUES (2,'Vue 入门教程','零基础入门Vue开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name,  description) VALUES (3,'Python 入门教程','零基础入门Python开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name,  description) VALUES (4,'Mysql 入门教程','零基础入门Mysql开发，企业级应用开发最佳首选框架');
insert into `ebook` (id, name,  description) VALUES (5,'Oracle 入门教程','零基础入门Oracle开发，企业级应用开发最佳首选框架');


#分类
create table `category`(
    `id` bigint not null comment 'id',
    `parent` bigint not null default 0 comment '父类',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
)engine =innodb default charset = utf8mb4 comment = '分类';

insert into `category` (id, parent, name, sort) values (100,000,'前端开发',100);
insert into `category` (id, parent, name, sort) values (101,100,'Vue',101);
insert into `category` (id, parent, name, sort) values (102,100,'HTML & CSS',102);
insert into `category` (id, parent, name, sort) values (200,000,'Java',200);
insert into `category` (id, parent, name, sort) values (201,200,'基础应用',201);
insert into `category` (id, parent, name, sort) values (202,200,'框架应用',202);
insert into `category` (id, parent, name, sort) values (300,000,'Python',300);
insert into `category` (id, parent, name, sort) values (301,300,'基础应用',301);
insert into `category` (id, parent, name, sort) values (302,300,'进阶方向应用',302);
insert into `category` (id, parent, name, sort) values (400,000,'数据库',400);
insert into `category` (id, parent, name, sort) values (401,400,'MySQL',401);
insert into `category` (id, parent, name, sort) values (500,000,'其他',500);
insert into `category` (id, parent, name, sort) values (501,500,'服务器',501);
insert into `category` (id, parent, name, sort) values (502,500,'开发工具',502);
insert into `category` (id, parent, name, sort) values (503,500,'热门服务端语言',503);

#文档表
create table `doc`(
    `id` bigint not null comment 'id',
    `ebook_id` bigint not null default 0 comment '电子书id',
    `parent` bigint not null default 0 comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    `view_count` int default 0 comment '阅读数',
    `vote_count` int default 0 comment '点赞数',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment ='文档';

insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (1,1,0,'文档1',1,0,0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (2,1,1,'文档1.1',1,0,0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (3,1,0,'文档2',2,0,0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (4,1,3,'文档2.1',1,0,0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (5,1,3,'文档2.2',2,0,0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (6,1,5,'文档2.2.1',1,0,0);

#文档内容
create table `content`(
    `id` bigint not null  comment '文档id',
    `content` mediumtext not null comment '内容',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment ='文档内容';


#用户表
create table `user` (
    `id` bigint not null comment 'ID',
    `login_name` varchar(50) not null comment '登录名',
    `name` varchar(50) comment '昵称',
    `password` char(32) not null comment '密码',
    primary key (`id`),
    unique key `login_name_unique` (`login_name`)
) engine=innodb default charset=utf8mb4 comment ='用户';

insert into `user`(id, login_name, name, password) values (1,'test','测试','test');

update doc set view_count = view_count+1 where id=1;


#按电子书分组统计文档数据，并更新到对应的电子书中
update ebook t1,(
    select ebook_id,count(1) doc_count,sum(view_count) view_count,sum(vote_count) vote_count
    from doc
    group by ebook_id) t2
set t1.doc_count = t2.doc_count, t1.view_count=t2.view_count, t1.vote_count=t2.vote_count
where t1.id=t2.ebook_id;
