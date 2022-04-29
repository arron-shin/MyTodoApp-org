package me.jungseob.apps.mytodoapp.repository

import kotlinx.coroutines.reactor.awaitSingle
import me.jungseob.apps.mytodoapp.repository.entity.TaskEntity
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.allAndAwait
import org.springframework.data.r2dbc.core.awaitOne
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Component

@Component
class TaskR2dbcRepository(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate
) {

    suspend fun findById(id: Long): TaskEntity? {
        val query = Query.query(Criteria.where("id").`is`(id))
        return r2dbcEntityTemplate.select(TaskEntity::class.java)
            .matching(query)
            .awaitOne()
    }

    suspend fun findAll(): List<TaskEntity> {
        return r2dbcEntityTemplate.select(TaskEntity::class.java)
            .all()
            .collectList()
            .awaitSingle()
    }

    suspend fun insert(taskEntity: TaskEntity): TaskEntity {
        return r2dbcEntityTemplate.insert(taskEntity)
            .awaitSingle()
    }

    suspend fun update(taskEntity: TaskEntity): Int {
        val query = Query.query(Criteria.where("id").`is`(taskEntity.id!!))
        return r2dbcEntityTemplate.update(TaskEntity::class.java)
            .matching(query)
            .apply(
                Update.update("title", taskEntity.title)
                    .set("memo", taskEntity.memo)
                    .set("checked", taskEntity.checked)
                    .set("deadline", taskEntity.deadline)
            )
            .awaitSingle()
    }

    suspend fun deleteById(id: Long): Int {
        val query = Query.query(Criteria.where("id").`is`(id))
        return r2dbcEntityTemplate.delete(TaskEntity::class.java)
            .matching(query)
            .allAndAwait()
    }
}
