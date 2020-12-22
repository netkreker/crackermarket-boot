package com.crackermarket.app.core;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;
    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
