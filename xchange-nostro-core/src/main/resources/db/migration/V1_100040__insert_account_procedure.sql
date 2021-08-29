CREATE OR REPLACE PROCEDURE insert_account(
  in_xchange TEXT,
  in_external_id TEXT,
  in_postfix TEXT
)
LANGUAGE plpgsql
AS $$
DECLARE
  l_exists INTEGER := 0;
BEGIN
  SELECT COUNT(*) INTO l_exists FROM account WHERE xchange = in_xchange AND external_id = in_external_id;
  IF l_exists = 0 THEN
    INSERT INTO account(xchange, external_id) VALUES(in_xchange, in_external_id);
    EXECUTE 'CREATE TABLE order$' || in_postfix || ' (LIKE order$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE trade$' || in_postfix || ' (LIKE trade$ INCLUDING DEFAULTS INCLUDING INDEXES)';
  END IF;
  COMMIT;
END;$$