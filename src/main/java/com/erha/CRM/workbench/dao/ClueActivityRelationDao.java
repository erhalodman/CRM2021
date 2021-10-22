package com.erha.CRM.workbench.dao;


import com.erha.CRM.workbench.domain.ClueActivityRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueActivityRelationDao {

	Integer insertRelation(ClueActivityRelation clueActivityRelation);

	List<ClueActivityRelation> selectRelationByClueId(@Param("clueId") String clueId);

	Integer deleteRelationByClueIdAndActivityId(@Param("clueId") String clueId,@Param("activityId") String activityId);

	Integer deleteRelationByClueId(@Param("clueId")String clueId);

}
