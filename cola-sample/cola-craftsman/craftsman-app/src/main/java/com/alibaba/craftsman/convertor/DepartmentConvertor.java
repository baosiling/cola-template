package com.alibaba.craftsman.convertor;

import com.alibaba.craftsman.domain.organization.Department;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;
import com.alibaba.craftsman.tunnel.database.dataobject.DepartmentDO;
import org.springframework.beans.BeanUtils;

public class DepartmentConvertor {

    public static Department toEntity(DepartmentCO departmentCO){
        Department department = new Department();
        BeanUtils.copyProperties(departmentCO, department);
        return department;
    }

    public static DepartmentDO toDataObjectForCreate(Department department){
        DepartmentDO departmentDO = new DepartmentDO();
        BeanUtils.copyProperties(department, departmentDO);
        return departmentDO;
    }

    public static Department toEntity(DepartmentDO departmentDO){
        Department department = new Department();
        BeanUtils.copyProperties(departmentDO, department);
        return department;
    }

}
