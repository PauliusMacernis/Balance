CREATE TABLE records
(
 id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 datetime_action DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 datetime_created DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 datetime_updated DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
 amount INT(11) NOT NULL,
 PRIMARY KEY (id)
)
COMMENT='Main table storing records of income and outcome. Values are stored in the smallest value of the default currency, e.g. euro cents'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;