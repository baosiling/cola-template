package com.alibaba.craftsman.command.query;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.extension.ExtensionExecutor;
import com.alibaba.craftsman.domain.organization.Department;
import com.alibaba.craftsman.dto.OrganizationQry;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;
import com.alibaba.craftsman.extension.OrganizationExtPtI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Command
public class DepartmentQryExe implements CommandExecutorI<Response, OrganizationQry> {

    @Autowired
    private ExtensionExecutor extensionExecutor;

    @Override
    public Response execute(OrganizationQry cmd) {
        String cropId = cmd.getCorpId();
        List<Department> departmentList
                = extensionExecutor.execute(OrganizationExtPtI.class, cmd.getBizScenario(), ex->ex.listDepartmentBy(cropId));
        if(!CollectionUtils.isEmpty(departmentList)){
            List<DepartmentCO> departmentCOList = new ArrayList<>();
            departmentList.forEach(t->{
                DepartmentCO departmentCO = new DepartmentCO();
                BeanUtils.copyProperties(t, departmentCO);
                departmentCOList.add(departmentCO);
            });
            return MultiResponse.ofWithoutTotal(departmentCOList);
        }
        return MultiResponse.buildSuccess();
    }

}
