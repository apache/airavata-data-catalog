-- TODO: move this to Liquibase or some other schema migration management tool
CREATE
OR REPLACE VIEW simple_data_product_sharing_view AS --
WITH recursive data_product_hierarchy(data_product_id, root) AS (
    SELECT
        -- include self with the root
        dp.data_product_id,
        dp.data_product_id AS root
    FROM
        data_product dp
    UNION
    ALL
    SELECT
        dp.data_product_id,
        h.root
    FROM
        data_product_hierarchy h,
        data_product dp
    WHERE
        dp.parent_data_product_id = h.data_product_id
)
SELECT
    dph.data_product_id AS data_product_id,
    su.user_id AS user_id,
    CASE
        WHEN s.permission_id = 'OWNER' THEN 0
        WHEN s.permission_id = 'READ' THEN 1
        WHEN s.permission_id = 'READ_METADATA' THEN 2
        WHEN s.permission_id = 'WRITE' THEN 3
        WHEN s.permission_id = 'WRITE_METADATA' THEN 4
        WHEN s.permission_id = 'MANAGE_SHARING' THEN 5
        ELSE NULL
    END AS permission_id
FROM
    simple_user_sharing s
    INNER JOIN simple_user su ON su.simple_user_id = s.simple_user_id
    INNER JOIN data_product_hierarchy dph ON dph.root = s.data_product_id
UNION
SELECT
    dph.data_product_id AS data_product_id,
    su.user_id AS user_id,
    CASE
        WHEN s.permission_id = 'OWNER' THEN 0
        WHEN s.permission_id = 'READ' THEN 1
        WHEN s.permission_id = 'READ_METADATA' THEN 2
        WHEN s.permission_id = 'WRITE' THEN 3
        WHEN s.permission_id = 'WRITE_METADATA' THEN 4
        WHEN s.permission_id = 'MANAGE_SHARING' THEN 5
        ELSE NULL
    END AS permission_id
FROM
    simple_group_sharing s
    INNER JOIN simple_group_membership gm ON gm.simple_group_id = s.simple_group_id
    INNER JOIN simple_user su ON su.simple_user_id = gm.simple_user_id
    INNER JOIN data_product_hierarchy dph ON dph.root = s.data_product_id;
