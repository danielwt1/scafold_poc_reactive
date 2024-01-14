package co.com.bancolombia.model.productmodel.model;

import lombok.Getter;

@Getter
public class ValueObject <T> {
    private final T value;
    public ValueObject(T value) {
        this.value = value;
    }

}
