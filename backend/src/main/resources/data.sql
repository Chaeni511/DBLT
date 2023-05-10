INSERT IGNORE INTO item
(item_id, category, code, price)
VALUES
    (1, 'bodies', 1, 50),
    (2, 'bodies', 2, 50),
    (3, 'bodies', 3, 50),
    (4, 'bodies', 4, 50),
    (5, 'bodies', 5, 50),
    (6, 'bodies', 6, 50),

    (7, 'body_parts', 1, 50),
    (8, 'body_parts', 2, 50),
    (9, 'body_parts', 3, 50),
    (10, 'body_parts', 4, 50),
    (11, 'body_parts', 5, 50),
    (12, 'body_parts', 6, 50),
    (13, 'body_parts', 7, 50),
    (14, 'body_parts', 8, 50),
    (15, 'body_parts', 9, 50),
    (16, 'body_parts', 10, 50),

    (17, 'eyes', 1, 50),
    (18, 'eyes', 2, 50),
    (19, 'eyes', 3, 50),
    (20, 'eyes', 4, 50),
    (21, 'eyes', 5, 50),
    (22, 'eyes', 6, 50),
    (23, 'eyes', 7, 50),
    (24, 'eyes', 8, 50),
    (25, 'eyes', 9, 50),
    (26, 'eyes', 10, 50),
    (27, 'eyes', 11, 50),

    (28, 'gloves', 1, 50),
    (29, 'gloves', 2, 50),
    (30, 'gloves', 3, 50),
    (31, 'gloves', 4, 50),
    (32, 'gloves', 5, 50),
    (33, 'gloves', 6, 50),
    (34, 'gloves', 7, 50),
    (35, 'gloves', 8, 50),
    (36, 'gloves', 9, 50),
    (37, 'gloves', 10, 50),

    (38, 'mouth_and_noses', 1, 50),
    (39, 'mouth_and_noses', 2, 50),
    (40, 'mouth_and_noses', 3, 50),
    (41, 'mouth_and_noses', 4, 50),
    (42, 'mouth_and_noses', 5, 50),
    (43, 'mouth_and_noses', 6, 50),
    (44, 'mouth_and_noses', 7, 50),
    (45, 'mouth_and_noses', 8, 50),
    (46, 'mouth_and_noses', 9, 50),
    (47, 'mouth_and_noses', 10, 50),
    (48, 'mouth_and_noses', 11, 50),
    (49, 'mouth_and_noses', 12, 50),
    (50, 'mouth_and_noses', 13, 50),
    (51, 'mouth_and_noses', 14, 50),
    (52, 'mouth_and_noses', 15, 50),

    (53, 'tails', 1, 50),
    (54, 'tails', 2, 50),
    (55, 'tails', 3, 50),
    (56, 'tails', 4, 50),
    (57, 'tails', 5, 50),
    (58, 'tails', 6, 50),
    (59, 'tails', 7, 50),
    (60, 'tails', 8, 50);

--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     category = new_item.category;
--     code = new_item.code;
--     price = new_item.price;


--
-- INSERT INTO skin
-- (skin_id, skin_price)
-- VALUES
--     (1, 50),
--     (2, 50),
--     (3, 50),
--     (4, 50),
--     (5, 50),
--     (6, 50)
--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     skin_price = new_item.skin_price;
--
--
-- INSERT INTO body
-- (body_id, body_price)
-- VALUES
--     (1, 50),
--     (2, 50),
--     (3, 50),
--     (4, 50),
--     (5, 50),
--     (6, 50),
--     (7, 50),
--     (8, 50),
--     (9, 50),
--     (10, 50)
--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     body_price = new_item.body_price;
--
--
-- INSERT INTO eye
-- (eye_id, eye_price)
-- VALUES
--     (1, 50),
--     (2, 50),
--     (3, 50),
--     (4, 50),
--     (5, 50),
--     (6, 50),
--     (7, 50),
--     (8, 50),
--     (9, 50),
--     (10, 50),
--     (11, 50)
--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     eye_price = new_item.eye_price;
--
--
-- INSERT INTO gloves
-- (gloves_id, gloves_price)
-- VALUES
--     (1, 50),
--     (2, 50),
--     (3, 50),
--     (4, 50),
--     (5, 50),
--     (6, 50),
--     (7, 50),
--     (8, 50),
--     (9, 50),
--     (10, 50)
--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     gloves_price = new_item.gloves_price;
--
--
-- INSERT INTO mouth_and_nose
-- (mouth_and_nose_id, mouth_and_nose_price)
-- VALUES
--     (1, 50),
--     (2, 50),
--     (3, 50),
--     (4, 50),
--     (5, 50),
--     (6, 50),
--     (7, 50),
--     (8, 50),
--     (9, 50),
--     (10, 50),
--     (11, 50),
--     (12, 50),
--     (13, 50),
--     (14, 50),
--     (15, 50)
--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     mouth_and_nose_price = new_item.mouth_and_nose_price;
--
--
-- INSERT INTO tail
-- (tail_id, tail_price)
-- VALUES
--     (1, 50),
--     (2, 50),
--     (3, 50),
--     (4, 50),
--     (5, 50),
--     (6, 50),
--     (7, 50),
--     (8, 50)
--     AS new_item
-- ON DUPLICATE KEY
-- UPDATE
--     tail_price = new_item.tail_price;

-- INSERT INTO character
-- (character_id, account, skin, body, gloves, eye, mouth_and_nose, tail)
-- VALUES
--     (1, 1, 1, 1, 1, 1, 1, 1)
