package com.priorityqueue;

public class MyTaskImpl implements MyTask {
    private int priority;
    public MyTaskImpl (int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int compareTo(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
        if (!(o instanceof MyTask))
            throw new IllegalArgumentException();
        MyTask other = (MyTask)o;
        if (this.getPriority() < other.getPriority())
            return -1;
        else if (this.getPriority() > other.getPriority())
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return "MyTaskImpl{" +
                "priority=" + priority +
                '}';
    }
}
