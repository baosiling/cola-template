package com.baosiling.cola.extension;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: ExtensionRepository
 * @author: wangzhx
 * @create: 2020-08-22 22:18
 */
@Component
public class ExtensionRepository {

    @Getter
    private Map<ExtensionCoordinate, ExtensionPointI> extensionRepo = new HashMap<>();
}