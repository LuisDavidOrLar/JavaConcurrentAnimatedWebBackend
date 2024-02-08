package com.davedex.javaconcurrencyanimatedweb.simulations;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AtomicIntegerService {

    private final List<AtomicInteger> atomicIntegers;
    private int baseValue = 0; // Adjusted to manage the initial increment

    public AtomicIntegerService() {
        atomicIntegers = IntStream.range(0, 5)
                .mapToObj(i -> new AtomicInteger(i))
                .collect(Collectors.toList());
    }

    public synchronized List<Integer> incrementAll() {
        // Increment each AtomicInteger and collect their new values
        List<Integer> newValues = atomicIntegers.stream()
                .map(ai -> ai.incrementAndGet())
                .collect(Collectors.toList());

        // Update the baseValue for the next cycle based on the last incremented value
        // This adjustment now happens after the incrementation to correct the initial state
        if (!newValues.isEmpty()) {
            baseValue = newValues.get(newValues.size() - 1);
        }

        // Prepare atomic integers for the next cycle by setting them to the next values in sequence
        IntStream.range(0, atomicIntegers.size())
                .forEach(i -> atomicIntegers.get(i).set(baseValue + i));

        return newValues;
    }

    public synchronized List<Integer> resetIntegers() {
        IntStream.range(0, atomicIntegers.size())
                .forEach(i -> atomicIntegers.get(i).set(i));
        baseValue = 4; // Reset the base value as before

        // Return the new state of atomic integers after reset
        return atomicIntegers.stream()
                .map(AtomicInteger::get)
                .collect(Collectors.toList());
    }

}