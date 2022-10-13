package io.tej.SwaggerCodgen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SendUnitResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-10-13T01:22:13.369819-07:00[America/Los_Angeles]")

public class SendUnitResponse   {
  @JsonProperty("userName")
  private String userName;

  @JsonProperty("unitsSent")
  private String unitsSent;

  public SendUnitResponse userName(String userName) {
    this.userName = userName;
    return this;
  }

  /**
   * Get userName
   * @return userName
  */
  @ApiModelProperty(example = "John", value = "")


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public SendUnitResponse unitsSent(String unitsSent) {
    this.unitsSent = unitsSent;
    return this;
  }

  /**
   * Get unitsSent
   * @return unitsSent
  */
  @ApiModelProperty(example = "10.45", value = "")


  public String getUnitsSent() {
    return unitsSent;
  }

  public void setUnitsSent(String unitsSent) {
    this.unitsSent = unitsSent;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SendUnitResponse sendUnitResponse = (SendUnitResponse) o;
    return Objects.equals(this.userName, sendUnitResponse.userName) &&
        Objects.equals(this.unitsSent, sendUnitResponse.unitsSent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName, unitsSent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SendUnitResponse {\n");
    
    sb.append("    userName: ").append(toIndentedString(userName)).append("\n");
    sb.append("    unitsSent: ").append(toIndentedString(unitsSent)).append("\n");
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

