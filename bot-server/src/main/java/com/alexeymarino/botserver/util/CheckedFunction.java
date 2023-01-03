package com.alexeymarino.botserver.util;

@FunctionalInterface
public interface CheckedFunction<T, U> {
    void apply(T t, U u) throws Exception;
}
