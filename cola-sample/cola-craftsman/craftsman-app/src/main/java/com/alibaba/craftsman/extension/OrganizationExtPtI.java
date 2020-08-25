package com.alibaba.craftsman.extension;

import com.alibaba.cola.extension.ExtensionPointI;
import com.alibaba.craftsman.domain.organization.Department;

import java.util.List;

public interface OrganizationExtPtI extends ExtensionPointI {

    List<Department> listDepartmentBy(String cropId);
}
