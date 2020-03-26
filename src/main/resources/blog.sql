DROP DATABASE IF EXISTS blog;

-- 创建数据库

CREATE DATABASE blog;

-- 使用数据库
use blog

-- 创建用户表
CREATE TABLE `user_login`(
`user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
`username` varchar(16) 	NOT NULL UNIQUE COMMENT '用户账号',
`password` varchar(255) NOT NULL COMMENT '登录密码',
`user_phone` varchar(11) NOT NULL UNIQUE COMMENT '用户手机号',
`role` int NOT NULL DEFAULT 1 COMMENT '用户权限:1普通用户,0系统管理员',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录表';

-- 创建用户详情表
CREATE TABLE `user_detail`(
`user_id` int NOT NULL COMMENT '用户id',
`user_image`  varchar(128) NOT NULL COMMENT '用户头像',
`user_name` varchar(64) NOT NULL UNIQUE COMMENT '用户昵称',
`user_sex` char(3) DEFAULT NULL COMMENT '性别,默认是null',
`user_age` int DEFAULT NULL COMMENT '年龄,null',
`user_motto` varchar(128) DEFAULT NULL COMMENT '用户格言,默认null',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详情表';

-- 创建类别表
CREATE TABLE `category`(
`category_id` int NOT NULL AUTO_INCREMENT COMMENT '类别id',
`category_name` varchar(12) NOT NULL COMMENT '类别名称',
`category_num` int NOT NULL COMMENT '类别编号',
`big_type` int  NOT NULL COMMENT '大类型,0代表前端,1是后端',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(`category_id`),
UNIQUE KEY `uqe_category_num` (`category_num`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章类别表';


-- 创建文章表
CREATE TABLE `article`(
`article_id` int NOT NULL AUTO_INCREMENT COMMENT '文章id',
`user_id` int NOT NULL COMMENT '用户id',
`article_type` int NOT NULL COMMENT '文章类别,对应类别编号',
`big_type` int  NOT NULL COMMENT '大类型,0代表前端,1是后端',
`article_title` varchar(64) NOT NULL COMMENT '文章标题',
`article_content` varchar(10000) NOT NULL COMMENT '文章内容',
`article_star` int DEFAULT 0 COMMENT '文章点赞数',
`comment_count` int DEFAULT 0 COMMENT '评论数',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(`article_id`),
KEY idx_category_id(`article_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章表';

-- 创建评论表
CREATE TABLE `article_comment`(
`comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
`user_id` int NOT NULL COMMENT '评论者id',
`article_id` int NOT NULL COMMENT '文章id',
`comment_content` varchar(512) NOT NULL COMMENT '评论内容',
`comment_star` int DEFAULT 0 COMMENT '点赞数',
`comment_status` int DEFAULT 0 COMMENT '状态是否已读,0未读,1已读,2已回复',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(`comment_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

-- 创建评论回复表
CREATE TABLE `comment_reply`(
`reply_id` bigint NOT NULL AUTO_INCREMENT COMMENT '回复表主键',
`comment_id` int NOT NULL COMMENT '对应的评论id',
`to_user_id` int NOT NULL COMMENT '回复对象',
`replyer_id` int NOT NULL COMMENT '回复者id',
`reply_content` varchar(512) NOT NULL COMMENT '评论内容',
`reply_star` int DEFAULT 0 COMMENT '回复点赞数',
`reply_status` int DEFAULT 0 COMMENT '状态是否已读,0未读,1已读,2已回复',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(`reply_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论回复表';

CREATE TABLE `user_star`(
`star_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户点赞表主键',
`user_id` int NOT NULL COMMENT '点赞的用户Id',
`obj_id` int DEFAULT 0 COMMENT '点赞的对象Id',
`star_type` int DEFAULT 0 COMMENT '点赞类型,1.文章,2.文章评论,3是回复',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(`star_id`)  
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户点赞表';


-- 插入user_login数据 密码是123
insert into user_login(username,password,user_phone)
values
("111","a4d3f50aa4387facf53e3472f5260589","13922077569"),
("222","a4d3f50aa4387facf53e3472f5260589","13924897691"),
("333","a4d3f50aa4387facf53e3472f5260589","16922077691"),
("444","a4d3f50aa4387facf53e3472f5260589","16984077691");

-- 插入user_detail数据
insert into user_detail(user_id,user_image,user_name,user_sex,user_age,user_motto)
values
(1,"/image/default.png","oneGe","男","18","我是一哥"),
(2,"/image/default.png","twoDog","女","19","我是二狗子"),
(3,"/image/default.png","threeSha","女","20","我是三傻"),
(4,"/image/default.png","fourWa","男","21","我是四娃");

-- 插入category数据
insert into category(category_num,category_name,big_type)
values
("001","Java",1),
("002","HTML",0),
("003","SQL",1),
("004","JavaScript",0),
("005","c语言",1),
("006","算法",1),
("007","Python",1),
("008","NoSQL",1);

-- 插入article数据
insert into article(user_id,article_type,article_title,article_content,big_type)
values
(1,001,"Java第一天","啊,java真香!",1),
(1,002,"HTML第一天","诶,HTML也不错",0),
(2,002,"HTML心得","学习HTML的心得分享..",0),
(3,003,"MySql第一天","mysql开源免费,...",1),
(4,004,"JavaScript第一天","JavaScript功能强大,...",0);

-- article_comment表插入数据
insert into article_comment(user_id,article_id,comment_content)
values
(3,1,"不错,写的什么玩意,成功浪费我几分钟"),
(2,1,"很好啊"),
(1,1,"不错,写的什么玩意,成功浪费我几分钟"),
(2,2,"很好啊"),
(1,2,"不错,写的什么玩意,成功浪费我几分钟"),
(2,3,"很好啊");

-- comment_reply表插入数据
insert into comment_reply(comment_id,to_user_id,replyer_id,reply_content)
values
(1,3,1,"哈哈"),
(1,1,3,"呵呵"),
(1,3,1,"傻子"),
(1,1,3,"你才是傻子"),
(1,3,1,"牛逼，咋不上天"),
(1,3,1,"哈哈");

-- user_star表插入数据
insert into user_star(user_id,obj_id,star_type)
values
(1,1,1),
(1,1,3),
(1,3,1),
(1,1,3),
(1,3,1),
(1,3,1);
