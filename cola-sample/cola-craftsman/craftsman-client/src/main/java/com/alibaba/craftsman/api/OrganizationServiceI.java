package com.alibaba.craftsman.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.craftsman.dto.DepartmentAddCmd;
import com.alibaba.craftsman.dto.OrganizationQry;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;


public interface OrganizationServiceI {
    /**
     * 通过企业ID查询部门列表
     * @param organizationQry
     * @return
     */
    MultiResponse<DepartmentCO> listDepartmentBy(OrganizationQry organizationQry);

    /**
     * 添加部门
     * @param departmentAddCmd
     * @return
     */
    Response addDepartment(DepartmentAddCmd departmentAddCmd);
}
