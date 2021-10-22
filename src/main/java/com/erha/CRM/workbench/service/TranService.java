package com.erha.CRM.workbench.service;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.workbench.domain.Tran;
import com.erha.CRM.workbench.domain.vo.TranUserActivityVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TranService {

    PageInfo<TranUserActivityVo> queryTranUserActivityVo(Map<String,String> stage2Possibility, String pageNo, String pageSize);

    TranUserActivityVo queryTranUserActivityVoById(Map<String,String> stage2Possibility, String id);

    TranUserActivityVo updateTranStage(Tran tran, User user);

    List<Map<String,Object>> queryStageCount();
}
