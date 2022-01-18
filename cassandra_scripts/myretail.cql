CREATE KEYSPACE IF NOT EXISTS myretail WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE IF NOT EXISTS myretail.productprice (
   	productID bigint,
   	price float,
    currency text,
    PRIMARY KEY (productID)
);

INSERT INTO myretail.productprice (productID, price, currency) VALUES(13860428, 19.99, 'USD');
INSERT INTO myretail.productprice (productID, price, currency) VALUES(13264003, 79.99, 'CAD');