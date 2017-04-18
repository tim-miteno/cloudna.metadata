package com.miteno.cloudna.metadata;

import java.util.Locale;
import java.util.Set;

/**
 * Created by tim on 18/04/2017.
 */
public interface Entity {
    Long getId();
    String getSchema();
    String getCode();
    String getName(Locale locale);

    Field getField(String fieldCode);
    Set<Field> getFields();
}
