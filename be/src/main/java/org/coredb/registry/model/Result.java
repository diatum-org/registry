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
 * Result
 */
@Validated
public class Result   {
  @JsonProperty("boolValue")
  private Boolean boolValue = null;

  @JsonProperty("numValue")
  private Long numValue = null;

  @JsonProperty("strValue")
  private String strValue = null;

  public Result boolValue(Boolean boolValue) {
    this.boolValue = boolValue;
    return this;
  }

  /**
   * Get boolValue
   * @return boolValue
  **/
  @ApiModelProperty(value = "")

  public Boolean isBoolValue() {
    return boolValue;
  }

  public void setBoolValue(Boolean boolValue) {
    this.boolValue = boolValue;
  }

  public Result numValue(Long numValue) {
    this.numValue = numValue;
    return this;
  }

  /**
   * Get numValue
   * @return numValue
  **/
  @ApiModelProperty(value = "")

  public Long getNumValue() {
    return numValue;
  }

  public void setNumValue(Long numValue) {
    this.numValue = numValue;
  }

  public Result strValue(String strValue) {
    this.strValue = strValue;
    return this;
  }

  /**
   * Get strValue
   * @return strValue
  **/
  @ApiModelProperty(value = "")

  public String getStrValue() {
    return strValue;
  }

  public void setStrValue(String strValue) {
    this.strValue = strValue;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Result result = (Result) o;
    return Objects.equals(this.boolValue, result.boolValue) &&
        Objects.equals(this.numValue, result.numValue) &&
        Objects.equals(this.strValue, result.strValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(boolValue, numValue, strValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Result {\n");
    
    sb.append("    boolValue: ").append(toIndentedString(boolValue)).append("\n");
    sb.append("    numValue: ").append(toIndentedString(numValue)).append("\n");
    sb.append("    strValue: ").append(toIndentedString(strValue)).append("\n");
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
