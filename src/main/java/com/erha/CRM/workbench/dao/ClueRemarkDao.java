package com.erha.CRM.workbench.dao;

import com.erha.CRM.workbench.domain.ClueRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> selectClueRemarkByClueId(@Param("clueId") String clueId);

    Integer deleteClueRemark(@Param("clueId") String clueId);
}
