package io.eugenethedev.taigamobile.repositories

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.eugenethedev.taigamobile.Session
import io.eugenethedev.taigamobile.dagger.DataModule
import io.eugenethedev.taigamobile.data.api.TaigaApi
import io.eugenethedev.taigamobile.manager.TaigaTestInstanceManager
import io.eugenethedev.taigamobile.manager.UserInfo
import io.eugenethedev.taigamobile.testdata.TestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import org.junit.runner.RunWith
import kotlin.test.AfterTest

@RunWith(AndroidJUnit4::class)
abstract class BaseRepositoryTest {
    lateinit var mockSession: Session
    lateinit var mockTaigaApi: TaigaApi
    lateinit var activeUser: UserInfo

    private val taigaManager = TaigaTestInstanceManager()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("Test thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        taigaManager.setup()
        activeUser = taigaManager.activeUser

        val dataModule = DataModule() // contains methods for API configuration

        mockSession = Session(ApplicationProvider.getApplicationContext()).also {
            it.server = taigaManager.baseUrl

            activeUser.data.apply {
                it.currentUserId = id
                it.token = accessToken
                it.refreshToken = refreshToken
            }

            activeUser.projects.entries.first().let { (id, project) ->
                it.currentProjectId = id
                it.currentProjectName = project.name
            }
        }
        mockTaigaApi = dataModule.provideTaigaApi(mockSession, dataModule.provideGson())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun cleanup() {
        taigaManager.clear()
        Dispatchers.resetMain()
    }
}
