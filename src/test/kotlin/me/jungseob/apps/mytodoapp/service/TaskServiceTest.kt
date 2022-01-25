package me.jungseob.apps.mytodoapp.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import java.util.Optional
import kotlin.random.Random
import me.jungseob.apps.mytodoapp.exception.MyNotFoundException
import me.jungseob.apps.mytodoapp.repository.TaskRepository
import me.jungseob.apps.mytodoapp.repository.entity.toEntity
import me.jungseob.apps.mytodoapp.repository.entity.toModel
import me.jungseob.apps.mytodoapp.service.model.Task
import me.jungseob.apps.mytodoapp.util.randomInstant
import me.jungseob.apps.mytodoapp.util.randomNonNegativeLong
import me.jungseob.apps.mytodoapp.util.randomShortAlphanumeric
import me.jungseob.apps.mytodoapp.util.randomTaskEntity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TaskServiceTest {

    @InjectMockKs
    private lateinit var underTest: TaskService

    @MockK
    private lateinit var taskRepository: TaskRepository

    @Test
    fun `createTask() 호출 성공`() {
        // given`
        val taskEntity = randomTaskEntity()
        every { taskRepository.save(any()) } returns taskEntity

        // when
        val title = randomShortAlphanumeric()
        val memo = randomShortAlphanumeric()
        val checked = Random.nextBoolean()
        val deadline = randomInstant()
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
    fun `getTask() 호출 성공`() {
        // given`
        val taskEntity = randomTaskEntity()
        every { taskRepository.findById(any()) } returns Optional.of(taskEntity)

        // when
        val id = randomNonNegativeLong()
        val actual = underTest.getTask(
            id = id,
        )

        // then
        val expected = taskEntity.toModel()
        assertThat(actual).isEqualTo(expected)
        verify(exactly = 1) {
            taskRepository.findById(id)
        }
    }

    @Test
    fun `getTask()에 해당하는 엔티티가 없다면 MyNotFoundException() 예외를 던진다`() {
        // given`
        every { taskRepository.findById(any()) } returns Optional.empty()

        // when
        val id = randomNonNegativeLong()
        val actual = assertThatThrownBy {
            underTest.getTask(
                id = id,
            )
        }

        // then
        actual.isInstanceOf(MyNotFoundException::class.java)
        verify(exactly = 1) {
            taskRepository.findById(id)
        }
    }

    @Test
    fun `list() 호출 성공`() {
        // given
        // when
        // then
    }

    @Test
    fun `updateTask() 호출 성공`() {
        // given
        // when
        // then
    }

    @Test
    fun `deleteTask() 호출 성공`() {
        // given
        // when
        // then
    }
}
