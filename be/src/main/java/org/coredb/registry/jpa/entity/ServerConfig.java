package org.coredb.registry.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.coredb.registry.model.ConfigEntry;
import org.coredb.registry.model.Entry;

import java.io.*;
import javax.persistence.*;

@Entity
@Table(name = "config", uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class ServerConfig extends ConfigEntry implements Serializable {
  private Integer id;

  public ServerConfig() {
    super.setEntry(new Entry());
  }

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
  public String getConfigId() {
    return super.getConfigId();
  }
  public void setConfigId(String value) {
    super.setConfigId(value);
  }

  @JsonIgnore
  public String getName() {
    return super.getEntry().getName();
  }
  public void setName(String value) {
    super.getEntry().setName(value);
  }

  @JsonIgnore
  public String getStringValue() {
    return super.getEntry().getStringValue();
  }
  public void setStringValue(String value) {
    super.getEntry().setStringValue(value);
  }

  @JsonIgnore
  public Long getNumValue() {
    return super.getEntry().getNumValue();
  }
  public void setNumValue(Long value) {
    super.getEntry().setNumValue(value);
  }

  @JsonIgnore
  public Boolean getBoolValue() {
    return super.getEntry().isBoolValue();
  }
  public void setBoolValue(Boolean value) {
    super.getEntry().setBoolValue(value);
  }

}

