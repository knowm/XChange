CREATE TABLE trade$ (
  id BIGSERIAL PRIMARY KEY,
  order_id TEXT NOT NULL,
  external_id TEXT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  document JSONB NOT NULL
);

COMMENT ON COLUMN trade$.id IS 'Unique trade identifier in the system';
COMMENT ON COLUMN trade$.order_id IS 'Unique order identifier';
COMMENT ON COLUMN trade$.external_id IS 'Trade identifier in the exchange';
COMMENT ON COLUMN trade$.timestamp IS 'Exchange timestamp of trade execution';
COMMENT ON COLUMN trade$.document IS 'JSON serialized trade object';

CREATE INDEX ON trade$(order_id);
CREATE INDEX ON trade$(external_id);
CREATE INDEX ON trade$(timestamp);
