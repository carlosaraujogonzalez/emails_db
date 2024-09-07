-- v1__create_gbtec_schema_and_emails_table.sql
DROP TABLE IF EXISTS `gbtec`.`emailscc`;
DROP TABLE IF EXISTS `gbtec`.`emailsto`;
DROP TABLE IF EXISTS `gbtec`.`emails`;
DROP TABLE IF EXISTS `gbtec`.`emailscc_seq`;
DROP TABLE IF EXISTS `gbtec`.`emailsto_seq`;

CREATE TABLE IF NOT EXISTS `gbtec`.`emails` (
  `emailid` INT NOT NULL,
  `emailFrom` VARCHAR(45) NULL,
  `emailBody` VARCHAR(45) NULL,
  `state` INT NULL,
  `lastUpdate` DATETIME NOT NULL,
  PRIMARY KEY (`emailid`));

CREATE TABLE IF NOT EXISTS `gbtec`.`emailscc` (
  `emailsccid` INT NOT NULL,
  `emailid` INT NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`emailsccid`),
  INDEX `fk_emailccid_idx` (`emailid` ASC) VISIBLE,
  CONSTRAINT `fk_emailccid`
    FOREIGN KEY (`emailid`)
    REFERENCES `gbtec`.`emails` (`emailid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `gbtec`.`emailsto` (
  `emailstoid` INT NOT NULL,
  `emailid` INT NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`emailstoid`),
  INDEX `fk_emailtoid_idx` (`emailid` ASC) VISIBLE,
  CONSTRAINT `fk_emailtoid`
    FOREIGN KEY (`emailid`)
    REFERENCES `gbtec`.`emails` (`emailid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE gbtec.emailscc_seq (
  `next_val` bigint(20) DEFAULT NULL);
INSERT INTO gbtec.emailscc_seq VALUES(0);

CREATE TABLE gbtec.emailsto_seq (
  `next_val` bigint(20) DEFAULT NULL
);
INSERT INTO gbtec.emailsto_seq VALUES(0);