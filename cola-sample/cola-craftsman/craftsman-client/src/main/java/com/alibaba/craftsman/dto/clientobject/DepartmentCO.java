package com.alibaba.craftsman.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import lombok.Data;

@Data
public class DepartmentCO extends ClientObject {

    private String orgName;

    private String parentId;

    private Integer employees;

    private Boolean isShow = Boolean.TRUE;

    private String cropId;

}
