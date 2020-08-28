/*
 * ISMSDAO.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */

package com.sourcetrace.eses.dao;

import java.util.List;

import com.ese.entity.sms.ForecastSMSHistory;
import com.ese.entity.sms.SMSHistory;
import com.ese.entity.sms.SmsGroupDetail;
import com.ese.entity.sms.SmsGroupHeader;
import com.ese.entity.sms.SmsTemplate;
import com.sourcetrace.eses.entity.Agent;
import com.sourcetrace.eses.umgmt.entity.User;
import com.sourcetrace.esesw.entity.profile.Farmer;

/**
 * @author PANNEER
 */
public interface ISMSDAO extends IESEDAO {

    /**
     * List sms history by status.
     * @param status the status
     * @return the list
     */
    public List<SMSHistory> listSMSHistoryByStatus(List<String> status);

    /**
     * Find sms history by id.
     * @param id the id
     * @return the sMS history
     */
    public SMSHistory findSmsHistoryById(long id);

    public SmsGroupHeader findGroupByName(String name);

    public SmsGroupHeader findGroupHeaderById(long id);

    public List<Object[]> listSmsGroupHeader();

    public SmsTemplate findSmsTemplateByName(String name);

    public SmsTemplate findSmsTemplateById(long id);

    public List<SmsTemplate> listSmsTemplates();

    public List<SmsGroupHeader> listGroupHeadersByIds(List<String> ids);
    
    public long findGroupsMobileNumbersCount(List<String> groupIds);
    
    public List<Object> listFarmers(String sort,int startIndex,int limit,Farmer farmer);
    
    public Integer findFarmerMobileNumberCount(String sort, int startIndex, int limit, Farmer farmer);
    
    public Integer findAgentMobileNumberCount(String sort,int startIndex,int limit,Agent agent);
    
    public Integer findUserMobileNumberCount(String sort, int startIndex, int limit, User user);
    
    public List<Object> listAgents(String sort,int startIndex,int limit,Agent agent);
    
    public List<Object> listUsers(String sort,int startIndex,int limit,User user);

	public void addForecastSMSHistory(ForecastSMSHistory smsHistory);
    
   /* public Integer listMappedMobileNumberCountByGroup(long[] ids);*/
	
	public List<SmsGroupDetail> listGroupDetailByGroupId(List<String> ids);
}
