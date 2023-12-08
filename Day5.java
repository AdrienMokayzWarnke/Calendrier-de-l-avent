package com.mokayz;

import com.mokayz.utils.CombinatoricsUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.LongStream;

public class Day5 {

    public static FileReader file;
    public static BufferedReader br;

    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay5.txt");
            //file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/smallDay5.txt");
            br = new BufferedReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int solution(boolean problem2) throws IOException {

        BufferedReader br = new BufferedReader(file);

        if(!problem2) {
            System.out.println(solvePartOne());
        } else {
            System.out.println(solvePartTwo());
        }

        br.close();

        return 0;
    }

    private static List<List<String>> getComponents(BufferedReader br) {
        List<List<String>> components = new ArrayList<>();
        List<String> currentComponent = new ArrayList<>();
        br.lines().forEach(line -> {
            if (line.isBlank()) {
                if (!currentComponent.isEmpty()) {
                    components.add(new ArrayList<>(currentComponent));
                    currentComponent.clear();
                }
            } else {
                currentComponent.add(line);
            }
        });
        return components;
    }
    private static List<Long> getSeeds(List<List<String>> components) {
        return components.getFirst()
                .stream()
                .limit(1)
                .flatMap(line -> Arrays.stream(line.split(": ")[1].trim().split(" ")))
                .map(Long::parseLong).toList();
    }

    private static List<Map> getMaps(List<List<String>> components) {
        return components.stream()
                .skip(1)
                .map(Map::create)
                .toList();
    }

    public static String solvePartOne() {
        final List<List<String>> components = getComponents(br);
        final List<Long> seeds = getSeeds(components);
        final List<Map> maps = getMaps(components);

        return String.valueOf(seeds.stream().map(seed -> {
            long value = seed;

            for (Map map : maps) {
                value = map.get(value);
            }

            return value;
        }).min(Long::compareTo).orElseThrow());
    }

    public static boolean isOriginalSeed(Collection<List<Long>> seedRanges, long seed) {
        for (List<Long> range : seedRanges) {
            if (range.get(0) <= seed && seed < range.get(0) + range.get(1)) {
                return true;
            }
        }

        return false;
    }

    public static String solvePartTwo() {

        final List<List<String>> components = getComponents(br);

        final Collection<List<Long>> seedRanges = CombinatoricsUtil.partitionList(getSeeds(components), 2);

        List<Map> maps = new ArrayList<>(getMaps(components));
        Collections.reverse(maps);

        LongStream locations = maps.getFirst().getAllValues();
        PrimitiveIterator.OfLong iterator = locations.iterator();
        while (iterator.hasNext()) {
            long next = iterator.next();

            long value = next;
            for (Map map : maps) {
                value = map.getKey(value);
            }

            if (isOriginalSeed(seedRanges, value)) {
                return String.valueOf(next);
            }
        }

        return "Not found";
    }

}

record Map(String name, List<MapEntry> entries) {
        static Map create(List<String> lines) {
            String name = lines.get(0).split(":")[0].split(" ")[0];
            List<MapEntry> entries = lines.stream().skip(1).map(MapEntry::create).toList();
            return new Map(name, entries);
        }

        public long get(long key) {
            for (MapEntry entry : entries) {
                if (entry.sourceStart() <= key && key <= entry.sourceEnd()) {
                    long distance = key - entry.sourceStart();
                    return entry.destinationStart() + distance;
                }
            }

            return key;
        }

        public long getKey(long value) {
            for (MapEntry entry : entries) {
                if (entry.destinationStart() <= value && value <= entry.destinationEnd()) {
                    long distance = value - entry.destinationStart();
                    return entry.sourceStart() + distance;
                }
            }

            return value;
        }

        public LongStream getAllValues() {
            long highestDestination = entries.stream().map(MapEntry::destinationEnd).max(Long::compareTo).orElseThrow();
            return LongStream.range(0, highestDestination).sorted();
        }
}

record MapEntry(long destinationStart, long destinationEnd, long sourceStart, long sourceEnd) {
    static MapEntry create(String line) {
        List<Long> parts = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
        return new MapEntry(parts.get(0), parts.get(0) + parts.get(2) - 1, parts.get(1), parts.get(1) + parts.get(2) - 1);
    }
}
