CREATE OR REPLACE PROCEDURE insert_account(
  in_xchange TEXT,
  in_external_id TEXT
)
LANGUAGE plpgsql
AS $$
DECLARE
  l_exists INTEGER := 0;
  l_postfix INTEGER := NULL;
BEGIN
  SELECT COUNT(*) INTO l_exists FROM account WHERE xchange = in_xchange AND external_id = in_external_id;
  IF l_exists = 0 THEN
    INSERT INTO account(xchange, external_id) VALUES(in_xchange, in_external_id) RETURNING id INTO l_postfix;
    EXECUTE 'CREATE TABLE order$' || l_postfix || ' (LIKE order$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE trade$' || l_postfix || ' (LIKE trade$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE sync_task$' || l_postfix || ' (LIKE sync_task$ INCLUDING DEFAULTS INCLUDING INDEXES)';
  END IF;
  COMMIT;
END;$$
