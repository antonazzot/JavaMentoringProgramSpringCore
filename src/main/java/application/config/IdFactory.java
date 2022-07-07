package application.config;

import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class factory individual id for every
 * essence in database
 **/
@NoArgsConstructor
public final class IdFactory {
    private AtomicInteger generalID;

    public synchronized int idBuilder(Integer id) {
        generalID = new AtomicInteger(id);
        return generalID.incrementAndGet();
    }
}
