package com.alibaba.craftsman.dto;

import com.alibaba.cola.dto.Command;
import com.alibaba.craftsman.dto.clientobject.DepartmentCO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentAddCmd extends Command {

    @NotNull
    private DepartmentCO departmentCO;

}
