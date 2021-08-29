CREATE TABLE account (
  id SERIAL PRIMARY KEY,
  xchange TEXT NOT NULL,
  external_id TEXT NOT NULL
);

COMMENT ON COLUMN account.id IS 'Unique account identifier in the system';
COMMENT ON COLUMN account.xchange IS 'Exchange name, used to create table postfix';
COMMENT ON COLUMN account.external_id IS 'Short unique account identifier or user name, used to create table postfix';

CREATE UNIQUE INDEX ON account(xchange, external_id);