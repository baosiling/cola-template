package com.alibaba.craftsman.command.query;

import com.alibaba.cola.command.Command;
import com.alibaba.cola.command.CommandExecutorI;
import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.craftsman.dto.OrganizationQry;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;
import com.alibaba.craftsman.tunnel.database.OrganizationTunnel;
import com.alibaba.craftsman.tunnel.database.dataobject.DepartmentDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Command
public class OrganizationQryExe implements CommandExecutorI<Response, OrganizationQry> {

    @Autowired
    private OrganizationTunnel organizationTunnel;

    @Override
    public Response execute(OrganizationQry cmd) {
        List<DepartmentDO> departmentDOList
                = organizationTunnel.listByCropId(cmd.getCorpId(), cmd.isIncludeDelete() ? "Y" : "N");

        if(!CollectionUtils.isEmpty(departmentDOList)){
            List<DepartmentCO> departmentCOList = new ArrayList<>();
            departmentDOList.forEach(t->{
                DepartmentCO departmentCO = new DepartmentCO();
                BeanUtils.copyProperties(t, departmentCO);
                departmentCOList.add(departmentCO);
            });
            return MultiResponse.ofWithoutTotal(departmentCOList);
        }
        return MultiResponse.buildSuccess();
    }
}
