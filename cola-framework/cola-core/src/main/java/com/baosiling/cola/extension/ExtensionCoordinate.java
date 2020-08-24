package com.baosiling.cola.extension;

import com.alibaba.cola.extension.BizScenario;
import lombok.Data;

import java.util.Objects;

/**
 * @description: ExtensionCoordinate (扩展坐标）is used to uniquely position an Extension
 * @author: wangzhx
 * @create: 2020-08-22 21:41
 */
@Data
public class ExtensionCoordinate {

    private String extensionPointName;
    private String bizScenarioUniqueIdentity;

    private Class extensionPointClass;
    private BizScenario bizScenario;

    public static ExtensionCoordinate valueOf(Class extPtClass, BizScenario bizScenario){
        return new ExtensionCoordinate(extPtClass, bizScenario);
    }

    public ExtensionCoordinate(Class extPtClass, BizScenario bizScenario){
        this.extensionPointClass = extPtClass;
        this.bizScenario = bizScenario;
        this.extensionPointName = extPtClass.getName();
        this.bizScenarioUniqueIdentity = bizScenario.getUniqueIdentity();
    }

    public ExtensionCoordinate(String extensionPointName, String bizScenario){
        this.extensionPointName = extensionPointName;
        this.bizScenarioUniqueIdentity = bizScenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtensionCoordinate that = (ExtensionCoordinate) o;
        return Objects.equals(extensionPointName, that.extensionPointName) &&
                Objects.equals(bizScenarioUniqueIdentity, that.bizScenarioUniqueIdentity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extensionPointName, bizScenarioUniqueIdentity);
    }

    @Override
    public String toString() {
        return "ExtensionCoordinate [extensionPointName=" + extensionPointName +
                ", bizScenarioUniqueIdentity=" + bizScenarioUniqueIdentity + "]";
    }
}