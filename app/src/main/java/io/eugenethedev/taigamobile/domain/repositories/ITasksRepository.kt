package io.eugenethedev.taigamobile.domain.repositories

import io.eugenethedev.taigamobile.domain.entities.*

interface ITasksRepository {
    suspend fun getStatuses(commonTaskType: CommonTaskType): List<Status>
    suspend fun getEpics(page: Int, query: String? = null): List<CommonTask>
    suspend fun getUserStories(statusId: Long, page: Int, sprintId: Long? = null): List<CommonTask>
    suspend fun getEpicUserStories(epicId: Long): List<CommonTask>
    suspend fun getSprints(page: Int): List<Sprint>
    suspend fun getUserStoryTasks(storyId: Long): List<CommonTask>
    suspend fun getSprintTasks(sprintId: Long, page: Int): List<CommonTask>
    suspend fun getCommonTask(commonTaskId: Long, type: CommonTaskType): CommonTaskExtended
    suspend fun getComments(commonTaskId: Long, type: CommonTaskType): List<Comment>


    // edit related
    suspend fun changeStatus(commonTaskId: Long, commonTaskType: CommonTaskType, statusId: Long, version: Int)
    suspend fun changeSprint(commonTaskId: Long, commonTaskType: CommonTaskType, sprintId: Long?, version: Int)
    suspend fun linkToEpic(epicId: Long, userStoryId: Long)
    suspend fun unlinkFromEpic(epicId: Long, userStoryId: Long)
    suspend fun changeAssignees(commonTaskId: Long, commonTaskType: CommonTaskType, assignees: List<Long>, version: Int)
    suspend fun changeWatchers(commonTaskId: Long, commonTaskType: CommonTaskType, watchers: List<Long>, version: Int)
    suspend fun createComment(commonTaskId: Long, commonTaskType: CommonTaskType, comment: String, version: Int)
    suspend fun deleteComment(commonTaskId: Long, commonTaskType: CommonTaskType, commentId: String)
    suspend fun editTask(commonTaskId: Long, commonTaskType: CommonTaskType, title: String, description: String, version: Int)

    suspend fun createCommonTask(
        commonTaskType: CommonTaskType,
        title: String,
        description: String,
        parentId: Long? = null,
        sprintId: Long? = null
    ): CommonTask

    suspend fun deleteCommonTask(commonTaskType: CommonTaskType, commonTaskId: Long)
}