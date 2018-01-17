package ru.chemarev.andrey.cache;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheEngineImplTest {

    @Test
    public void maxElementsTest() {
        CacheEngine<Long, String> cache = new CacheEngineImpl<>(10, 0, 0, true);

        for (long i = 1; i <= 20; i++) {
            cache.put(new MyElement<>(i, Long.toString(i)));
        }

        for (long i = 1; i <= 20; i++) {
            cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(10, cache.getMissCount());

        cache.dispose();
    }

    @Test
    public void lifeTimeMsTest() throws InterruptedException {
        long lifeTimeMs = 5 * 1000;
        CacheEngine<Long, String> cache = new CacheEngineImpl<>(10, lifeTimeMs, 0, false);

        for (long i = 1; i <= 20; i++) {
            cache.put(new MyElement<>(i, Long.toString(i)));
        }

        Thread.sleep(lifeTimeMs + 1000);

        for (long i = 1; i <= 20; i++) {
            cache.get(i);
        }

        assertEquals(0, cache.getHitCount());
        assertEquals(20, cache.getMissCount());

        cache.dispose();
    }

    @Test
    public void idleTimeMsTest() throws InterruptedException {
        long idleTimeMs = 5 * 1000;
        CacheEngine<Long, String> cache = new CacheEngineImpl<>(20, 0, idleTimeMs, false);

        for (long i = 1; i <= 20; i++) {
            cache.put(new MyElement<>(i, Long.toString(i)));
        }

        Thread.sleep(idleTimeMs / 2);

        for (long i = 1; i <= 10; i++) {
            cache.get(i);
        }

        Thread.sleep(idleTimeMs);

        for (int i = 1; i <= 20; i++) {
            cache.get((long) i);
        }

        assertEquals(20, cache.getHitCount()); // first 10 elements was got twice
        assertEquals(10, cache.getMissCount());

        cache.dispose();
    }
}