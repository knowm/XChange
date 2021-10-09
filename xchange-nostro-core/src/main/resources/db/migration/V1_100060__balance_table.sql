CREATE TABLE balance$ (
  id BIGSERIAL PRIMARY KEY,
  asset TEXT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  zero BOOLEAN NOT NULL DEFAULT FALSE,
  document JSONB NOT NULL 
);

COMMENT ON COLUMN balance$.asset IS 'Unique asset, i.e. currency';
COMMENT ON COLUMN balance$.timestamp IS 'Exchange timestamp of balance update';
COMMENT ON COLUMN balance$.zero IS 'When TRUE, balance is not included into update subscription';
COMMENT ON COLUMN balance$.document IS 'JSON serialized balance object';

CREATE INDEX ON balance$(asset, timestamp);