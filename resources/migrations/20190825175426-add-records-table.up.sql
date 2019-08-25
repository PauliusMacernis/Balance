CREATE TABLE records
(
 id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 datetime_action DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 datetime_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 datetime_updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
 amount INT NOT NULL,
 PRIMARY KEY (id)
)
COMMENT='Main table storing records of income and outcome. Values are stored in the smallest value of the default currency, e.g. euro cents'
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1
;