package me.jungseob.apps.mytodoapp.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import kotlin.random.Random
import me.jungseob.apps.mytodoapp.repository.TaskRepository
import me.jungseob.apps.mytodoapp.repository.entity.toEntity
import me.jungseob.apps.mytodoapp.repository.entity.toModel
import me.jungseob.apps.mytodoapp.service.model.Task
import me.jungseob.apps.mytodoapp.util.randomLocalDateTimeInYear2xxx
import me.jungseob.apps.mytodoapp.util.randomShortAlphanumeric
import me.jungseob.apps.mytodoapp.util.randomTaskEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TaskServiceTest {

    @InjectMockKs
    private lateinit var underTest: TaskService

    @MockK
    private lateinit var taskRepository: TaskRepository

    @Test
    fun createTask() {
        // given`
        val taskEntity = randomTaskEntity()
        every { taskRepository.save(any()) } returns taskEntity

        // when
        val title = randomShortAlphanumeric()
        val memo = randomShortAlphanumeric()
        val checked = Random.nextBoolean()
        val deadline = randomLocalDateTimeInYear2xxx()
        val actual = underTest.createTask(
            title = title,
            memo = memo,
            checked = checked,
            deadline = deadline,
        )

        // then
        assertThat(actual).isEqualTo(taskEntity.toModel())
        val expectedTaskEntity = Task(
            title = title,
            memo = memo,
            checked = checked,
            deadline = deadline,
        ).toEntity()
        verify(exactly = 1) {
            taskRepository.save(expectedTaskEntity)
        }
    }

    @Test
    fun getTask() {
        // given
        // when
        // then
    }

    @Test
    fun list() {
        // given
        // when
        // then
    }

    @Test
    fun updateTask() {
        // given
        // when
        // then
    }

    @Test
    fun deleteTask() {
        // given
        // when
        // then
    }
}
