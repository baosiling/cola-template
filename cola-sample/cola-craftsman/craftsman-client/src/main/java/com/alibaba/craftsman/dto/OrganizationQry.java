package com.alibaba.craftsman.dto;

import com.alibaba.cola.dto.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationQry extends Query {

    private String corpId;

    private boolean includeDelete;

}
