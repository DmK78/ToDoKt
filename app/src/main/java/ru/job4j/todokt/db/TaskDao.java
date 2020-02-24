package ru.job4j.todokt.db;





import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;


/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

@Dao
public interface TaskDao {


    @Query("SELECT * FROM tasks")
    Flowable<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE id == :id")
    Single<Task> getTaskById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();









}
