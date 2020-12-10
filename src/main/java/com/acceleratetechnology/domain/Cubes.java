package com.acceleratetechnology.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cubes.
 */
@Entity
@Table(name = "cubes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cubes")
public class Cubes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cube")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<VariableName> cubes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<VariableName> getCubes() {
        return cubes;
    }

    public Cubes cubes(Set<VariableName> variableNames) {
        this.cubes = variableNames;
        return this;
    }

    public Cubes addCube(VariableName variableName) {
        this.cubes.add(variableName);
        variableName.setCube(this);
        return this;
    }

    public Cubes removeCube(VariableName variableName) {
        this.cubes.remove(variableName);
        variableName.setCube(null);
        return this;
    }

    public void setCubes(Set<VariableName> variableNames) {
        this.cubes = variableNames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cubes)) {
            return false;
        }
        return id != null && id.equals(((Cubes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cubes{" +
            "id=" + getId() +
            "}";
    }
}
