package com.acceleratetechnology.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Connection.
 */
@Entity
@Table(name = "connection")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "connection")
public class Connection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "connection")
    private String connection;

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
    private App connection;

    @OneToOne
    @JoinColumn(unique = true)
    private Cubes connection;

    @OneToOne
    @JoinColumn(unique = true)
    private VariableName connection;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConnection() {
        return connection;
    }

    public Connection connection(String connection) {
        this.connection = connection;
        return this;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getDescription() {
        return description;
    }

    public Connection description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getuRL() {
        return uRL;
    }

    public Connection uRL(String uRL) {
        this.uRL = uRL;
        return this;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
    }

    public String getUsername() {
        return username;
    }

    public Connection username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Connection password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentityDomain() {
        return identityDomain;
    }

    public Connection identityDomain(String identityDomain) {
        this.identityDomain = identityDomain;
        return this;
    }

    public void setIdentityDomain(String identityDomain) {
        this.identityDomain = identityDomain;
    }

    public App getConnection() {
        return connection;
    }

    public Connection connection(App app) {
        this.connection = app;
        return this;
    }

    public void setConnection(App app) {
        this.connection = app;
    }

    public Cubes getConnection() {
        return connection;
    }

    public Connection connection(Cubes cubes) {
        this.connection = cubes;
        return this;
    }

    public void setConnection(Cubes cubes) {
        this.connection = cubes;
    }

    public VariableName getConnection() {
        return connection;
    }

    public Connection connection(VariableName variableName) {
        this.connection = variableName;
        return this;
    }

    public void setConnection(VariableName variableName) {
        this.connection = variableName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Connection)) {
            return false;
        }
        return id != null && id.equals(((Connection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Connection{" +
            "id=" + getId() +
            ", connection='" + getConnection() + "'" +
            ", description='" + getDescription() + "'" +
            ", uRL='" + getuRL() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", identityDomain='" + getIdentityDomain() + "'" +
            "}";
    }
}
