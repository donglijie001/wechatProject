# Host: localhost  (Version: 5.0.96-community-nt)
# Date: 2018-11-04 09:32:51
# Generator: MySQL-Front 5.3  (Build 4.13)

/*!40101 SET NAMES utf8 */;

#
# Source for table "friends"
#

DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `U_id` varchar(30) NOT NULL default '',
  `F_id` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`U_id`,`F_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "friends"
#

INSERT INTO `friends` VALUES ('dlj','fhj'),('dlj','zf'),('dlj','zl'),('dlj','zmq'),('fhj','dlj'),('fhj','zf'),('fhj','zl'),('fhj','zmq'),('qq','zf'),('qq','zl'),('zf','dlj'),('zf','fhj'),('zf','qq'),('zf','zl'),('zf','zmq'),('zl','dlj'),('zl','fhj'),('zl','zf'),('zl','zmq'),('zmq','dlj'),('zmq','fhj'),('zmq','zf'),('zmq','zl');

#
# Source for table "msg"
#

DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
  `U_id` varchar(30) NOT NULL default '',
  `F_id` varchar(255) NOT NULL default '',
  `M_id` int(11) NOT NULL auto_increment,
  `content` varchar(255) default NULL,
  PRIMARY KEY  (`M_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=latin1;

#
# Data for table "msg"
#

INSERT INTO `msg` VALUES ('zf','dlj',105,'how are you'),('zmq','zl',106,'Hello'),('zl','zf',107,'who are you'),('zf','zl',108,'zz'),('zl','zf',109,'how are you'),('zl','zmq',110,'yyy'),('zl','zf',111,'hohoho');

#
# Source for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `U_LoginID` varchar(30) NOT NULL default '',
  `U_NickName` varchar(30) default '',
  `U_PassWord` varchar(30) NOT NULL default '',
  `U_SignaTure` varchar(150) default NULL,
  `U_Sex` int(1) default NULL,
  `U_Birthday` datetime default NULL,
  `U_Telephone` varchar(30) default NULL,
  PRIMARY KEY  (`U_LoginID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "user"
#

INSERT INTO `user` VALUES ('dlj','master_yoda','qq','Named after the name of god,by your hands!',0,'1999-10-10 00:00:00',NULL),('fhj','tom','qq','Lazy boy',0,'1999-10-10 00:00:00',NULL),('qq','ww','qq','who are you',0,'1990-10-10 00:00:00','1111111'),('zf','zf','123','I am a girl',0,'1999-10-10 00:00:00',NULL),('zl','when i was a little boy','11','hello world',0,'1998-10-10 00:00:00',NULL),('zmq','zmq','qq','calm down',0,'1999-10-10 00:00:00',NULL);
