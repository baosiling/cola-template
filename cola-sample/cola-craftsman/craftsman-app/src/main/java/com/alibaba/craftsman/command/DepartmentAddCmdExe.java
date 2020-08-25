package com.alibaba.craftsman.command;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.Response;
import com.alibaba.craftsman.convertor.DepartmentConvertor;
import com.alibaba.craftsman.domain.organization.Department;
import com.alibaba.craftsman.dto.DepartmentAddCmd;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;
import com.alibaba.craftsman.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Command
public class DepartmentAddCmdExe implements CommandExecutorI<Response, DepartmentAddCmd> {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Response execute(DepartmentAddCmd cmd) {
        DepartmentCO departmentCO = cmd.getDepartmentCO();
        Department department = DepartmentConvertor.toEntity(departmentCO);
        departmentRepository.create(department);
        return Response.buildSuccess();
    }
}
