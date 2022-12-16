package com.example.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.BaseAction;
import com.example.common.UserAction;
import com.google.auto.service.AutoService;

@AutoService(BaseAction.class)
public class UserActionImpl implements UserAction {
    @Nullable
    @Override
    public String getUserName() {
        System.out.println("UserActionImpl.getUserName");
        return null;
    }

    @Override
    public void setUserName(@NonNull String userName) {
        System.out.println("UserActionImpl.setUserName");
    }

    @NonNull
    @Override
    public String name() {
        return "userAction";
    }
}
