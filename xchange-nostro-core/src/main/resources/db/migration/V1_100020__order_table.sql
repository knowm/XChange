CREATE TABLE order$ (
  id TEXT PRIMARY KEY,
  external_id TEXT NOT NULL,
  instrument TEXT NOT NULL,
  terminal BOOLEAN NOT NULL DEFAULT FALSE,
  created TIMESTAMP NOT NULL DEFAULT NOW(),
  updated TIMESTAMP NOT NULL DEFAULT NOW(),
  document JSONB NOT NULL
);

COMMENT ON COLUMN order$.id IS 'Unique order identifier in the system, used when submitting an order as user_reference';
COMMENT ON COLUMN order$.external_id IS 'Order identifier in the exchange';
COMMENT ON COLUMN order$.instrument IS 'Currency pair or derivative';
COMMENT ON COLUMN order$.terminal IS 'When TRUE order is in terminal (final) state, i.e. will not be changed';
COMMENT ON COLUMN order$.created IS 'Exchange timestamp of order record creation';
COMMENT ON COLUMN order$.updated IS 'Exchange timestamp of last order status update';
COMMENT ON COLUMN order$.document IS 'JSON serialized order object';

CREATE INDEX ON order$(external_id);
CREATE INDEX ON order$(terminal, instrument);
