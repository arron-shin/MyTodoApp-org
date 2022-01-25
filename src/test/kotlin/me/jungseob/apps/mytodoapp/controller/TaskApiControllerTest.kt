package me.jungseob.apps.mytodoapp.controller

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import kotlin.random.Random
import me.jungseob.apps.mytodoapp.controller.dto.PostTaskRequestDto
import me.jungseob.apps.mytodoapp.controller.dto.PutTaskRequestDto
import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.util.randomInstant
import me.jungseob.apps.mytodoapp.util.randomNonNegativeLong
import me.jungseob.apps.mytodoapp.util.randomShortAlphanumeric
import me.jungseob.apps.mytodoapp.util.randomTask
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TaskApiControllerTest {

    @InjectMockKs
    private lateinit var underTest: TaskApiController

    @MockK
    private lateinit var taskService: TaskService

    @Test
    fun `getTask() 호출 성공`() {
        // given
        val task = randomTask()
        every { taskService.getTask(any()) } returns task

        // when
        val id = randomNonNegativeLong()
        val actual = underTest.getTask(id)

        // then
        assertThat(actual).isEqualTo(task)
        verify(exactly = 1) { taskService.getTask(id) }
        confirmVerified(taskService)
    }

    @Test
    fun `postTask() 호출 성공`() {
        // given
        val task = randomTask()
        every { taskService.createTask(any(), any(), any(), any()) } returns task

        // when
        val postTaskRequestDto = PostTaskRequestDto(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            checked = Random.nextBoolean(),
            deadline = randomInstant(),
        )
        val actual = underTest.postTask(postTaskRequestDto)

        // then
        assertThat(actual).isEqualTo(task)
        verify(exactly = 1) {
            taskService.createTask(
                postTaskRequestDto.title,
                postTaskRequestDto.memo,
                postTaskRequestDto.checked,
                postTaskRequestDto.deadline,
            )
        }
        confirmVerified(taskService)
    }

    @Test
    fun `modifyTask() 호출 성공`() {
        // given
        val task = randomTask()
        every { taskService.updateTask(any(), any(), any(), any()) } returns task

        // when
        val id = randomNonNegativeLong()
        val putTaskRequestDto = PutTaskRequestDto(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            deadline = randomInstant(),
        )
        underTest.modifyTask(id, putTaskRequestDto)

        // then
        verify(exactly = 1) {
            taskService.updateTask(
                id,
                putTaskRequestDto.title,
                putTaskRequestDto.memo,
                putTaskRequestDto.deadline
            )
        }
        confirmVerified(taskService)
    }

    @Test
    fun `deleteTask() 호출 성공`() {
        // given
        justRun { taskService.deleteTask(any()) }

        // when
        val id = randomNonNegativeLong()
        underTest.deleteTask(id)

        // then
        verify(exactly = 1) { taskService.deleteTask(id) }
        confirmVerified(taskService)
    }

    @Test
    fun `listTask() 호출 성공`() {
        // given
        val tasks = generateSequence { randomTask() }
            .take(Random.nextInt(0, 10))
            .toList()
        every { taskService.list() } returns tasks

        // when
        val actual = underTest.listTask()

        // then
        assertThat(actual).isEqualTo(tasks)
        verify(exactly = 1) { taskService.list() }
        confirmVerified(taskService)
    }
}
