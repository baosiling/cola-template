package com.alibaba.craftsman.domain.organization;

import com.alibaba.cola.domain.EntityObject;
import lombok.Data;

@Data
public class Department extends EntityObject {

    private String orgName;

    private String parentId;

    private Integer employees;

    private Boolean isShow = Boolean.TRUE;

    private String cropId;

}
