CREATE TABLE sync_task$ (
  symbol TEXT NOT NULL,
  timestamp TIMESTAMP NOT NULL,
  document JSONB NOT NULL,
  PRIMARY KEY(symbol, timestamp) 
);

COMMENT ON COLUMN sync_task$.symbol IS 'Unique symbol identifier (instrument for trading; currency for wallet) on which the data is synchronized';
COMMENT ON COLUMN sync_task$.timestamp IS 'Exchange timestamp by which the data is synchronized';
COMMENT ON COLUMN sync_task$.document IS 'JSON serialized sync_task object';
