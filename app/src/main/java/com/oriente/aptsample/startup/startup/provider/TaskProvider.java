package com.oriente.aptsample.startup.startup.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.oriente.aptsample.startup.startup.AbsTask;
import com.oriente.aptsample.startup.startup.manage.TaskManager;

import java.util.List;

public class TaskProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        try {
            List<AbsTask<?>> startups = TaskInitializer
                    .discoverAndInitializ(getContext(), getClass().getName());
            new TaskManager.Builder()
                    .addAllTask(startups)
                    .build(getContext())
                    .start()
                    .await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}