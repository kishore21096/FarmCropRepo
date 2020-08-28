package com.sourcetrace.esesw.dao.report;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.util.StringUtil;
import com.sourcetrace.esesw.entity.profile.GinnerQuantitySold;

public class GinnerQuantitySoldReportDAO  extends ReportDAO
{
	
	protected void addExampleFiltering(Criteria criteria, Map params) {

		// check for filter entity
		GinnerQuantitySold entity = (GinnerQuantitySold) params.get(FILTER);
		if (entity != null) {

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

			if (entity.getSeasonCode() != null && !"".equals(entity.getSeasonCode()))
				criteria.add(Restrictions.like("seasonCode", entity.getSeasonCode(), MatchMode.ANYWHERE));

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
