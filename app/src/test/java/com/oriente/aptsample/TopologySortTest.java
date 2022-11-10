package com.oriente.aptsample;

import android.util.Log;

import com.oriente.aptsample.sort.StartupSortStore;
import com.oriente.aptsample.sort.TopologySort;
import com.oriente.aptsample.task.Task1;
import com.oriente.aptsample.task.Task2;
import com.oriente.aptsample.task.Task3;
import com.oriente.aptsample.task.Task4;
import com.oriente.aptsample.task.Task5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TopologySortTest {

    @Test
    public void testTopologySort() {
        List<Startup<?>> list = new ArrayList<>();
        list.add(new Task4());
        list.add(new Task5());
        list.add(new Task3());
        list.add(new Task2());
        list.add(new Task1());
        StartupSortStore startupSortStore = TopologySort.sort(list);
        List<Startup<?>> result = startupSortStore.getResult();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n===============================================================\n");
        stringBuilder.append(" Task Graph:\n");
        for (Startup<?> startup : result) {
            stringBuilder.append("    ").append(startup.getClass().getName()).append("\n");

        }
        stringBuilder.append("\n===============================================================\n");
        System.out.println("alex----------" + stringBuilder.toString());


    }
}
