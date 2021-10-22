package com.erha.CRM.workbench.dao;

import com.erha.CRM.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranHistoryDao {
    Integer insertTranHistory(TranHistory tranHistory);

    List<Map<String, Object>> queryStageCount();
}
