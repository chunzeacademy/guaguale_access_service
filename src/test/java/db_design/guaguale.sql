SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS accountInfo;




/* Create Tables */

CREATE TABLE accountInfo
(
	userPayPaltform varchar(128),
	openid varchar(128),
	-- 买家账户，卖家账户
	accountType int COMMENT '买家账户，卖家账户',
	currency varchar(32),
	userAccountId bigint,
	accountStatus int,
	-- 保存有效期的字符串
	expireTimes varchar(128) COMMENT '保存有效期的字符串'
);



