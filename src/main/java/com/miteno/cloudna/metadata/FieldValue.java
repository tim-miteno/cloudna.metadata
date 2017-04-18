package com.miteno.cloudna.metadata;

/**
 * Created by tim on 18/04/2017.
 */
public class FieldValue {
    private Field field;
    private Object value;

    public FieldValue(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public boolean hasNoPermission() {
        return false;
    }
}
