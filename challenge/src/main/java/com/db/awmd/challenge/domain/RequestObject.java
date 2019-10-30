package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class RequestObject {
	@Getter
	@Setter
	@NotNull
	
	private String accFromId;
	
	@Getter
	@Setter
    @NotNull
	private String accToId;
	
	@Getter
	@Setter
    @NotNull
	private BigDecimal amountToTranfer;
	
	  @JsonCreator
	  public RequestObject(@JsonProperty("accFromId") String accFromId,
	    @JsonProperty("accToId") String accToId,@JsonProperty("amountToTranfer") BigDecimal amountToTranfer ) {
	    this.accFromId = accFromId;
	    this.accToId = accToId;
	    this.amountToTranfer = amountToTranfer;
	  }
	
}
