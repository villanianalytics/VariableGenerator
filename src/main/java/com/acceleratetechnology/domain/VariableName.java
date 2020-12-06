package com.acceleratetechnology.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A VariableName.
 */
@Entity
@Table(name = "variable_name")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "variablename")
public class VariableName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variable_name")
    private String variableName;

    @ManyToOne
    @JsonIgnoreProperties(value = "cubes", allowSetters = true)
    private Cubes cube;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariableName() {
        return variableName;
    }

    public VariableName variableName(String variableName) {
        this.variableName = variableName;
        return this;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Cubes getCube() {
        return cube;
    }

    public VariableName cube(Cubes cubes) {
        this.cube = cubes;
        return this;
    }

    public void setCube(Cubes cubes) {
        this.cube = cubes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariableName)) {
            return false;
        }
        return id != null && id.equals(((VariableName) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariableName{" +
            "id=" + getId() +
            ", variableName='" + getVariableName() + "'" +
            "}";
    }
}
