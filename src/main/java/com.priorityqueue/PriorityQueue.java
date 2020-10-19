package com.priorityqueue;

import java.util.List;
import java.util.Map;

public interface PriorityQueue {
    public static final int BURN_RATE = 2;
    public void enqueue(Integer priority);

    public Integer dequeue() throws IllegalAccessException;

    boolean isEmpty();
}
