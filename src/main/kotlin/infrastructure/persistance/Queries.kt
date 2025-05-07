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
        "INSERT INTO users(id, username, age, height, goal) VALUES (?, ?, ?, ?, ?)"
    ),
    ADD_USER_WEIGHT(
        "INSERT INTO user_weight(user_id, weight, timestamp) VALUES (?, ?, ?)"
    ),
    GET_USER_WEIGHT_FROM_TO(
        "SELECT user_id, weight, timestamp FROM user_weight\n" +
                "                WHERE user_id = ? AND timestamp >= ? AND timestamp <= ?"
    ),

    GET_USER_STATISTIC(
        "SELECT \n" +
                "    u.username,\n" +
                "    uw.weight,\n" +
                "    u.height,\n" +
                "    u.age,\n" +
                "    u.goal\n" +
                "FROM \n" +
                "    users u\n" +
                "LEFT JOIN (\n" +
                "    SELECT \n" +
                "        user_id,\n" +
                "        weight,\n" +
                "        timestamp,\n" +
                "        ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY timestamp DESC) as rn\n" +
                "    FROM \n" +
                "        user_weight\n" +
                ") uw ON u.id = uw.user_id AND uw.rn = 1\n" +
                "WHERE \n" +
                "    u.id = ?;"
    )
}