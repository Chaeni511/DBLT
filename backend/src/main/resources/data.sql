INSERT INTO skins
(skin_id, price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50)
    AS new_skin
ON DUPLICATE KEY
UPDATE
    price = new_item.price;


INSERT INTO bodies
(body_id, price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50),
    (7, 50),
    (8, 50),
    (9, 50),
    (10, 50)
    AS new_item
ON DUPLICATE KEY
UPDATE
    price = new_item.price;


INSERT INTO eyes
(eye_id, price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50),
    (7, 50),
    (8, 50),
    (9, 50),
    (10, 50),
    (11, 50)
    AS new_item
ON DUPLICATE KEY
UPDATE
    price = new_item.price;


INSERT INTO gloves
(glove_id, price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50),
    (7, 50),
    (8, 50),
    (9, 50),
    (10, 50)
    AS new_item
ON DUPLICATE KEY
UPDATE
    price = new_item.price;


INSERT INTO mouth_and_noses
(mouth_and_nose_id, price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50),
    (7, 50),
    (8, 50),
    (9, 50),
    (10, 50),
    (11, 50),
    (12, 50),
    (13, 50),
    (14, 50),
    (15, 50)
    AS new_item
ON DUPLICATE KEY
UPDATE
    price = new_item.price;


INSERT INTO tails
(glove_id, price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50),
    (7, 50),
    (8, 50)
    AS new_item
ON DUPLICATE KEY
UPDATE
    price = new_item.price;