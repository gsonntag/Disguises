package me.dkm.disguises.models

import java.lang.IllegalArgumentException

enum class Rank(val prefix: String) {

    VIP("&a[VIP] "),
    VIP_PLUS("&a[VIP&6+&a] "),
    MVP("&b[MVP] "),
    MVP_PLUS("&b[MVP&c+&b] "),
    MVP_PLUS_PLUS("&6[MVP&c++&6] "),
    HELPER("&9[HELPER] "),
    MOD("&2[MOD] "),
    ADMIN("&c[ADMIN] "),
    DEFAULT("&7"),
    YOUTUBE("&c[&fYOUTUBE&c] ");

    companion object {
        fun of(str: String): Rank? {
            return try {
                valueOf(str)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
        fun fromPrefix(str: String): Rank? {
            for(v in values()) {
                if(v.prefix == str)
                    return v
            }
            return null
        }
    }
}