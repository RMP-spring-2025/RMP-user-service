package org.healthapp.infrastructure.persistance

enum class Queries(val query: String) {

    SAVE_USER_CONSUMED_PRODUCT(
        "INSERT INTO user_products(user_id, product_id, mass_consumed, timestamp)\n" +
                "            VALUES (?, ?, ?, ?)"
    ),
    GET_CALORIES_FROM_TO(
        "SELECT product_id, timestamp, mass_consumed FROM user_products\n" +
                "                WHERE user_id = ? AND timestamp >= ? AND timestamp <= ?"
    ),
    ADD_USER(
        "INSERT INTO users(id, username, age, height) VALUES (?, ?, ?, ?)"
    ),
    ADD_USER_WEIGHT(
        "INSERT INTO user_weight(user_id, weight, timestamp) VALUES (?, ?, ?)"
    ),
    GET_USER_WEIGHT_FROM_TO(
        "SELECT user_id, weight, timestamp FROM user_weight\n" +
                "                WHERE user_id = ? AND timestamp >= ? AND timestamp <= ?"
    )
}