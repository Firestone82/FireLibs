package cz.devfire.firelibs.Shared.Utils.Objects;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PercentageList<T> {
    private final HashMap<T, Integer> def = Maps.newHashMap();
    private final List<T> items = Lists.newArrayList();

    public void add(T t, Integer percent) {
        def.put(t, percent);

        for (int i = 0; i < percent; ++i) {
            items.add(t);
        }
    }

    public void remove(T t) {
        for (int i = 0; i < getChance(t); ++i) {
            items.add(t);
        }

        def.remove(t);
    }

    public void clear() {
        def.clear();
        items.clear();
    }

    public boolean contains(T t) {
        return def.containsKey(t);
    }

    public double getChance(T t) {
        return def.get(t);
    }

    public void clean() {
        // TODO: Zmenseni velikosti nejvetsim spolecnym delitelem
    }

    public T getRandom() {
        Collections.shuffle(items);

        return items.get(0);
    }
}