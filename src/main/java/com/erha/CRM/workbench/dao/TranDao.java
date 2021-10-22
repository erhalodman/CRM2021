package com.erha.CRM.workbench.dao;

import com.erha.CRM.workbench.domain.Tran;
import com.erha.CRM.workbench.domain.vo.TranUserActivityVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TranDao {
    Integer insertTran(Tran tran);

    List<TranUserActivityVo> selectTranUserActivityVo();

    TranUserActivityVo selectTranUserActivityVoById(@Param("id") String id);

    Integer updateStage(Tran tran);

}
