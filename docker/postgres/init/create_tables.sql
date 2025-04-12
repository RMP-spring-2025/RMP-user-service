create table if not exists users(
    id SERIAL PRIMARY KEY,
    username varchar(50) UNIQUE NOT NULL,
    age integer NOT NULL,
    height DECIMAL(5,2),
    weight DECIMAL(5, 2)
    check ( age > 0 and height > 0 and weight > 0 )
);

create table if not exists user_products(
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    product_id INTEGER NOT NULL,
    mass_consumed INTEGER NOT NULL,
    timestamp date NOT NULL,
    check ( mass_consumed > 0 )

);
