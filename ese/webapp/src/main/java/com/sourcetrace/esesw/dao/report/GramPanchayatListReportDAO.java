/*
 * GramPanchayatListReportDAO.java
 * Copyright (c) 2014-2015, SourceTrace Systems, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of SourceTrace Systems
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * SourceTrace Systems.
 */
package com.sourcetrace.esesw.dao.report;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.util.ObjectUtil;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.esesw.entity.profile.GramPanchayat;

public class GramPanchayatListReportDAO extends ReportDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ese.dao.ReportDAO#addExampleFiltering(org.hibernate.Criteria,
	 * java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		GramPanchayat entity = (GramPanchayat) params.get(FILTER);

		if (entity != null) {
			criteria.createAlias("city", "c");
			criteria.createAlias("c.locality", "l");
			criteria.createAlias("l.state", "s");
			criteria.createAlias("s.country", "country");
			
			if (entity.getCode() != null && !"".equals(entity.getCode()))
				criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));

			if (entity.getName() != null && !"".equals(entity.getName()))
				criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));

			if (!ObjectUtil.isEmpty(entity.getCity()) && !StringUtil.isEmpty(entity.getCity().getName()))
				criteria.add(Restrictions.like("c.name", entity.getCity().getName(), MatchMode.ANYWHERE));
			
			if (entity.getCity() != null && entity.getCity().getLocality() != null
					&& entity.getCity().getLocality().getName() != null
					&& !"".equals(entity.getCity().getLocality().getName()))
				criteria.add(Restrictions.like("l.name", entity.getCity().getLocality().getName(), MatchMode.ANYWHERE));
			
			if (entity.getCity() != null && entity.getCity().getLocality() != null
					&& entity.getCity().getLocality().getState() != null
					&& entity.getCity().getLocality().getState().getName() != null
					&& !"".equals(entity.getCity().getLocality().getState().getName()))
				criteria.add(Restrictions.like("s.name", entity.getCity().getLocality().getState().getName(),
						MatchMode.ANYWHERE));
			
			if (entity.getCity() != null && entity.getCity().getLocality() != null
					&& entity.getCity().getLocality().getState()!= null
					&& entity.getCity().getLocality().getState().getCountry() != null
					&& entity.getCity().getLocality().getState().getCountry().getName() != null
					&& !"".equals(entity.getCity().getLocality().getState().getCountry().getName()))
				criteria.add(Restrictions.like("country.name", entity.getCity().getLocality().getState().getCountry().getName(),
						MatchMode.ANYWHERE));
			
			
			if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
				criteria.add(Restrictions.in("branchId", entity.getBranchesList()));
			}

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

			String dir = (String) params.get(DIR);
			String sort = (String) params.get(SORT_COLUMN);
			if (dir != null && sort != null) {
				if (dir.equals(DESCENDING)) {
					criteria.addOrder(Order.desc(sort));
				} else {
					criteria.addOrder(Order.asc(sort));
				}
			}

		}
	}
}
