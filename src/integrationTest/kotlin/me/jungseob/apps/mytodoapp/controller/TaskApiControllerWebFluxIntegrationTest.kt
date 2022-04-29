package me.jungseob.apps.mytodoapp.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import kotlinx.coroutines.runBlocking
import me.jungseob.apps.mytodoapp.repository.TaskR2dbcRepository
import me.jungseob.apps.mytodoapp.repository.entity.toModel
import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.service.model.Task
import me.jungseob.apps.mytodoapp.util.randomNonNegativeLong
import me.jungseob.apps.mytodoapp.util.randomTaskEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest(TaskApiController::class)
@Import(TaskService::class, TaskR2dbcRepository::class)
class TaskApiControllerWebFluxIntegrationTest(
    @Autowired val webTestClient: WebTestClient
) {

    @MockkBean
    private lateinit var taskR2dbcRepository: TaskR2dbcRepository

    @Test
    fun `getTask() HTTP API 호출 성공`() {
        // given
        val taskEntity = randomTaskEntity()
        coEvery { taskR2dbcRepository.findById(any()) } returns taskEntity

        // when
        val id = randomNonNegativeLong()
        val actual = runBlocking {
            webTestClient.get()
                .uri("/api/v1/tasks/$id")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Task::class.java)
                .responseBody
                .blockFirst()
        }

        // then
        assertThat(actual).isEqualTo(taskEntity.toModel())
        coVerify(exactly = 1) { taskR2dbcRepository.findById(id) }
        confirmVerified(taskR2dbcRepository)
    }

//    @Test
//    fun `postTask() HTTP API 호출 성공`() {
//        // given
//        val postTaskRequestDto = PostTaskRequestDto(
//            title = randomShortAlphanumeric(),
//            memo = randomShortAlphanumeric(),
//            checked = Random.nextBoolean(),
//            deadline = randomInstant(),
//        )
//        val taskEntity = randomTaskEntity()
//        every { taskRepository.save(any()) } returns taskEntity
//
//        // when
//        val actual = mockMvc.post("/api/v1/tasks") {
//            contentType = MediaType.APPLICATION_JSON
//            accept = MediaType.APPLICATION_JSON
//            content = jacksonObjectMapper.writeValueAsString(postTaskRequestDto)
//        }.andExpect {
//            status { isOk() }
//            content { contentType(MediaType.APPLICATION_JSON) }
//        }.andReturn()
//
//        // then
//        val actualToModel = jacksonObjectMapper.readValue(actual.response.contentAsString, Task::class.java)
//        assertThat(actualToModel).isEqualTo(taskEntity.toModel())
//        val expectedTaskEntity = TaskEntity(
//            id = null,
//            title = postTaskRequestDto.title,
//            memo = postTaskRequestDto.memo,
//            checked = postTaskRequestDto.checked,
//            deadline = postTaskRequestDto.deadline,
//        )
//        verify(exactly = 1) { taskRepository.save(expectedTaskEntity) }
//        confirmVerified(taskRepository)
//    }
//
//    @Test
//    fun `modifyTask() HTTP API 호출 성공`() {
//        // given
//        val putTaskRequestDto = PutTaskRequestDto(
//            title = randomShortAlphanumeric(),
//            memo = randomShortAlphanumeric(),
//            deadline = randomInstant(),
//        )
//        val taskEntity = randomTaskEntity()
//        every { taskRepository.findById(any()) } returns Optional.of(taskEntity)
//        val updatedTaskEntity = taskEntity.copy(
//            title = randomShortAlphanumeric(),
//            memo = randomShortAlphanumeric(),
//            deadline = randomInstant(),
//        )
//        every { taskRepository.save(any()) } returns updatedTaskEntity
//
//        // when
//        val id = randomNonNegativeLong()
//        val actual = mockMvc.put("/api/v1/tasks/$id") {
//            contentType = MediaType.APPLICATION_JSON
//            accept = MediaType.APPLICATION_JSON
//            content = jacksonObjectMapper.writeValueAsString(putTaskRequestDto)
//        }.andExpect {
//            status { isOk() }
//            content { contentType(MediaType.APPLICATION_JSON) }
//        }.andReturn()
//
//
//        // then
//        val actualToModel = jacksonObjectMapper.readValue(actual.response.contentAsString, Task::class.java)
//        assertThat(actualToModel).isEqualTo(updatedTaskEntity.toModel())
//        verify(exactly = 1) { taskRepository.findById(id) }
//        val expectedUpdatedTaskEntity = taskEntity.copy(
//            title = putTaskRequestDto.title,
//            memo = putTaskRequestDto.memo,
//            deadline = putTaskRequestDto.deadline,
//        )
//        verify(exactly = 1) { taskRepository.save(expectedUpdatedTaskEntity) }
//        confirmVerified(taskRepository)
//    }
//
//    @Test
//    fun `deleteTask() HTTP API 호출 성공`() {
//        // given
//        justRun { taskRepository.deleteById(any()) }
//
//        // when
//        val id = randomNonNegativeLong()
//        mockMvc.delete("/api/v1/tasks/$id") {
//            contentType = MediaType.APPLICATION_JSON
//            accept = MediaType.APPLICATION_JSON
//        }.andExpect {
//            status { isOk() }
//        }.andReturn()
//
//
//        // then
//        verify(exactly = 1) { taskRepository.deleteById(id) }
//        confirmVerified(taskRepository)
//    }
//
//    @Test
//    fun `listTask() HTTP API 호출 성공`() {
//        // given
//        val taskEntities = generateSequence { randomTaskEntity() }
//            .distinctBy { it.id }
//            .take(Random.nextInt(0, 10))
//            .toList()
//        every { taskRepository.findAll() } returns taskEntities
//
//        // when
//        val actual = mockMvc.get("/api/v1/tasks") {
//            contentType = MediaType.APPLICATION_JSON
//            accept = MediaType.APPLICATION_JSON
//        }.andExpect {
//            status { isOk() }
//            content { contentType(MediaType.APPLICATION_JSON) }
//        }.andReturn()
//
//        // then
//        val actualToModel = jacksonObjectMapper.readValue(
//            actual.response.contentAsString,
//            object : TypeReference<List<Task>>() {}
//        )
//        val expectedTasks = taskEntities.map { it.toModel() }
//        assertThat(actualToModel).isEqualTo(expectedTasks)
//    }
}
