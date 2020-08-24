package com.alibaba.craftsman.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.craftsman.dto.OrganizationQry;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;


public interface OrganizationServiceI {
    /**
     * 通过企业ID查询部门列表
     * @param organizationQry
     * @return
     */
    MultiResponse<DepartmentCO> listDepartmentBy(OrganizationQry organizationQry);
}
