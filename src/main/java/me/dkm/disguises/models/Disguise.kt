package me.dkm.disguises.models

import java.util.*

data class Disguise
(
        val active: Boolean,
        val name: String,
        val realName: String,
        val id: UUID,
        val realId: UUID,
        val skin: ISkin,
        val realSkin: ISkin,
        val rank: Rank
)