package com.acceleratetechnology.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ConnectionMgr.
 */
@Entity
@Table(name = "connection_mgr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "connectionmgr")
public class ConnectionMgr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "u_rl")
    private String uRL;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "identity_domain")
    private String identityDomain;

    @OneToOne
    @JoinColumn(unique = true)
    private App connectionName;

    @OneToOne
    @JoinColumn(unique = true)
    private Cubes connectionName;

    @OneToOne
    @JoinColumn(unique = true)
    private VariableName connectionName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ConnectionMgr description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getuRL() {
        return uRL;
    }

    public ConnectionMgr uRL(String uRL) {
        this.uRL = uRL;
        return this;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
    }

    public String getUsername() {
        return username;
    }

    public ConnectionMgr username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public ConnectionMgr password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityDomain() {
        return identityDomain;
    }

    public ConnectionMgr identityDomain(String identityDomain) {
        this.identityDomain = identityDomain;
        return this;
    }

    public void setIdentityDomain(String identityDomain) {
        this.identityDomain = identityDomain;
    }

    public App getConnectionName() {
        return connectionName;
    }

    public ConnectionMgr connectionName(App app) {
        this.connectionName = app;
        return this;
    }

    public void setConnectionName(App app) {
        this.connectionName = app;
    }

    public Cubes getConnectionName() {
        return connectionName;
    }

    public ConnectionMgr connectionName(Cubes cubes) {
        this.connectionName = cubes;
        return this;
    }

    public void setConnectionName(Cubes cubes) {
        this.connectionName = cubes;
    }

    public VariableName getConnectionName() {
        return connectionName;
    }

    public ConnectionMgr connectionName(VariableName variableName) {
        this.connectionName = variableName;
        return this;
    }

    public void setConnectionName(VariableName variableName) {
        this.connectionName = variableName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionMgr)) {
            return false;
        }
        return id != null && id.equals(((ConnectionMgr) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectionMgr{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", uRL='" + getuRL() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", identityDomain='" + getIdentityDomain() + "'" +
            "}";
    }
}
