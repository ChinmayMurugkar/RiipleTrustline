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
 * ReceivingUnitsRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-10-13T01:22:13.369819-07:00[America/Los_Angeles]")

public class ReceivingUnitsRequest   {
  @JsonProperty("transactionId")
  private String transactionId;

  @JsonProperty("sederName")
  private String sederName;

  @JsonProperty("amount")
  private String amount;

  public ReceivingUnitsRequest transactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Get transactionId
   * @return transactionId
  */
  @ApiModelProperty(value = "")


  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public ReceivingUnitsRequest sederName(String sederName) {
    this.sederName = sederName;
    return this;
  }

  /**
   * Get sederName
   * @return sederName
  */
  @ApiModelProperty(example = "John", value = "")


  public String getSederName() {
    return sederName;
  }

  public void setSederName(String sederName) {
    this.sederName = sederName;
  }

  public ReceivingUnitsRequest amount(String amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @ApiModelProperty(example = "10.45", value = "")


  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReceivingUnitsRequest receivingUnitsRequest = (ReceivingUnitsRequest) o;
    return Objects.equals(this.transactionId, receivingUnitsRequest.transactionId) &&
        Objects.equals(this.sederName, receivingUnitsRequest.sederName) &&
        Objects.equals(this.amount, receivingUnitsRequest.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, sederName, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReceivingUnitsRequest {\n");
    
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    sederName: ").append(toIndentedString(sederName)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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

