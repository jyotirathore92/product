package com.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GenericResponse {

	private Integer status;
    private String message;
    private Boolean success;
    
    public GenericResponse(Integer status, String message, Boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }
    
}
