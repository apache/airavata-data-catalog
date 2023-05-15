-- TODO: get this into a database migration script
CREATE
OR REPLACE VIEW custos_data_product_sharing_view AS
SELECT
    dp.data_product_id AS data_product_id,
    u.user_id AS user_id,
    CASE
        WHEN s.permission_type_id = concat('OWNER', '@', s.tenant_id) THEN 0
        WHEN s.permission_type_id = concat('READ', '@', s.tenant_id) THEN 1
        WHEN s.permission_type_id = concat('READ_METADATA', '@', s.tenant_id) THEN 2
        WHEN s.permission_type_id = concat('WRITE', '@', s.tenant_id) THEN 3
        WHEN s.permission_type_id = concat('WRITE_METADATA', '@', s.tenant_id) THEN 4
        WHEN s.permission_type_id = concat('MANAGE_SHARING', '@', s.tenant_id) THEN 5
        ELSE NULL
    END AS permission_id
FROM
    sharing s
    INNER JOIN tenant t ON t.external_id = s.tenant_id
    INNER JOIN user_table u ON u.external_id = s.associating_id
    AND u.tenant_id = t.tenant_id
    INNER JOIN data_product dp ON concat(dp.external_id, '@', t.external_id) = s.entity_id
WHERE
    -- TODO: add group support
    s.associating_id_type = 'user';
