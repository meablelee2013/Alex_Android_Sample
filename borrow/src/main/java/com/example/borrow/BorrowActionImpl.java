package com.example.borrow;

import androidx.annotation.NonNull;

import com.example.common.BaseAction;
import com.example.common.BorrowAction;
import com.google.auto.service.AutoService;

@AutoService(BaseAction.class)
public class BorrowActionImpl implements BorrowAction {
    @Override
    public void borrow() {
        System.out.println("BorrowActionImpl.borrow");
    }

    @Override
    public void verifyBorrow() {
        System.out.println("BorrowActionImpl.verifyBorrow");
    }

    @NonNull
    @Override
    public String name() {
        return "borrowAction";
    }
}
