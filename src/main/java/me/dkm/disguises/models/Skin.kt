package me.dkm.disguises.models

class Skin(private val dAta: String, private val sIgnature: String): ISkin {
    override fun getData(): String {
        return dAta
    }

    override fun getSignature(): String {
        return sIgnature
    }
}
