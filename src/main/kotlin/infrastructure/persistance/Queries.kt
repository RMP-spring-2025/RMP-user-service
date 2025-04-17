package org.healthapp.infrastructure.persistance

enum class Queries(val query: String) {

    SAVE_USER_CONSUMED_PRODUCT("INSERT INTO user_products(user_id, product_id, mass_consumed, timestamp)\n" +
            "            VALUES (?, ?, ?, ?)"),
    GET_CALORIES_FROM_TO("SELECT product_id, timestamp, mass_consumed FROM user_products\n" +
            "                WHERE user_id = ? AND timestamp >= ? AND timestamp <= ?")
}