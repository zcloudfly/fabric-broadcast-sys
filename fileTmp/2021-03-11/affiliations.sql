
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for affiliations
-- ----------------------------
DROP TABLE IF EXISTS `affiliations`;
CREATE TABLE `affiliations`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prekey` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name_index`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for certificates
-- ----------------------------
DROP TABLE IF EXISTS `certificates`;
CREATE TABLE `certificates`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `serial_number` varbinary(128) NOT NULL,
  `authority_key_identifier` varbinary(128) NOT NULL,
  `ca_label` varbinary(128) NULL DEFAULT NULL,
  `status` varbinary(128) NOT NULL,
  `reason` int(11) NULL DEFAULT NULL,
  `expiry` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `revoked_at` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `pem` varbinary(4096) NOT NULL,
  `level` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`serial_number`, `authority_key_identifier`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for credentials
-- ----------------------------
DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `revocation_handle` varbinary(128) NOT NULL,
  `cred` varbinary(4096) NOT NULL,
  `ca_label` varbinary(128) NULL DEFAULT NULL,
  `status` varbinary(128) NOT NULL,
  `reason` int(11) NULL DEFAULT NULL,
  `expiry` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `revoked_at` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `level` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`revocation_handle`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for nonces
-- ----------------------------
DROP TABLE IF EXISTS `nonces`;
CREATE TABLE `nonces`  (
  `val` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `expiry` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `level` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`val`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties`  (
  `property` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `value` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`property`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for revocation_authority_info
-- ----------------------------
DROP TABLE IF EXISTS `revocation_authority_info`;
CREATE TABLE `revocation_authority_info`  (
  `epoch` int(11) NOT NULL DEFAULT 0,
  `next_handle` int(11) NULL DEFAULT NULL,
  `lasthandle_in_pool` int(11) NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`epoch`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `token` blob NULL,
  `type` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `affiliation` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `attributes` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `state` int(11) NULL DEFAULT NULL,
  `max_enrollments` int(11) NULL DEFAULT NULL,
  `level` int(11) NULL DEFAULT 0,
  `incorrect_password_attempts` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
