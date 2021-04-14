package com.miniredis.miniredis.domain.model;

import java.time.LocalDateTime;

public class SetDataType extends DataType {

    private Object value;
    private LocalDateTime expirationDate;

    public SetDataType(Object value) {
        this.value = value;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
