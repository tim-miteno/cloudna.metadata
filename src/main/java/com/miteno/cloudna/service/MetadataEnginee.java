package com.miteno.cloudna.service;

import com.miteno.cloudna.model.Rectifier;
import com.miteno.cloudna.model.Sorter;

import java.util.List;
import java.util.Map;

/**
 * Created by tim on 18/04/2017.
 */
public interface MetadataEnginee {
    public Map<String, Object> pump(Long pumpId, Rectifier rectifier, List<Sorter> sorters);
}
