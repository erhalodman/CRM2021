package com.erha.CRM.workbench.service.Impl;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.util.UUIDUtil;
import com.erha.CRM.workbench.dao.TranDao;
import com.erha.CRM.workbench.dao.TranHistoryDao;
import com.erha.CRM.workbench.domain.Tran;
import com.erha.CRM.workbench.domain.TranHistory;
import com.erha.CRM.workbench.domain.vo.TranUserActivityVo;
import com.erha.CRM.workbench.service.TranService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameTranServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1813:41
 * @Version 1.0
 **/
@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public PageInfo<TranUserActivityVo> queryTranUserActivityVo(Map<String,String> stage2Possibility, String pageNo, String pageSize) {
        PageHelper.startPage(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
        List<TranUserActivityVo> tranUserActivityVos = tranDao.selectTranUserActivityVo();
        Iterator<TranUserActivityVo> iterator = tranUserActivityVos.iterator();
        while(iterator.hasNext()){
            TranUserActivityVo next = iterator.next();
            next.setPossibility(stage2Possibility.get(next.getStage()));
        }
        PageInfo<TranUserActivityVo> tranUserActivityVoPageInfo = new PageInfo<>(tranUserActivityVos);
        return tranUserActivityVoPageInfo;
    }

    @Override
    public TranUserActivityVo queryTranUserActivityVoById(Map<String,String> stage2Possibility,String id) {
        TranUserActivityVo tranUserActivityVo = tranDao.selectTranUserActivityVoById(id);
        tranUserActivityVo.setPossibility(stage2Possibility.get(tranUserActivityVo.getStage()));
        return tranUserActivityVo;
    }

    @Override
    public TranUserActivityVo updateTranStage(Tran tran, User user) {
        Integer integer = tranDao.updateStage(tran);
        if(integer>0){
            TranUserActivityVo tranUserActivityVo = tranDao.selectTranUserActivityVoById(tran.getId());
            Integer insert = tranHistoryDao.insertTranHistory(new TranHistory(UUIDUtil.getUUID(), tranUserActivityVo.getStage(), tranUserActivityVo.getMoney(), tranUserActivityVo.getExpectedDate(), DateTimeUtil.getSysTime(), user.getName(), tranUserActivityVo.getId()));
            if(insert<1){
                throw new RuntimeException("创建交易历史失败");
            }
            return tranUserActivityVo;
        }else{
            throw new RuntimeException("修改交易阶段失败");
        }
    }

    @Override
    public List<Map<String, Object>> queryStageCount() {
        List<Map<String, Object>> stageCount = tranHistoryDao.queryStageCount();
        return stageCount;
    }
}
