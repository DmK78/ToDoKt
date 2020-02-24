package ru.job4j.todokt.db;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

public class TasksRepository {

    private AppDatabase appDatabase;
    private static TasksRepository mInstance;
    private Context context;
    private final String TAG = this.getClass().getSimpleName();


    private TasksRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }

    public static synchronized TasksRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TasksRepository(context);
        }
        return mInstance;
    }




    public void getAllTasks(final MutableLiveData<List<Task>> callback) {
        Disposable subscribe = appDatabase.taskDao().getAllTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Task>>() {
                    @Override
                    public void accept(List<Task> tasks) throws Exception {
                        callback.postValue(tasks);
                    }

                });
    }

    public void addTask(final Task task) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.taskDao().insertTask(task);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteAllTasks() {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.taskDao().deleteAllTasks();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "All Tasks deleted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteTask(final Task task) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.taskDao().deleteTask(task);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getTaskById(int id, final MutableLiveData<Task> callback) {
        appDatabase.taskDao().getTaskById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Task>() {
                    @Override
                    public void onSuccess(Task task) {
                        Toast.makeText(context, "Task id = " + task.getId(), Toast.LENGTH_SHORT).show();
                        callback.postValue(task);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateTask(final Task task) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.taskDao().updateTask(task);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Task updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
