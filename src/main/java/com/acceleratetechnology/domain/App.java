package com.acceleratetechnology.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A App.
 */
@Entity
@Table(name = "app")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "app")
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "derived_value")
    private Boolean derivedValue;

    @Column(name = "variable_value")
    private String variableValue;

    @Column(name = "eff_dt")
    private ZonedDateTime effDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDerivedValue() {
        return derivedValue;
    }

    public App derivedValue(Boolean derivedValue) {
        this.derivedValue = derivedValue;
        return this;
    }

    public void setDerivedValue(Boolean derivedValue) {
        this.derivedValue = derivedValue;
    }

    public String getVariableValue() {
        return variableValue;
    }

    public App variableValue(String variableValue) {
        this.variableValue = variableValue;
        return this;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public ZonedDateTime getEffDt() {
        return effDt;
    }

    public App effDt(ZonedDateTime effDt) {
        this.effDt = effDt;
        return this;
    }

    public void setEffDt(ZonedDateTime effDt) {
        this.effDt = effDt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof App)) {
            return false;
        }
        return id != null && id.equals(((App) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "App{" +
            "id=" + getId() +
            ", derivedValue='" + isDerivedValue() + "'" +
            ", variableValue='" + getVariableValue() + "'" +
            ", effDt='" + getEffDt() + "'" +
            "}";
    }
}
