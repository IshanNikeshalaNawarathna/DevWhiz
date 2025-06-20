package com.DevWhiz.blog.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category categoey = (Category) o;
        return Objects.equals(Id, categoey.Id) && Objects.equals(name, categoey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name);
    }
}
