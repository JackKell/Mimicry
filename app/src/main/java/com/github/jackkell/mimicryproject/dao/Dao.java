package com.github.jackkell.mimicryproject.dao;

import com.github.jackkell.mimicryproject.entity.IEntity;

public interface Dao<T extends IEntity> {

    public Long create(T object);
    public T get(Long id);
    public void update(T object);
    public void delete(Long id);
}
