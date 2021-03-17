package org.coredb.registry.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.*;
import javax.persistence.*;

import org.coredb.registry.model.AmigoMessage;

@Entity
@Table(name = "account", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class Account extends AmigoMessage implements Serializable {
  private Integer id;
  private String emigoId;
  private String handle;
  private String name;
  private byte[] logo;
  private String registry;
  private Long created;
  private Long updated;
  private Integer revision;
  private Boolean blocked;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  @JsonIgnore
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer value) {
    this.id = value;
  }

  @JsonIgnore
  public String getEmigoId() {
    return this.emigoId;
  }
  public void setEmigoId(String value) {
    this.emigoId = value;
  }

  @JsonIgnore
  public String getHandle() {
    return this.handle;
  }
  public void setHandle(String value) {
    this.handle = value;
  }

  @JsonIgnore
  public String getName() {
    return this.name;
  }
  public void setName(String value) {
    this.name = value;
  };

  @JsonIgnore
  @Lob
  @Column(length=262144)
  public byte[] getLogo() {
    return this.logo;
  }
  public void setLogo(byte[] value) {
    this.logo = value;
  };

  @JsonIgnore
  public String getRegistry() {
    return this.registry;
  }
  public void setRegistry(String value) {
    this.registry = value;
  }

  @JsonIgnore
  public Long getCreated() {
    return this.created;
  }
  public void setCreated(Long value) {
    this.created = value;
  }

  @JsonIgnore
  public Long getUpdated() {
    return this.updated;
  }
  public void setUpdated(Long value) {
    this.updated = value;
  }

  @JsonIgnore
  public Integer getRevision() {
    return this.revision;
  }
  public void setRevision(Integer value) {
    this.revision = value;
  }

  @JsonIgnore
  @Column(length = 65535)
  public String getMessage() {
    return super.getData();
  }
  public void setMessage(String value) {
    super.setData(value);;
  }

  @JsonIgnore
  @Column(length = 4096)
  public String getSignature() {
    return super.getSignature();
  }
  public void setSignature(String value) {
    super.setSignature(value);
  }

  @JsonIgnore
  @Column(length = 4096)
  public String getPubkey() {
    return super.getKey();
  }
  public void setPubkey(String value) {
    super.setKey(value);
  }

  @JsonIgnore
  @Column(length = 32)
  public String getPubkeyType() {
    return super.getKeyType();
  }
  public void setPubkeyType(String value) {
    super.setKeyType(value);
  }

  @JsonIgnore
  public Boolean getBlocked() {
    return this.blocked;
  }
  public void setBlocked(Boolean value) {
    this.blocked = value;
  }

}

