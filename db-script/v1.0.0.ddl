DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(15) NOT NULL COMMENT '用户名',
  password varchar(50) NOT NULL COMMENT '密码',
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  plate_no varchar(10) DEFAULT NULL COMMENT '用户车辆车牌号',
  nickname varchar(15) NOT NULL COMMENT '用户昵称',
  mobile BIGINT(12) NOT NULL COMMENT '用户手机',
  logo varchar(50) DEFAULT NULL COMMENT '用户头像',
  bind_device_id int(15) DEFAULT NULL COMMENT '绑定设备id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间',
  INDEX users_username (username)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '用户表';

DROP TABLE IF EXISTS `devices`;
CREATE TABLE `devices` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '设备表';


DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE `user_friends` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  user_id int(15) NOT NULL COMMENT '用户id',
  friend_id int(15) NOT NULL COMMENT '好友id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间',
  INDEX user_friends_user_id (user_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '用户好友表';


DROP TABLE IF EXISTS `user_groups`;
CREATE TABLE `user_groups` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  user_id int(15) NOT NULL COMMENT '群主用户id',
  name varchar(15) NOT NULL COMMENT '群名称',
  descipt varchar(15) NOT NULL COMMENT '群描述',
  tag_id int(15)  NOT NULL COMMENT '群标签id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间',
  INDEX user_groups_user_id (user_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '群组表';

DROP TABLE IF EXISTS `user_group_tags`;
CREATE TABLE `user_group_tags` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  name varchar(15) NOT NULL COMMENT '群标签'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '群标签表';

DROP TABLE IF EXISTS `user_group_map`;
CREATE TABLE `user_group_map` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  group_id int(15) NOT NULL COMMENT '群组id',
  user_id int(15) NOT NULL COMMENT '成员用户id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间',
  INDEX user_group_map_group_id (group_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '群组成员表';


DROP TABLE IF EXISTS `user_verification_code`;
CREATE TABLE `user_verification_code` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  user_id int(15) NOT NULL COMMENT '用户id',
  code_type int(2) NOT NULL COMMENT '验证码类型：1 注册, 2 找回密码',
  verification_code int(15) NOT NULL COMMENT '验证码',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间',
  INDEX user_group_map_group_id (user_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '用户验证码表';
