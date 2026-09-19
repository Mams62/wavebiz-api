CREATE TABLE businesses (
  id UUID PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  phone VARCHAR(20),
  currency CHAR(3) NOT NULL DEFAULT 'NGN',
  created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE products (
  id UUID PRIMARY KEY,
  business_id UUID NOT NULL REFERENCES businesses(id),
  name VARCHAR(160) NOT NULL,
  sku VARCHAR(80),
  selling_price NUMERIC(19,2) NOT NULL CHECK (selling_price >= 0),
  quantity NUMERIC(19,3) NOT NULL CHECK (quantity >= 0),
  low_stock_threshold NUMERIC(19,3) NOT NULL DEFAULT 0,
  version BIGINT NOT NULL DEFAULT 0,
  created_at TIMESTAMPTZ NOT NULL,
  CONSTRAINT uq_product_sku UNIQUE (business_id, sku)
);

CREATE TABLE sales (
  id UUID PRIMARY KEY,
  business_id UUID NOT NULL REFERENCES businesses(id),
  total_amount NUMERIC(19,2) NOT NULL CHECK (total_amount >= 0),
  payment_method VARCHAR(20) NOT NULL,
  occurred_at TIMESTAMPTZ NOT NULL,
  created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE sale_items (
  id UUID PRIMARY KEY,
  sale_id UUID NOT NULL REFERENCES sales(id),
  product_id UUID NOT NULL REFERENCES products(id),
  product_name VARCHAR(160) NOT NULL,
  quantity NUMERIC(19,3) NOT NULL CHECK (quantity > 0),
  unit_price NUMERIC(19,2) NOT NULL CHECK (unit_price >= 0),
  line_total NUMERIC(19,2) NOT NULL CHECK (line_total >= 0)
);

CREATE INDEX idx_products_business ON products(business_id);
CREATE INDEX idx_sales_business_occurred ON sales(business_id, occurred_at);

