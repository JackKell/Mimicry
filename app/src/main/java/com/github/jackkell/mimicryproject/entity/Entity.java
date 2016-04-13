package com.github.jackkell.mimicryproject.entity;

public abstract class Entity implements IEntity {

    public Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
