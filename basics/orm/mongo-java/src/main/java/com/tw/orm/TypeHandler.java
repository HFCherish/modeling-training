package com.tw.orm;

/**
 * Created by pzzheng on 12/28/16.
 */

import java.util.Optional;

public interface TypeHandler {
    Optional<Converter> getConverter(ConversionType sourceType, ConversionType targetType);

}
