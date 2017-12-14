package ru.chemarev.andrey.convertor;

@FunctionalInterface
public interface CustomConverter<T> {
    Object convert(T obj);
}
