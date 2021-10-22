package com.erha.CRM.workbench.dao;


import com.erha.CRM.workbench.domain.Clue;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.erha.CRM.workbench.domain.vo.ClueUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClueDao {

	Integer insertInToClue(Clue clue);

	List<Clue> selectClues();

	List<ClueUserVo> selectClueUserVo();

	Clue selectCluesById(@Param("id")String id);

	List<ActivityUserVo> queryActivityUserVo(@Param("clueId") String clueId);

	Integer deleteClueById(@Param("id") String id);

}
