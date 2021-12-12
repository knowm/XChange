CREATE TABLE funding$ (
  id BIGSERIAL PRIMARY KEY,
  external_id TEXT NOT NULL,
  type TEXT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  document JSONB NOT NULL
);

COMMENT ON COLUMN funding$.id IS 'Unique funding record identifier in the system';
COMMENT ON COLUMN funding$.external_id IS 'Funding record identifier in the exchange';
COMMENT ON COLUMN funding$.type IS 'Unique funding record type identifier';
COMMENT ON COLUMN funding$.timestamp IS 'Exchange timestamp of funding transaction';
COMMENT ON COLUMN funding$.document IS 'JSON serialized funding record object';

CREATE INDEX ON funding$(external_id);
CREATE INDEX ON funding$(type);
CREATE INDEX ON funding$(timestamp);


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
    EXECUTE 'CREATE TABLE balance$' || l_postfix || ' (LIKE balance$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE order$' || l_postfix || ' (LIKE order$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE trade$' || l_postfix || ' (LIKE trade$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE order_task$' || l_postfix || ' (LIKE order_task$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE sync_task$' || l_postfix || ' (LIKE sync_task$ INCLUDING DEFAULTS INCLUDING INDEXES)';
    EXECUTE 'CREATE TABLE funding$' || l_postfix || ' (LIKE funding$ INCLUDING DEFAULTS INCLUDING INDEXES)';
  END IF;
  COMMIT;
END; $$;


DO $$
DECLARE
  r RECORD;
BEGIN
  FOR r IN SELECT * FROM account
  LOOP
    EXECUTE 'CREATE TABLE funding$' || r.id || ' (LIKE funding$ INCLUDING DEFAULTS INCLUDING INDEXES)';
  END LOOP;
END; $$ LANGUAGE plpgsql;