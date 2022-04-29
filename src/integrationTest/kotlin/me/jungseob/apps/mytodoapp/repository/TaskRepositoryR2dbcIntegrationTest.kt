package me.jungseob.apps.mytodoapp.repository

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import java.io.File
import javax.annotation.PreDestroy
import kotlinx.coroutines.runBlocking
import me.jungseob.apps.mytodoapp.repository.entity.TaskEntity
import me.jungseob.apps.mytodoapp.util.randomTaskEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class TaskRepositoryR2dbcIntegrationTest {

    private lateinit var underTest: TaskR2dbcRepository

    companion object {
        @JvmStatic
        @Container
        val testDockerContainer = DockerComposeContainer<Nothing>(File("docker-compose.yml")).apply {
            withExposedService("mysql", 3306)
        }
    }

    @BeforeEach
    fun setup() {
        val r2dbcEntityTemplate = R2dbcEntityTemplate(
            ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                    .option(ConnectionFactoryOptions.PROTOCOL, "r2dbc")
                    .option(ConnectionFactoryOptions.DRIVER, "mysql")
                    .option(ConnectionFactoryOptions.HOST, "127.0.0.1")
                    .option(ConnectionFactoryOptions.USER, "my-user")
                    .option(ConnectionFactoryOptions.PORT, 3306)
                    .option(ConnectionFactoryOptions.PASSWORD, "my-user-pwd")
                    .option(ConnectionFactoryOptions.DATABASE, "my_db")
                    .build()
            )
        )
        underTest = TaskR2dbcRepository(
            r2dbcEntityTemplate = r2dbcEntityTemplate,
        )
        r2dbcEntityTemplate.delete(TaskEntity::class.java).all().block()
    }

    @PreDestroy
    fun preDestroy() {
        testDockerContainer.stop()
    }

    @Test
    fun `save() 성공`() {
        // given
        val task = randomTaskEntity(id = null)

        // when
        val actual = runBlocking {
            underTest.insert(task)
        }

        // then
        assertThat(actual).isNotNull
        assertThat(actual.id).isNotNull
    }
}
