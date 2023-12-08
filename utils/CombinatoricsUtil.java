package com.mokayz.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CombinatoricsUtil {
    public static <T> List<List<T>> generateSublist(List<T> list, int numElements) {
        if (numElements <= 0 || numElements > list.size()) {
            throw new IllegalArgumentException(String.format("Cannot generate sublists of size <= 0 or size >= list size (%d)", list.size()));
        }

        List<List<T>> result = new ArrayList<>();
        generateSublist(list, numElements, 0, new ArrayList<>(), result);
        return result;
    }

    private static <T> void generateSublist(List<T> list, int numElements, int idx, List<T> current, List<List<T>> result) {
        if (numElements == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = idx; i < list.size(); i++) {
            current.add(list.get(i));
            generateSublist(list, numElements - 1, i + 1, current, result);
            current.removeLast();
        }
    }

    public static <T> Collection<List<T>> partitionList(List<T> list, int numPerPartition) {
        AtomicInteger numPartitions = new AtomicInteger(0);
        return list.stream().collect(Collectors.groupingBy(item -> numPartitions.getAndIncrement() / numPerPartition)).values();
    }
}
