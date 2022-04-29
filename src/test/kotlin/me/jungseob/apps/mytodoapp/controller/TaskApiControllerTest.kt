package me.jungseob.apps.mytodoapp.controller

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlin.random.Random
import kotlinx.coroutines.runBlocking
import me.jungseob.apps.mytodoapp.controller.dto.PostTaskRequestDto
import me.jungseob.apps.mytodoapp.controller.dto.PutTaskRequestDto
import me.jungseob.apps.mytodoapp.service.TaskService
import me.jungseob.apps.mytodoapp.service.model.Task
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
        coEvery { taskService.getTask(any()) } returns task

        // when
        val id = randomNonNegativeLong()
        val actual = runBlocking {
            underTest.getTask(id)
        }

        // then
        assertThat(actual).isEqualTo(task)
        coVerify(exactly = 1) { taskService.getTask(id) }
        confirmVerified(taskService)
    }

    @Test
    fun `postTask() 호출 성공`() {
        // given
        val task = randomTask()
        coEvery { taskService.createTask(any(), any(), any(), any()) } returns task

        // when
        val postTaskRequestDto = PostTaskRequestDto(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            checked = Random.nextBoolean(),
            deadline = randomInstant(),
        )
        val actual = runBlocking {
            underTest.postTask(postTaskRequestDto)
        }

        // then
        assertThat(actual).isEqualTo(task)
        coVerify(exactly = 1) {
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
        coEvery { taskService.updateTask(any()) } returns task

        // when
        val id = randomNonNegativeLong()
        val putTaskRequestDto = PutTaskRequestDto(
            title = randomShortAlphanumeric(),
            memo = randomShortAlphanumeric(),
            checked = Random.nextBoolean(),
            deadline = randomInstant(),
        )
        runBlocking {
            underTest.modifyTask(id, putTaskRequestDto)
        }

        // then
        val expectedTask = Task(
            id = id,
            title = putTaskRequestDto.title,
            memo = putTaskRequestDto.memo,
            checked = putTaskRequestDto.checked,
            deadline = putTaskRequestDto.deadline,
        )
        coVerify(exactly = 1) {
            taskService.updateTask(expectedTask)
        }
        confirmVerified(taskService)
    }

    @Test
    fun `deleteTask() 호출 성공`() {
        // given
        coJustRun { taskService.deleteTask(any()) }

        // when
        val id = randomNonNegativeLong()
        runBlocking { underTest.deleteTask(id) }

        // then
        coVerify(exactly = 1) { taskService.deleteTask(id) }
        confirmVerified(taskService)
    }

    @Test
    fun `listTask() 호출 성공`() {
        // given
        val tasks = generateSequence { randomTask() }
            .take(Random.nextInt(0, 10))
            .toList()
        coEvery { taskService.list() } returns tasks

        // when
        val actual = runBlocking {
            underTest.listTask()
        }

        // then
        assertThat(actual).isEqualTo(tasks)
        coVerify(exactly = 1) { taskService.list() }
        confirmVerified(taskService)
    }
}
