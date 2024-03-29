package org.coredb.registry.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SystemStat
 */
@Validated
public class SystemStat   {
  @JsonProperty("timestamp")
  private Integer timestamp = null;

  @JsonProperty("processor")
  private Integer processor = null;

  @JsonProperty("memory")
  private Long memory = null;

  @JsonProperty("storage")
  private Long storage = null;

  @JsonProperty("requests")
  private Long requests = null;

  @JsonProperty("accounts")
  private Long accounts = null;

  public SystemStat timestamp(Integer timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Integer getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Integer timestamp) {
    this.timestamp = timestamp;
  }

  public SystemStat processor(Integer processor) {
    this.processor = processor;
    return this;
  }

  /**
   * Get processor
   * @return processor
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Integer getProcessor() {
    return processor;
  }

  public void setProcessor(Integer processor) {
    this.processor = processor;
  }

  public SystemStat memory(Long memory) {
    this.memory = memory;
    return this;
  }

  /**
   * Get memory
   * @return memory
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Long getMemory() {
    return memory;
  }

  public void setMemory(Long memory) {
    this.memory = memory;
  }

  public SystemStat storage(Long storage) {
    this.storage = storage;
    return this;
  }

  /**
   * Get storage
   * @return storage
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Long getStorage() {
    return storage;
  }

  public void setStorage(Long storage) {
    this.storage = storage;
  }

  public SystemStat requests(Long requests) {
    this.requests = requests;
    return this;
  }

  /**
   * Get requests
   * @return requests
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Long getRequests() {
    return requests;
  }

  public void setRequests(Long requests) {
    this.requests = requests;
  }

  public SystemStat accounts(Long accounts) {
    this.accounts = accounts;
    return this;
  }

  /**
   * Get accounts
   * @return accounts
  **/
  @ApiModelProperty(required = true, value = "")
      @NotNull

    public Long getAccounts() {
    return accounts;
  }

  public void setAccounts(Long accounts) {
    this.accounts = accounts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SystemStat systemStat = (SystemStat) o;
    return Objects.equals(this.timestamp, systemStat.timestamp) &&
        Objects.equals(this.processor, systemStat.processor) &&
        Objects.equals(this.memory, systemStat.memory) &&
        Objects.equals(this.storage, systemStat.storage) &&
        Objects.equals(this.requests, systemStat.requests) &&
        Objects.equals(this.accounts, systemStat.accounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, processor, memory, storage, requests, accounts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SystemStat {\n");
    
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    processor: ").append(toIndentedString(processor)).append("\n");
    sb.append("    memory: ").append(toIndentedString(memory)).append("\n");
    sb.append("    storage: ").append(toIndentedString(storage)).append("\n");
    sb.append("    requests: ").append(toIndentedString(requests)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

