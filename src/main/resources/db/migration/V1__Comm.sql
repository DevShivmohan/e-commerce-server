CREATE TABLE customer (
  id INT NOT NULL,
   name VARCHAR(255) NULL,
   email VARCHAR(255) NULL,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);



CREATE TABLE customer_orders (
  customer_id INT NOT NULL,
   orders_id INT NOT NULL
);

CREATE TABLE order_table (
  id INT NOT NULL,
   product_id INT NOT NULL,
   qty INT NOT NULL,
   amount DOUBLE NOT NULL,
   CONSTRAINT pk_order_table PRIMARY KEY (id)
);


CREATE TABLE product (
  id INT NOT NULL,
   name VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   price DOUBLE NOT NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE shopping_kart (
  id INT NOT NULL,
   product_id INT NOT NULL,
   qty INT NOT NULL,
   amount DOUBLE NOT NULL,
   CONSTRAINT pk_shopping_kart PRIMARY KEY (id)
);

CREATE TABLE customer_shopping_kart (
  customer_id INT NOT NULL,
   shopping_kart_id INT NOT NULL
);


ALTER TABLE customer ADD CONSTRAINT uc_customer_email UNIQUE (email);

ALTER TABLE customer_orders ADD CONSTRAINT fk_cusord_on_customer FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer_orders ADD CONSTRAINT fk_cusord_on_order FOREIGN KEY (orders_id) REFERENCES order_table (id);

ALTER TABLE customer_shopping_kart ADD CONSTRAINT fk_cusshokar_on_customer FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer_shopping_kart ADD CONSTRAINT fk_cusshokar_on_shopping_kart FOREIGN KEY (shopping_kart_id) REFERENCES shopping_kart (id);

