package com.antonioleiva.data.repository

import com.antonioleiva.data.newer.entity.UserEntity
import com.antonioleiva.domain.Project
import com.antonioleiva.domain.ProjectRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProjectRepositoryImpl @Inject constructor(
    private val database: AppDatabase,
    restClient: RestClient
) :
    ProjectRepository {

    private val projectDao = database.projectDao()
    private val userDao = database.userDao()
    private val projectUserJoinDao = database.projectUserJoinDao()
    private val projectService = restClient.retrofit.create(ProjectService::class.java)

    override fun getProjectList(
        page: Int,
        perPage: Int,
        query: String?,
        category: String?,
        time: String?,
        colorHex: String?
    ): Single<List<Project>> {
        return projectService.getProjectList(page, perPage, query, category, time, colorHex)
            .map { response ->
                val data = response.mapToEntityList()
                val projectEntityList = mutableListOf<ProjectEntity>()
                val userEntityList = mutableListOf<UserEntity>()
                val projectUserJoinEntityList = mutableListOf<ProjectUserJoinEntity>()
                data.forEach { project ->
                    projectEntityList.add(ProjectAdapter.toStorage(project))
                    val userEntitySubList = project.ownerList.map { UserAdapter.toStorage(it) }
                    userEntityList.addAll(userEntitySubList)
                    val projectUserJoinEntitySubList =
                        userEntitySubList.map { ProjectUserJoinEntity(project.id, it.id) }
                    projectUserJoinEntityList.addAll(projectUserJoinEntitySubList)
                }
                database.runInTransaction {
                    projectDao.saveProjectList(projectEntityList)
                    userDao.saveUserList(userEntityList)
                    projectUserJoinDao.saveProjectUserJoinList(projectUserJoinEntityList)
                }
                data
            }
    }

    override fun getProject(projectId: Long): Flowable<Project> {
        val local = Flowable.combineLatest(
            projectDao.getProject(projectId),
            projectUserJoinDao.getUserListForProject(projectId),
            BiFunction<ProjectEntity, List<UserEntity>, Project> { projectEntity, userEntityList ->
                ProjectAdapter.fromStorage(projectEntity, userEntityList)
            }
        )
            .distinctUntilChanged { oldData, newData -> oldData.id == newData.id }
        val remote = projectService.getProjectById(projectId)
            .map {
                val project = it.mapToEntity()
                projectDao.saveProject(ProjectAdapter.toStorage(project))
                project
            }
            .toFlowable()
        return Flowable.merge(local, remote)
            .distinctUntilChanged()
    }
}