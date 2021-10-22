package com.erha.CRM.workbench.service;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.workbench.domain.Clue;
import com.erha.CRM.workbench.domain.ClueActivityRelation;
import com.erha.CRM.workbench.domain.Tran;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.erha.CRM.workbench.domain.vo.ClueUserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ClueService {

    boolean addClue(Clue clue);

    PageInfo<ClueUserVo> queryCluesVoByPageInto(String pageNo, String pageSize);

    Clue queryClueById(String id);

    Boolean addActivityRelation(String clueId,String activityS);

    List<ClueActivityRelation> queryRelationByClueId(String clueId);

    List<ActivityUserVo> queryActivityUserVoByClueId(String clueId);

    Boolean deleteClueActivityRelationByClueIdAndAcId(String clueId,String activityId);

    Boolean clueConvert(String clueId, User user, String userId, Tran tran);

}
