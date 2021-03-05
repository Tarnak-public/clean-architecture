package com.antonioleiva.domain

import com.antonioleiva.domain.newer.User
import com.sun.org.glassfish.external.statistics.Stats
import java.awt.Image
import java.util.*

data class Project(
    val id: Long,
    val name: String,
    val date: Date,
    val url: String,
    val categoryList: List<String>,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val isMatureContent: Boolean,
    val description: String,
    val imageList: List<Image>,
    val ownerList: List<User>,
    val stats: Stats,
    val colorList: List<Int>
)