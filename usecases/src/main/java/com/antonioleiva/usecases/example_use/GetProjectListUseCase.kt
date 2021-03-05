package com.antonioleiva.usecases.example_use

//class GetProjectListUseCase{
//
//}
import com.antonioleiva.domain.Project
import com.antonioleiva.domain.ProjectRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetProjectListUseCase @Inject constructor(private val projectRepository: ProjectRepository) :
    SingleUseCase<List<Project>, GetProjectListUseCase.Params>() {

    override fun buildUseCaseSingle(params: Params): Single<List<Project>> {
        return with(params) {
            projectRepository.getProjectList(page, perPage, query, category, time, colorHex)
        }
    }

    class Params(
        val page: Int,
        val perPage: Int,
        val query: String? = null,
        val category: String? = null,
        val time: String? = null,
        val colorHex: String? = null
    )
}

