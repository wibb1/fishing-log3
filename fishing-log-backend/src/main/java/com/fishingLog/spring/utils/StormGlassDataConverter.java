package com.fishingLog.spring.utils;

import java.io.IOException;
import java.util.Map;

public interface StormGlassDataConverter {
    Map<String, Object> dataConverter(String data) throws IOException;

}
