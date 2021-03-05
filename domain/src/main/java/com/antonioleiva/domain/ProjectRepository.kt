package com.antonioleiva.domain

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface ProjectRepository {

    fun getProjectList(
        page: Int,
        perPage: Int,
        query: String? = null,
        category: String? = null,
        time: String? = null,
        colorHex: String? = null
    ): Single<List<Project>>

    fun getProject(projectId: Long): Flowable<Project>
}