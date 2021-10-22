package com.erha.CRM.workbench.web.controller;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.settings.service.UserService;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.util.UUIDUtil;
import com.erha.CRM.workbench.domain.Clue;
import com.erha.CRM.workbench.domain.ClueActivityRelation;
import com.erha.CRM.workbench.domain.Tran;
import com.erha.CRM.workbench.domain.vo.ActivityUserVo;
import com.erha.CRM.workbench.domain.vo.ClueUserVo;
import com.erha.CRM.workbench.service.ClueService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @ClassNameClueController
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1510:26
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = {"/clue"})
public class ClueController {
    @Autowired
    private ClueService clueService;

    @RequestMapping(value = {"/addClue.action"})
    @ResponseBody
    public Boolean addClue(Clue clue, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateBy(user.getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        return clueService.addClue(clue);
    }

    @RequestMapping(value = {"/queryPage.action"})
    @ResponseBody
    public PageInfo<ClueUserVo> queryClues(String pageNo, String pageSize){
        PageInfo<ClueUserVo> cluePageInfo = clueService.queryCluesVoByPageInto(pageNo, pageSize);
        return cluePageInfo;
    }

    @RequestMapping(value = {"/queryClueById.action"})
    public ModelAndView queryClueByIdToDetail(String id,String username){
        Clue clue = clueService.queryClueById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("clue", clue);
        modelAndView.addObject("username", username);
        modelAndView.setViewName("forward:/workbench/clue/detail.jsp");
        return modelAndView;
    }

    @RequestMapping(value = {"/addActivityRelation.action"})
    @ResponseBody
    public Boolean addClueActivityRelation(String clueId,String activityS){
        Boolean aBoolean = clueService.addActivityRelation(clueId, activityS);
        return aBoolean;
    }

    @RequestMapping(value = {"/queryRelations.action"})
    @ResponseBody
    public List<ClueActivityRelation> queryRelations(String clueId){
        return clueService.queryRelationByClueId(clueId);
    }

    @RequestMapping(value = {"/queryActivityRelationByClueId.action"})
    @ResponseBody
    public List<ActivityUserVo> queryActivityRelationsByClueId(String clueId){
        return clueService.queryActivityUserVoByClueId(clueId);
    }

    @RequestMapping(value = {"/queryActivityRelationCondition.action"})
    @ResponseBody
    public List<ActivityUserVo> queryActivityRelationsByClueIdCondition(String clueId,String condition){
        return clueService.queryActivityUserVoByClueId(clueId);
    }

    @RequestMapping(value = {"/deleteRelationById.action"})
    @ResponseBody
    public Boolean deleteRelationById(String clueId,String activityId){
        return clueService.deleteClueActivityRelationByClueIdAndAcId(clueId, activityId);
    }


    @RequestMapping(value = {"/convert.action"}, method = RequestMethod.POST)
    public ModelAndView clueConvert(String clueId, String userId, Tran tran, HttpServletRequest request){
        System.out.println("clueId :"+clueId);
        System.out.println("userId :"+userId);
        System.out.println("tran :"+tran);
        User user = (User) request.getSession().getAttribute("user");
        clueService.clueConvert(clueId, user, userId, tran);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/workbench/clue/index.jsp");
        return modelAndView;
    }





}
