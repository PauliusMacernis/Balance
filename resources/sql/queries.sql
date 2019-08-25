-- :name create-record! :! :n
-- :doc creates a new income-outcome record
INSERT INTO records
(datetime_action, amount, datetime_created, datetime_updated)
VALUES (:datetime_action, :amount, :datetime_created, :datetime_updated)

-- :name get-records :? :*
-- :doc selects all available records
SELECT * FROM records ORDER BY id DESC

-- :name update-record! :! :n
-- :doc updates an existing income-outcome record
UPDATE records
SET datetime_action = :datetime_action, amount = :amount, datetime_updated = :datetime_updated
WHERE id = :id

-- :name get-record :? :1
-- :doc retrieves an income-outcome record given the id
SELECT * FROM records
WHERE id = :id

-- :name delete-record! :! :n
-- :doc deletes an income-outcome record given the id
DELETE FROM records
WHERE id = :id
