create DATABASE gensoft;

USE gensoft;

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
  bind_device_id BIGINT(15) DEFAULT NULL COMMENT '绑定设备id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATETIME DEFAULT NULL COMMENT '更新时间',
  INDEX users_username (username)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '用户表';

DROP TABLE IF EXISTS `devices`;
CREATE TABLE `devices` (
  id BIGINT NOT NULL PRIMARY KEY COMMENT 'IMEI',
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATETIME DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '设备表';


DROP TABLE IF EXISTS `user_friends`;
CREATE TABLE `user_friends` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  status int(2) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效，1有效',
  user_id int(15) NOT NULL COMMENT '用户id',
  friend_id int(15) NOT NULL COMMENT '好友id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATETIME DEFAULT NULL COMMENT '更新时间',
  INDEX user_friends_user_id (user_id),
  INDEX user_friends_user_friend_id (user_id, friend_id)
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
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATETIME DEFAULT NULL COMMENT '更新时间',
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
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATETIME DEFAULT NULL COMMENT '更新时间',
  INDEX user_group_map_group_id (group_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '群组成员表';


DROP TABLE IF EXISTS `user_verification_code`;
CREATE TABLE `user_verification_code` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  mobile BIGINT(12) NOT NULL COMMENT '用户手机',
  code_type int(2) NOT NULL COMMENT '验证码类型：1 注册, 2 找回密码',
  verification_code varchar(10) NOT NULL COMMENT '验证码',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  update_date DATETIME DEFAULT NULL COMMENT '更新时间',
  INDEX verification_code_mobile_and_type (mobile, code_type)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '用户手机验证码表';

DROP TABLE IF EXISTS `user_chat`;
CREATE TABLE `user_chat` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  is_voice int(1) NOT NULL COMMENT '是否语音聊天,0文字聊天,1语音聊天',
  message varchar(1024) NOT NULL COMMENT '聊天内容（语音聊天则为语音文件地址）',
  sender_id int(15) NOT NULL COMMENT '发送者id',
  receiver_id int(15) NOT NULL COMMENT '接收者id',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  INDEX user_chat_sender_id (sender_id),
  INDEX user_chat_receiver_id (receiver_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '成员聊天表';

DROP TABLE IF EXISTS `group_chat`;
CREATE TABLE `group_chat` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  is_voice int(1) NOT NULL COMMENT '是否语音聊天,0文字聊天,1语音聊天',
  message varchar(1024) NOT NULL COMMENT '聊天内容（语音聊天则为语音文件地址）',
  sender_id int(15) NOT NULL COMMENT '发送者id',
  group_id int(15) NOT NULL COMMENT '群组id',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  INDEX group_chat_group_id (group_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '组聊天表';

DROP TABLE IF EXISTS `last_chat_record`;
CREATE TABLE `last_chat_record` (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  is_voice int(1) NOT NULL COMMENT '是否语音聊天,0文字聊天,1语音聊天',
  is_group int(1) NOT NULL COMMENT '是否组聊天表,0否,1是',
  message varchar(1024) NOT NULL COMMENT '聊天内容（语音聊天则为语音文件地址）',
  user_id int(15) NOT NULL COMMENT '用户id',
  chat_id int(15) NOT NULL COMMENT '聊天者id',
  create_date DATETIME DEFAULT NULL COMMENT '创建时间',
  INDEX last_chat_record_user_id (user_id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '最新聊天记录表';