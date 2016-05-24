DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int(15) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username varchar(10) NOT NULL COMMENT '用户名',
  password varchar(50) NOT NULL COMMENT '密码',
  status int(2) NOT NULL DEFAULT 1 COMMENT '用户是否有效,0无效，1有效',
  #role varchar(6) DEFAULT 'VISITOR' COMMENT '用户权限角色,OWNER:车主, VISITOR:访客',
  nickname varchar(10) NOT NULL COMMENT '用户昵称',
  mobile int(11) NOT NULL COMMENT '用户手机',
  logo varchar(50) DEFAULT NULL COMMENT '用户头像',
  bind_device_id int(15) DEFAULT NULL COMMENT '绑定设备id',
  create_by_id int(15) DEFAULT NULL COMMENT '创建者',
  create_date DATE DEFAULT NULL COMMENT '创建时间',
  update_by_id int(15) DEFAULT NULL COMMENT '更新者',
  update_date DATE DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '用户表';

DROP TABLE IF EXISTS `devices`;
CREATE TABLE `devices` (
  `id` int(15) NOT NULL,
  `plate_no` varchar(10) DEFAULT NULL COMMENT '设备绑定车辆车牌号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8  COMMENT '设备表';



