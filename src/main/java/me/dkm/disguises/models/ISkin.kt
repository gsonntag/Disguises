package me.dkm.disguises.models

import com.mojang.authlib.properties.Property

interface ISkin {

    fun getData(): String
    fun getSignature(): String

    fun toProperty(): Property {
        return Property("textures", getData(), getSignature())
    }

}
