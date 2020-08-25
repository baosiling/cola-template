package com.alibaba.craftsman.extension;


import com.alibaba.cola.extension.Extension;
import com.alibaba.craftsman.convertor.DepartmentConvertor;
import com.alibaba.craftsman.domain.organization.Department;
import com.alibaba.craftsman.tunnel.database.DepartmentTunnel;
import com.alibaba.craftsman.tunnel.database.dataobject.DepartmentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Extension(bizId = "organize", useCase = "getByCropId", scenario = "dingTalk")
public class DingTalkOrganizationExt implements OrganizationExtPtI {

    @Autowired
    private DepartmentTunnel departmentTunnel;

    @Override
    public List<Department> listDepartmentBy(String cropId) {
        List<DepartmentDO> departmentDOList = departmentTunnel.listByCropId(cropId, "N");
        if(!CollectionUtils.isEmpty(departmentDOList)){
            List<Department> departmentList = new ArrayList<>();
            departmentDOList.forEach(t->{
                departmentList.add(DepartmentConvertor.toEntity(t));
            });
            return departmentList;
        }
        return null;
    }
}
