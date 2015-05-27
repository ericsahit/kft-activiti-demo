insert into ACT_ID_GROUP values ('admin', 1, '管理员', 'security-role');
insert into ACT_ID_GROUP values ('user', 1, '用户', 'security-role');
insert into ACT_ID_GROUP values ('deptLeader', 1, '部门领导', 'assignment');
insert into ACT_ID_GROUP values ('hr', 1, '人事', 'assignment');
insert into ACT_ID_GROUP values ('generalManager', 1, '总经理', 'assignment');

insert into ACT_ID_GROUP values ('kefu', 1, '客服', 'assignment');
insert into ACT_ID_GROUP values ('kufang', 1, '库房', 'assignment');
insert into ACT_ID_GROUP values ('driver', 1, '司机', 'assignment');

insert into ACT_ID_USER values ('kefu01', 1, '马国华', '', 'kefu01@jb.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('kefu01', 'kefu');

insert into ACT_ID_USER values ('kufang01', 1, '王海华', '', 'kufang01@jb.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('kufang01', 'kufang');

insert into ACT_ID_USER values ('driver01', 1, '张师傅', '', 'driver01@jb.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('driver01', 'driver');

insert into ACT_ID_USER values ('admin01', 1, '管理员', '', 'admin01@jb.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('admin01', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('admin01', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('admin01', 'driver');



insert into ACT_ID_USER values ('admin', 1, 'Admin', 'Kad', 'admin@kad.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('admin', 'user');
insert into ACT_ID_MEMBERSHIP values ('admin', 'admin');

insert into ACT_ID_USER values ('kafeitu', 1, 'Henry', 'Yan', 'yanhonglei@gmail.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('kafeitu', 'admin');

insert into ACT_ID_USER values ('hruser', 1, 'Lili', 'Zhang', 'hr@gmail.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('hruser', 'user');
insert into ACT_ID_MEMBERSHIP values ('hruser', 'hr');

insert into ACT_ID_USER values ('leaderuser', 1, 'Jhon', 'Li', 'leader@gmail.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('leaderuser', 'user');
insert into ACT_ID_MEMBERSHIP values ('leaderuser', 'deptLeader');

insert into ACT_ID_USER values ('generalManager', 1, 'Lone', 'Li', 'leader@gmail.com', '000000', '');
insert into ACT_ID_MEMBERSHIP values ('generalManager', 'generalManager');



insert into ACT_ID_USER values ('liujinghua', 1, '刘景华', '', 'kefu01@jb.com', '123456', '');
insert into ACT_ID_USER values ('wangpeng', 1, '王鹏', '', 'kefu01@jb.com', '123456', '');
insert into ACT_ID_USER values ('lvnaying', 1, '吕那英', '', 'kefu01@jb.com', '123456', '');
insert into ACT_ID_USER values ('lilingrui', 1, '李玲瑞', '', 'kefu01@jb.com', '123456', '');
insert into ACT_ID_USER values ('lilingxue', 1, '李凌雪', '', 'kefu01@jb.com', '123456', '');
insert into ACT_ID_USER values ('meiyu', 1, '梅玉', '', 'kefu01@jb.com', '123456', '');

insert into ACT_ID_MEMBERSHIP values ('liujinghua', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('wangpeng', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('lvnaying', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('lilingrui', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('lilingxue', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('meiyu', 'kefu');

insert into ACT_ID_USER values ('wanghan', 1, '王翰', '', 'driver01@jb.com', '123456', '');
insert into ACT_ID_USER values ('liujiong', 1, '刘炯', '', 'driver01@jb.com', '123456', '');

insert into ACT_ID_MEMBERSHIP values ('wanghan', 'driver');
insert into ACT_ID_MEMBERSHIP values ('liujiong', 'driver');



insert into ACT_ID_USER values ('xuhe', 1, '徐贺', '', 'kufang01@jb.com', '123456', '');
insert into ACT_ID_USER values ('xuziqiang', 1, '徐子强', '', 'kufang01@jb.com', '123456', '');
insert into ACT_ID_USER values ('zhenheng', 1, '振恒', '', 'kufang01@jb.com', '123456', '');
insert into ACT_ID_USER values ('laowang', 1, '老王', '', 'kufang01@jb.com', '123456', '');
insert into ACT_ID_USER values ('fengshuangyin', 1, '冯双银', '', 'kufang01@jb.com', '123456', '');

insert into ACT_ID_MEMBERSHIP values ('xuhe', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('xuziqiang', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('zhenheng', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('laowang', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('fengshuangyin', 'kufang');



insert into ACT_ID_USER values ('maguohua', 1, '马国华', '', 'admin01@jb.com', '123456', '');
insert into ACT_ID_USER values ('fengchen', 1, '冯琛', '', 'admin01@jb.com', '123456', '');
insert into ACT_ID_USER values ('fengxiaohui', 1, '冯晓辉', '', 'admin01@jb.com', '123456', '');
insert into ACT_ID_USER values ('zhaoying', 1, '赵影', '', 'admin01@jb.com', '123456', '');


insert into ACT_ID_MEMBERSHIP values ('maguohua', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('fengchen', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('fengxiaohui', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('zhaoying', 'kefu');
insert into ACT_ID_MEMBERSHIP values ('maguohua', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('fengchen', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('fengxiaohui', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('zhaoying', 'kufang');
insert into ACT_ID_MEMBERSHIP values ('maguohua', 'driver');
insert into ACT_ID_MEMBERSHIP values ('fengchen', 'driver');
insert into ACT_ID_MEMBERSHIP values ('fengxiaohui', 'driver');
insert into ACT_ID_MEMBERSHIP values ('zhaoying', 'driver');
insert into ACT_ID_MEMBERSHIP values ('maguohua', 'admin');
insert into ACT_ID_MEMBERSHIP values ('fengchen', 'admin');
insert into ACT_ID_MEMBERSHIP values ('fengxiaohui', 'admin');
insert into ACT_ID_MEMBERSHIP values ('zhaoying', 'admin');


update ACT_GE_PROPERTY
set VALUE_ = '12'
where NAME_ = 'next.dbid';