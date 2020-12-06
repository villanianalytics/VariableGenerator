package com.acceleratetechnology.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ListUD.
 */
@Entity
@Table(name = "list_ud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "listud")
public class ListUD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_name")
    private String listName;

    @Column(name = "list_value")
    private String listValue;

    @OneToOne
    @JoinColumn(unique = true)
    private VariableName listName;

    @OneToMany(mappedBy = "listUD")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<VariableName> listNames = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public ListUD listName(String listName) {
        this.listName = listName;
        return this;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListValue() {
        return listValue;
    }

    public ListUD listValue(String listValue) {
        this.listValue = listValue;
        return this;
    }

    public void setListValue(String listValue) {
        this.listValue = listValue;
    }

    public VariableName getListName() {
        return listName;
    }

    public ListUD listName(VariableName variableName) {
        this.listName = variableName;
        return this;
    }

    public void setListName(VariableName variableName) {
        this.listName = variableName;
    }

    public Set<VariableName> getListNames() {
        return listNames;
    }

    public ListUD listNames(Set<VariableName> variableNames) {
        this.listNames = variableNames;
        return this;
    }

    public ListUD addListName(VariableName variableName) {
        this.listNames.add(variableName);
        variableName.setListUD(this);
        return this;
    }

    public ListUD removeListName(VariableName variableName) {
        this.listNames.remove(variableName);
        variableName.setListUD(null);
        return this;
    }

    public void setListNames(Set<VariableName> variableNames) {
        this.listNames = variableNames;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListUD)) {
            return false;
        }
        return id != null && id.equals(((ListUD) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListUD{" +
            "id=" + getId() +
            ", listName='" + getListName() + "'" +
            ", listValue='" + getListValue() + "'" +
            "}";
    }
}
