package com.miteno.cloudna.metadata;

/**
 * Created by tim on 18/04/2017.
 */
public interface Domain {
    Long getId();
    String getName();
    void validate(Object value) throws Exception;

}
