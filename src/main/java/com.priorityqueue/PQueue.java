package com.priorityqueue;

import java.util.*;

public class PQueue<T extends MyTask> implements PriorityQueue {
    private Map<Integer, Integer> processed = new HashMap<Integer, Integer>();
    private Map<Integer, List<T>> managedTasks = new TreeMap<Integer, List<T>>();

    public PQueue (List<Integer> priorities) {
        if (priorities == null)
            throw new IllegalArgumentException();
        for (Integer priority : priorities)
            enqueue(priority);
    }

    public synchronized void enqueue (Integer priority) {
        if (priority == null)
            throw new IllegalArgumentException();
        List<T> tasks = this.managedTasks.get(priority);
        if (tasks == null) {
            tasks = new ArrayList<T>();
            this.managedTasks.put(priority, tasks);
        }
        tasks.add((T) new MyTaskImpl(priority));
    }

    /**
     * The PriorityQueue is a map.
     * The key is the priority.
     * The value is a task.
     *
     * The map contains a list of priorities which counts number of dequeue for that priority up to BURN RATE
     * If counter exceeds BURN RATE, counter is reset to 0.
     * All reset is done at tne end
     *
     * Dequeue attempts to get first priority (sorted map)
     * if counter for that priority reached burn rate, next priority is retrieved.
     *
     * @return priority of a task
     */
    public synchronized Integer dequeue () throws IllegalAccessException {
        Set<Integer> priorities = this.managedTasks.keySet();
        //get next priority to process
        if (this.managedTasks.size() <= 0)
            throw new IllegalAccessException();
        Integer priority = priorities.iterator().next();

        T task = null;
        List<T> tasksList4Priority = null;

        int  processedCounter = getProcessedCounter(priority);
        //if BURN RATE reached for current priority get next priority
        if (processedCounter > 0 && processedCounter % BURN_RATE == 0) {
            tasksList4Priority = getNextPriorityTask (priority);
        } else {
            tasksList4Priority = (List<T>) this.managedTasks.get(priority);
        }

        // removing tasks from lists
        if (tasksList4Priority != null) {
            if (!tasksList4Priority.isEmpty())
                task = tasksList4Priority.remove(0);
            if (tasksList4Priority.isEmpty())
                this.managedTasks.remove(task.getPriority());
        }

        // prepare output and clear processed counters
        Integer targetPriority = null;
        if (task != null) {
            targetPriority = task.getPriority();
            processedCounter = getProcessedCounter(targetPriority);
            if (processedCounter == 0) {
                try {
                    for (Integer processed : processed.keySet()) {
                        this.processed.put(processed , 0);
                        if (processed >= targetPriority - 1)
                            break;
                    }
                } catch (Throwable e) {} // processed counters are not set in constructor - expect errors
            }
            processed.put (targetPriority, processedCounter + 1);
        }
        return targetPriority;
    }

    public boolean isEmpty() {
        return this.managedTasks.isEmpty();
    }

    private int getProcessedCounter(Integer priority) {
        try {
            int target = processed.get(priority);
            return target;
        } catch (Throwable e) {
            // processed counters are not set in constructor - expect errors
            return 0;
        }
    }

    /**
     * retrieve next priority
     * if all priorities contain counters up to BURN RATE
     * move to next priority
     *
     * if current priority didn't reach BURN RATE
     * retrieve current priority
     *
     * if nothing above get next higher priority
     *
     * @param currentPriority
     * @return
     */
    private List<T> getNextPriorityTask(Integer currentPriority) {
        for (Integer priority : this.managedTasks.keySet()) {
            Integer counter = processed.get(priority);
            if (counter != null && counter == BURN_RATE)
                continue;
            if (priority == currentPriority) {
                if (counter == null || counter % BURN_RATE != 0)
                    return this.managedTasks.get(priority);
            }
            if (priority > currentPriority)
                return this.managedTasks.get(priority);
        }
        return null;
    }
}
