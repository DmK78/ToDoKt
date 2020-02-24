package ru.job4j.todokt.db

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */
class TasksRepository private constructor(private val context: Context) {
    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)
    private val TAG = this.javaClass.simpleName

    companion object {
        private var mInstance: TasksRepository? = null
        @Synchronized
        fun getInstance(context: Context): TasksRepository? {
            if (mInstance == null) {
                mInstance = TasksRepository(context)
            }
            return mInstance
        }
    }

    fun getAllTasks(callback: MutableLiveData<List<Task?>?>) {
        val subscribe = appDatabase.taskDao().getAllTasks()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { tasks -> callback.postValue(tasks) }
    }

    fun addTask(task: Task?) {
        Completable.fromAction { appDatabase.taskDao().insertTask(task) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, e.message)
                }
            })
    }

    fun deleteAllTasks() {
        Completable.fromAction { appDatabase.taskDao().deleteAllTasks() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Toast.makeText(context, "All Tasks deleted", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, e.message)
                }
            })
    }

    fun deleteTask(task: Task?) {
        Completable.fromAction { appDatabase.taskDao().deleteTask(task) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, e.message)
                }
            })
    }

    fun getTaskById(id: Int, callback: MutableLiveData<Task?>) {
        appDatabase.taskDao().getTaskById(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableSingleObserver<Task?>() {
                override fun onSuccess(task: Task) {
                    Toast.makeText(context, "Task id = " + task.id, Toast.LENGTH_SHORT).show()
                    callback.postValue(task)
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, e.message)
                }
            })
    }

    fun updateTask(task: Task?) {
        Completable.fromAction { appDatabase.taskDao().updateTask(task) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {}
                override fun onComplete() {
                    Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, e.message)
                }
            })
    }


}