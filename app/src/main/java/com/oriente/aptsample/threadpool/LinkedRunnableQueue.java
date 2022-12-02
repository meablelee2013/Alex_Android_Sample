package com.oriente.aptsample.threadpool;

import com.oriente.aptsample.threadpool.ThreadPool.ThreadPool;
import com.oriente.aptsample.threadpool.delypolicy.DenyPolicy;
import com.oriente.aptsample.threadpool.queue.RunnableQueue;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedRunnableQueue implements RunnableQueue {
    //存放任务的队列
    private LinkedList<Runnable> mLinkedList;
    //任务队列的最大容量
    private int mLimit;
    private DenyPolicy mDenyPolicy;
    private ThreadPool threadPool;
//    private ReentrantLock lock = new ReentrantLock();

    public LinkedRunnableQueue(int mLimit, DenyPolicy mDenyPolicy) {
        this.mLimit = mLimit;
        this.mDenyPolicy = mDenyPolicy;
        mLinkedList = new LinkedList<>();
    }

    //入队
    @Override
    public void offer(Runnable runnable) {
        synchronized (mLinkedList) {
            if (size() < mLimit) {
                mLinkedList.add(runnable);
                mLinkedList.notifyAll();
            } else {
                mDenyPolicy.reject(runnable);
            }
        }
    }

    //出队
    @Override
    public Runnable poll() {
        synchronized (mLinkedList) {
            while (mLinkedList == null) {
                try {
                    mLinkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return mLinkedList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (mLinkedList) {
            return mLinkedList.size();
        }
    }
}
