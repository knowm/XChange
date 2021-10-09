CREATE TABLE order_task$ (
  order_id TEXT NOT NULL,
  type TEXT NOT NULL,
  finished BOOLEAN NOT NULL DEFAULT FALSE,
  document JSONB NOT NULL,
  PRIMARY KEY(order_id, type) 
);

COMMENT ON COLUMN order_task$.order_id IS 'Unique order identifier';
COMMENT ON COLUMN order_task$.type IS 'Unique task type identifier';
COMMENT ON COLUMN order_task$.finished IS 'When TRUE task is in progress, FALSE for finished (done or cancelled) tasks';
COMMENT ON COLUMN order_task$.document IS 'JSON serialized sync_task object';

CREATE INDEX ON order_task$(finished, type);