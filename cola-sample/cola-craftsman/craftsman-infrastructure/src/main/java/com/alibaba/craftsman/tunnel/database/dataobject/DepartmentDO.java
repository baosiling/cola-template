package com.alibaba.craftsman.tunnel.database.dataobject;

import lombok.Data;

@Data
public class DepartmentDO {
    private String orgName;

    private String parentId;

    private Integer employees;

    /**
     * n 未删除
     * y 已删除
     */
    private String isDeleted;

    private String cropId;
}
