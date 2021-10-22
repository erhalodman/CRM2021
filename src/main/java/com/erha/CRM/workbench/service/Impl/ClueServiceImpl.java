package com.erha.CRM.workbench.service.Impl;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.util.UUIDUtil;
import com.erha.CRM.workbench.dao.*;
import com.erha.CRM.workbench.domain.*;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.erha.CRM.workbench.domain.vo.ClueUserVo;
import com.erha.CRM.workbench.service.ClueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassNameClueServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1510:23
 * @Version 1.0
 **/
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public boolean addClue(Clue clue) {
        Integer integer = clueDao.insertInToClue(clue);
        if(integer > 0){
            return true;
        }
        return false;
    }

    @Override
    public PageInfo<ClueUserVo> queryCluesVoByPageInto(String pageNo,String pageSize) {
        PageHelper.startPage(Integer.parseInt(pageNo),Integer.parseInt(pageSize));
        List<ClueUserVo> clues = clueDao.selectClueUserVo();
        PageInfo<ClueUserVo> cluePageInfo = new PageInfo<>(clues);
        return cluePageInfo;
    }

    @Override
    public Clue queryClueById(String id) {
        return clueDao.selectCluesById(id);
    }

    @Override
    public Boolean addActivityRelation(String clueId,String activityS) {
        String[] split = activityS.split("/");
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        int flag = 0;
        for(String activityId: split){
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(activityId);
            clueActivityRelation.setClueId(clueId);
            Integer integer = clueActivityRelationDao.insertRelation(clueActivityRelation);
            if(integer>0){
                flag++;
            }
        }
        if(flag != split.length){
            throw new RuntimeException(clueId +"Clue关系添加失败");
        }
        return true;
    }

    @Override
    public List<ClueActivityRelation> queryRelationByClueId(String clueId) {
        return clueActivityRelationDao.selectRelationByClueId(clueId);
    }

    @Override
    public List<ActivityUserVo> queryActivityUserVoByClueId(String clueId) {
        return clueDao.queryActivityUserVo(clueId);
    }

    @Override
    public Boolean deleteClueActivityRelationByClueIdAndAcId(String clueId,String activityId) {
        Integer integer = clueActivityRelationDao.deleteRelationByClueIdAndActivityId(clueId, activityId);
        if(integer>0){
            return true;
        }
        return false;
    }

    /**
     * @Author 二哈老头子
     * @Description //TODO 线索转换
     * @Date 19:23 2021/10/17
     * @Param [clueId, user, userId, tran]
     * @return java.lang.Boolean
     **/
    @Override
    public Boolean clueConvert(String clueId, User user, String userId, Tran tran) {
        Clue clue = clueDao.selectCluesById(clueId);
        Customer customer = customerDao.selectCustomer(clue.getCompany(), clue.getWebsite(), clue.getPhone());
        //判断该客户是否存在
        if(customer == null){
            Customer newCustomer = new Customer(UUIDUtil.getUUID(),userId,clue.getCompany(),clue.getWebsite(),clue.getPhone(),user.getName(),DateTimeUtil.getSysTime(),null,null,clue.getContactSummary(),clue.getNextContactTime(),clue.getDescription(),clue.getAddress());
            Integer integer = customerDao.insertCustomer(newCustomer);
            if(integer<1){
                throw new RuntimeException("添加客户失败");
            }
            Contacts contacts = new Contacts(UUIDUtil.getUUID(), userId, clue.getSource(), newCustomer.getId(), clue.getFullname(), clue.getAppellation(), clue.getEmail(), clue.getMphone(), clue.getJob(), null, user.getName(), DateTimeUtil.getSysTime(), null, null, clue.getDescription(), clue.getContactSummary(), clue.getNextContactTime(), clue.getAddress());
            integer = contactsDao.insertContacts(contacts);
            if(integer<1){
                throw new RuntimeException("添加联系人失败");
            }
            //将线索备注转换为客户备注和联系人备注
            List<ClueRemark> clueRemarks = clueRemarkDao.selectClueRemarkByClueId(clueId);
            Iterator<ClueRemark> iterator = clueRemarks.iterator();
            while(iterator.hasNext()){
                ClueRemark clueRemark = iterator.next();
                customerRemarkDao.insertCustomerRemark(new CustomerRemark(UUIDUtil.getUUID(), clueRemark.getNoteContent(), clueRemark.getCreateTime(),clueRemark.getCreateBy(),clueRemark.getEditTime(),clueRemark.getEditBy(),clueRemark.getEditFlag(),newCustomer.getId()));
                contactsRemarkDao.insertContactsRemark(new ContactsRemark(UUIDUtil.getUUID(), clueRemark.getNoteContent(), clueRemark.getCreateTime(),clueRemark.getCreateBy(),clueRemark.getEditTime(),clueRemark.getEditBy(),clueRemark.getEditFlag(),contacts.getId()));
            }
            //将"线索和市场活动"的关系转化到"联系人和市场活动"的关系
            List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.selectRelationByClueId(clueId);
            Iterator<ClueActivityRelation> clueActivityRelationsIterator = clueActivityRelations.iterator();
            while(clueActivityRelationsIterator.hasNext()){
                ClueActivityRelation clueActivityRelation = clueActivityRelationsIterator.next();
                contactsActivityRelationDao.insertRelation(new ContactsActivityRelation(UUIDUtil.getUUID(), contacts.getId(), clueActivityRelation.getActivityId()));
            }
            //如果交易不为空
            if(!tran.getStage().isEmpty()){
                System.out.println("---------------------生成交易---------------------");
                tran.setId(UUIDUtil.getUUID());
                tran.setOwner(user.getId());
                tran.setCustomerId(newCustomer.getId());
                tran.setSource(clue.getSource());
                tran.setContactsId(contacts.getId());
                tran.setCreateBy(user.getName());
                tran.setCreateTime(DateTimeUtil.getSysTime());
                tran.setDescription(clue.getDescription());
                tran.setContactSummary(clue.getContactSummary());
                tran.setNextContactTime(clue.getNextContactTime());
                tranDao.insertTran(tran);
                tranHistoryDao.insertTranHistory(new TranHistory(UUIDUtil.getUUID(), tran.getStage(), tran.getMoney(), tran.getExpectedDate(), DateTimeUtil.getSysTime(), user.getName(), tran.getId()));
            }
            //请求线索
            clueDao.deleteClueById(clueId);
            clueRemarkDao.deleteClueRemark(clueId);
            clueActivityRelationDao.deleteRelationByClueId(clueId);
            return true;
        }
        return false;
    }
}
