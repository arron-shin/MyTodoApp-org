//package me.jungseob.apps.mytodoapp.repository
//
//import java.io.File
//import javax.annotation.PreDestroy
//import me.jungseob.apps.mytodoapp.util.randomTaskEntity
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.test.context.ActiveProfiles
//import org.testcontainers.containers.DockerComposeContainer
//import org.testcontainers.junit.jupiter.Container
//import org.testcontainers.junit.jupiter.Testcontainers
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Testcontainers
//@ActiveProfiles("test")
//class TaskRepositoryIntegrationTest(
//    @Autowired private val taskRepository: TaskRepository
//) {
//
//    companion object {
//        @JvmStatic
//        @Container
//        val testDockerContainer = DockerComposeContainer<Nothing>(File("docker-compose.yml")).apply {
//            withExposedService("mysql", 3306)
//        }
//    }
//
//    @PreDestroy
//    fun preDestroy() {
//        testDockerContainer.stop()
//    }
//
//    @Test
//    fun `save() 성공`() {
//        // given
//        val task = randomTaskEntity(id = null)
//
//        // when
//        val actual = taskRepository.save(task)
//
//        // then
//        assertThat(actual).isNotNull
//        assertThat(actual.id).isNotNull
//    }
//}
