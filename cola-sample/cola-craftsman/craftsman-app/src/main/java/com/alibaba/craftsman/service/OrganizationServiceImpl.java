package com.alibaba.craftsman.service;

import com.alibaba.cola.command.CommandBus;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.craftsman.api.OrganizationServiceI;
import com.alibaba.craftsman.dto.DepartmentAddCmd;
import com.alibaba.craftsman.dto.OrganizationQry;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationServiceI {

    @Autowired
    private CommandBus commandBus;

    @Override
    public MultiResponse<DepartmentCO> listDepartmentBy(OrganizationQry organizationQry) {
        Response response = commandBus.send(organizationQry);
        return (MultiResponse<DepartmentCO>)response;
    }

    @Override
    public Response addDepartment(DepartmentAddCmd departmentAddCmd) {
        return commandBus.send(departmentAddCmd);
    }


}
