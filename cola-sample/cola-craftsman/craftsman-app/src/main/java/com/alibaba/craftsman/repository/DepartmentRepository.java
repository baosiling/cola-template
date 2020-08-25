package com.alibaba.craftsman.repository;

import com.alibaba.craftsman.convertor.DepartmentConvertor;
import com.alibaba.craftsman.domain.organization.Department;
import com.alibaba.craftsman.tunnel.database.DepartmentTunnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {

    @Autowired
    private DepartmentTunnel departmentTunnel;

    public void create(Department department){
        departmentTunnel.create(DepartmentConvertor.toDataObjectForCreate(department));
    }

}
