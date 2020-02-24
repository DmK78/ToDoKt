package ru.job4j.todokt.db

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flowable<List<Task?>?>?

    @Query("SELECT * FROM tasks WHERE id == :id")
    fun getTaskById(id: Int): Single<Task?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task?)

    @Delete
    fun deleteTask(task: Task?)

    @Update
    fun updateTask(task: Task?)

    @Query("DELETE FROM tasks")
    fun deleteAllTasks()
}