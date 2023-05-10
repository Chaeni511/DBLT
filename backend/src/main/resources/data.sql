INSERT INTO skin
(skin_id, skin_price)
VALUES
    (1, 50),
    (2, 50),
    (3, 50),
    (4, 50),
    (5, 50),
    (6, 50)
    AS new_item
ON DUPLICATE KEY
UPDATE
    skin_price = new_item.skin_price;


INSERT INTO body
(body_id, body_price)
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
    body_price = new_item.body_price;


INSERT INTO eye
(eye_id, eye_price)
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
    eye_price = new_item.eye_price;


INSERT INTO gloves
(gloves_id, gloves_price)
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
    gloves_price = new_item.gloves_price;


INSERT INTO mouth_and_nose
(mouth_and_nose_id, mouth_and_nose_price)
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
    mouth_and_nose_price = new_item.mouth_and_nose_price;


INSERT INTO tail
(tail_id, tail_price)
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
    tail_price = new_item.tail_price;

-- INSERT INTO character
-- (character_id, account, skin, body, gloves, eye, mouth_and_nose, tail)
-- VALUES
--     (1, 1, 1, 1, 1, 1, 1, 1)
