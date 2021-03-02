package io.eugenethedev.taigamobile.domain.repositories

import io.eugenethedev.taigamobile.domain.entities.User

interface IUsersRepository {
    suspend fun getUser(userId: Long): User
}