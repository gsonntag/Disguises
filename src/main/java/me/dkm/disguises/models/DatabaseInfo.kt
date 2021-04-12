package me.dkm.disguises.models

data class DatabaseInfo(
    val host: String,
    val database: String,
    val port: Int,
    val username: String,
    val password: String,
) {

    fun isValid(): Boolean {
        return host.isNotBlank() && database.isNotBlank() && username.isNotBlank() && password.isNotBlank()
    }
}
