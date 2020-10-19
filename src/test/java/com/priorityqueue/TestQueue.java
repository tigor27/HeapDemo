package com.priorityqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestQueue {
    @Test
    public void test1 () throws IllegalAccessException {
//        Step 1: Enqueue (in sequence from left to right): 4 1 3 2 1 2
//        Step 2: Dequeue: 1
//        Step 3: Dequeue: 1
//        Step 4: Enqueue: 1
//        Step 5: Dequeue: 2 (not 1 due to step 3 where two items with priority "1" have been dequeued
//                thus enabling Constraint B, that is, the next item must be of priority P+1)
//        Step 6: Dequeue: 1
//        Step 7: Dequeue: 2
//        Step 8: Dequeue: 3
        StringBuilder target = new StringBuilder();
        List<Integer> initialSet = new ArrayList<Integer>(Arrays.asList(4, 1, 3, 2, 1, 2));
        PriorityQueue queue = new PQueue(initialSet);
        target.append(queue.dequeue());
        target.append(queue.dequeue());
        queue.enqueue(1);
        while (!queue.isEmpty())
            target.append(queue.dequeue());
        Assert.assertEquals("1121234", target.toString());
    }

    @Test
    public void test2 () throws IllegalAccessException {
//        Step 1: Enqueue (in sequence from left to right): 4 1 3 2 2
//        Step 2: Dequeue: 1
//        Step 3: Dequeue: 2
//        Step 4: Enqueue: 1
//        Step 5: Dequeue: 1
//        Step 6: Dequeue: 2
//        Step 7: Dequeue: 3 (two items with priority "2" dequeued in steps 3 & 6 (not necessary to be
//                consecutive) thus enabling Constraint B)
//        Step 8: Dequeue: 4
        StringBuilder target = new StringBuilder();
        List<Integer> initialSet = new ArrayList<Integer>(Arrays.asList(4, 1, 3, 2, 2));
        PriorityQueue queue = new PQueue(initialSet);
        target.append(queue.dequeue());
        target.append(queue.dequeue());
        queue.enqueue(1);
        while (!queue.isEmpty())
            target.append(queue.dequeue());
        Assert.assertEquals("121234", target.toString());
    }

    @Test
    public void test3 () throws IllegalAccessException {
        StringBuilder target = new StringBuilder();
        List<Integer> initialSet = new ArrayList<Integer>(Arrays.asList(4,1,3,2, 1,4,2,3, 2,4,1,3, 3,5,2,1, 3,6,1,2, 4,2,4,1, 3,2,1,5, 2,1,1,2, 3,1,1));
        PriorityQueue queue = new PQueue(initialSet);
        while (!queue.isEmpty())
            target.append(queue.dequeue());
        Assert.assertEquals("11211231121123411212322345233434564", target.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void test5 () throws IllegalAccessException {
        PriorityQueue queue = new PQueue<MyTask>(null);
        queue.enqueue(1);
        queue.dequeue();
    }

    @Test(expected = IllegalAccessException.class)
    public void test6 () throws IllegalAccessException {
        List<Integer> initialSet = new ArrayList<Integer>();
        PriorityQueue queue = new PQueue<MyTask>(initialSet);
        queue.dequeue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test7 () throws IllegalAccessException {
        List<Integer> initialSet = new ArrayList<Integer>();
        PriorityQueue queue = new PQueue<MyTask>(initialSet);
        queue.enqueue(null);
        queue.dequeue();
    }

}
