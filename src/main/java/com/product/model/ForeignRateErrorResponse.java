package com.product.model;

import lombok.Data;

@Data
public class ForeignRateErrorResponse {

    private int errorCode;
    private String type;
    private String info;
}
