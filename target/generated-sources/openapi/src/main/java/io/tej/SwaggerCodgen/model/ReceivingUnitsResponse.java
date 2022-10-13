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
 * ReceivingUnitsResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-10-13T01:22:13.369819-07:00[America/Los_Angeles]")

public class ReceivingUnitsResponse   {
  @JsonProperty("unitsReceived")
  private String unitsReceived;

  public ReceivingUnitsResponse unitsReceived(String unitsReceived) {
    this.unitsReceived = unitsReceived;
    return this;
  }

  /**
   * Get unitsReceived
   * @return unitsReceived
  */
  @ApiModelProperty(example = "10.45", value = "")


  public String getUnitsReceived() {
    return unitsReceived;
  }

  public void setUnitsReceived(String unitsReceived) {
    this.unitsReceived = unitsReceived;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReceivingUnitsResponse receivingUnitsResponse = (ReceivingUnitsResponse) o;
    return Objects.equals(this.unitsReceived, receivingUnitsResponse.unitsReceived);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitsReceived);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReceivingUnitsResponse {\n");
    
    sb.append("    unitsReceived: ").append(toIndentedString(unitsReceived)).append("\n");
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

