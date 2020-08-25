package com.alibaba.craftsman.tunnel.database;

import com.alibaba.craftsman.tunnel.database.dataobject.DepartmentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentTunnel {

    void create(DepartmentDO departmentDO);

    List<DepartmentDO> listByCropId(@Param("cropId") String corpId, @Param("includeDeleted") String includeDeleted);

}
