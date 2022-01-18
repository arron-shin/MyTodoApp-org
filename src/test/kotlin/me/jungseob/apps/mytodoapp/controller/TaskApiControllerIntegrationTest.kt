package me.jungseob.apps.mytodoapp.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.justRun
import io.mockk.verify
import java.time.LocalDateTime
import java.util.Optional
import kotlin.random.Random
import me.jungseob.apps.mytodoapp.controller.dto.PostTaskRequestDto
import me.jungseob.apps.mytodoapp.controller.dto.PutTaskRequestDto
import me.jungseob.apps.mytodoapp.repository.TaskRepository
import me.jungseob.apps.mytodoapp.repository.entity.TaskEntity
import me.jungseob.apps.mytodoapp.repository.entity.toModel
import me.jungseob.apps.mytodoapp.util.randomNonNegativeLong
import me.jungseob.apps.mytodoapp.util.randomShortAlphanumeric
import me.jungseob.apps.mytodoapp.util.randomTaskEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskApiControllerIntegrationTest {

    @Autowired
    private lateinit var underTest: TaskApiController

    @MockkBean
    private lateinit var taskRepository: TaskRepository

    @Test
    fun `getTask() 호출 성공`() {
        // given
        val taskEntity = randomTaskEntity()
        every { taskRepository.findById(any()) } returns Optional.of(taskEntity)

        // when
        val id = randomNonNegativeLong()
        val actual = underTest.getTask(id)

        // then
        assertThat(actual).isEqualTo(taskEntity.toModel())
        verify(exactly = 1) { taskRepository.findById(id) }
        confirmVerified(taskRepository)
    }

    @Test
    fun `postTask 호출 성공`() {
        // given
        val postTaskRequestDto = PostTaskRequestDto(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            checked = Random.nextBoolean(),
            deadline = LocalDateTime.now(),
        )
        val taskEntity = randomTaskEntity()
        every { taskRepository.save(any()) } returns taskEntity

        // when
        val actual = underTest.postTask(postTaskRequestDto)

        // then
        assertThat(actual).isEqualTo(taskEntity.toModel())
        val expectedTaskEntity = TaskEntity(
            id = null,
            title = postTaskRequestDto.title,
            memo = postTaskRequestDto.memo,
            checked = postTaskRequestDto.checked,
            deadline = postTaskRequestDto.deadline,
        )
        verify(exactly = 1) { taskRepository.save(expectedTaskEntity) }
        confirmVerified(taskRepository)
    }

    @Test
    fun `modifyTask() 호출 성공`() {
        // given
        val putTaskRequestDto = PutTaskRequestDto(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            deadline = LocalDateTime.now(),
        )
        val taskEntity = randomTaskEntity()
        every { taskRepository.findById(any()) } returns Optional.of(taskEntity)
        val updatedTaskEntity = taskEntity.copy(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            deadline = LocalDateTime.now(),
        )
        every { taskRepository.save(any()) } returns updatedTaskEntity

        // when
        val id = randomNonNegativeLong()
        val actual = underTest.modifyTask(id, putTaskRequestDto)

        // then
        assertThat(actual).isEqualTo(updatedTaskEntity.toModel())
        verify(exactly = 1) { taskRepository.findById(id) }
        val expectedUpdatedTaskEntity = taskEntity.copy(
            title = putTaskRequestDto.title,
            memo = putTaskRequestDto.memo,
            deadline = putTaskRequestDto.deadline,
        )
        verify(exactly = 1) { taskRepository.save(expectedUpdatedTaskEntity) }
        confirmVerified(taskRepository)
    }

    @Test
    fun `deleteTask() 호출 성공`() {
        // given
        justRun { taskRepository.deleteById(any()) }

        // when
        val id = randomNonNegativeLong()
        underTest.deleteTask(id)

        // then
        verify(exactly = 1) { taskRepository.deleteById(id) }
        confirmVerified(taskRepository)
    }

    @Test
    fun `listTask() 호출 성공`() {
        // given
        val taskEntities = generateSequence { randomTaskEntity() }
            .distinctBy { it.id }
            .take(Random.nextInt(0, 10))
            .toList()
        every { taskRepository.findAll() } returns taskEntities

        // when
        val actual = underTest.listTask()

        // then
        val expectedTasks = taskEntities.map { it.toModel() }
        assertThat(actual).isEqualTo(expectedTasks)
    }
}
