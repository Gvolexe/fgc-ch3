package org.firstinspires.ftc.teamcode.utilities;

import java.util.ArrayList;
import java.util.List;

public class RingBuffer<T> {

    protected List<T> list;
    protected int index = 0;

    public RingBuffer(int length, T startingValue) {
        list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(startingValue);
        }
    }

    public T getValue(T current) {
        T retVal = list.get(index);

        list.set(index, current);

        index++;
        index = index % list.size();

        return retVal;

    }
}