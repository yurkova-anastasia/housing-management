package ru.clevertec.cachestarter.cache;

import java.util.List;
import java.util.UUID;

public interface Cache {

    boolean put(UUID key, Object value);

    Object get(UUID key);

    List<Object> getAll();

    boolean delete(UUID key);
}
