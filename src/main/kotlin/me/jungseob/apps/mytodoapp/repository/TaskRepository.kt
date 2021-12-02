package me.jungseob.apps.mytodoapp.repository

import me.jungseob.apps.mytodoapp.repository.entity.TaskEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : CrudRepository<TaskEntity, Long>
