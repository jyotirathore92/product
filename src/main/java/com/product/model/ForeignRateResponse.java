package com.product.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForeignRateResponse {

	private boolean success;
	private Long timestamp;
	private String base;
	private Date date;
	private Map<String, BigDecimal> rates;
	private ForeignRateErrorResponse error;
}
