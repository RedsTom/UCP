package fr.il_totore.ucp.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ListUtils {

    public static <T> Optional<T> find(List<T> list, Function<T, Boolean> p) {
        final Iterator<T> it = list.listIterator();
        while (it.hasNext()) {
            final T t = it.next();
            if (p.apply(t)) return Optional.of(t);
        }
        return Optional.empty();
    }

}
