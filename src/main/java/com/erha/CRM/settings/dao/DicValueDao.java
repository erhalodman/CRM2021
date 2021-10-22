package com.erha.CRM.settings.dao;

import com.erha.CRM.settings.domain.DicValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DicValueDao {

    List<DicValue> selectDicValuesByTypeCode(@Param("typeCode") String typeCode);

}
