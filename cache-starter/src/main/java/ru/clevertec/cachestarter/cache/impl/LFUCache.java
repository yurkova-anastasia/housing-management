package ru.clevertec.cachestarter.cache.impl;

import lombok.Data;
import ru.clevertec.cachestarter.cache.Cache;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LFUCache implements Cache {

    private final int size;
    private final ConcurrentHashMap<UUID, CacheElement> cache;

    public LFUCache(int size) {
        this.size = size;
        this.cache = new ConcurrentHashMap<>(size);
    }

    @Override
    public boolean put(UUID key, Object value) {
        CacheElement element = new CacheElement(0, value);
        if (cache.size() >= this.size) {
            evictElement();
        }
        cache.put(key, element);
        return true;
    }

    @Override
    public Object get(UUID key) {
        if (cache.containsKey(key)) {
            CacheElement element = cache.get(key);
            element.setNumberOfRequests(element.getNumberOfRequests() + 1);
            return element.getValue();
        } else {
            return null;
        }
    }

    @Override
    public List<Object> getAll() {
        Collection<CacheElement> cacheElements = cache.values();
        cacheElements = cacheElements.stream()
                .peek(c -> c.setNumberOfRequests(c.getNumberOfRequests() + 1))
                .toList();
        return cacheElements.stream()
                .map(CacheElement::getValue)
                .toList();
    }

    @Override
    public boolean delete(UUID key) {
        CacheElement removed = cache.remove(key);
        return removed != null;
    }


    private void evictElement() {
        CacheElement elementToRemove = cache.values().stream()
                .min(Comparator.comparing(CacheElement::getNumberOfRequests))
                .orElse(null);

        if (elementToRemove != null) {
            UUID keyToRemove = this.getKeyByValue(cache, elementToRemove);
            cache.remove(keyToRemove);
        }
    }

    private UUID getKeyByValue(Map<UUID, CacheElement> cache, CacheElement value) {
        for (Map.Entry<UUID, CacheElement> entry : cache.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Data
    private static class CacheElement {

        private Integer numberOfRequests;
        private Object value;

        public CacheElement(Integer numberOfRequests, Object value) {
            this.numberOfRequests = numberOfRequests;
            this.value = value;
        }
    }

}
