package com.miteno.cloudna.metadata;

import java.util.Locale;

/**
 * Created by tim on 18/04/2017.
 */
public interface Field {
    Long getId();
    Long getEntityId();
    String getCode();
    String getName(Locale locale);
    Domain getDomain();
    boolean isNullable();
}
