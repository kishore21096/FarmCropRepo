package com.sourcetrace.esesw.dao.report;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ese.entity.txn.training.TrainingMaterial;
import com.sourcetrace.eses.dao.ReportDAO;
import com.sourcetrace.eses.util.StringUtil;

public class TrainingMaterialDAO extends ReportDAO {

	protected void addExampleFiltering(Criteria criteria, Map params) {
		// check for filter entity
		TrainingMaterial entity = (TrainingMaterial) params.get(FILTER);

		if (entity != null) {

			/*
			 * if (!ObjectUtil.isListEmpty(entity.getBranchesList())) {
			 * criteria.add(Restrictions.in("branchId",
			 * entity.getBranchesList())); }
			 */

			if (!StringUtil.isEmpty(entity.getBranchId())) {
				criteria.add(Restrictions.eq("branchId", entity.getBranchId()));
			}

			if (entity.getName() != null && !"".equals(entity.getName()))
				criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
			if (entity.getCode() != null && !"".equals(entity.getCode()))
				criteria.add(Restrictions.like("code", entity.getCode(), MatchMode.ANYWHERE));

			// sorting direction
			String dir = (String) params.get(DIR);
			// sorting column
			String sort = (String) params.get(SORT_COLUMN);
			// sort a column in the given direction ascending/descending
			if (dir != null && sort != null) {
				Criteria sortCriteria = null;
				if (sort.contains(DELIMITER)) {
					sort = sort.substring(sort.lastIndexOf(DELIMITER) + 1);
					if (dir.equals(DESCENDING)) {
						// sort descending
						sortCriteria.addOrder(Order.desc(sort));
					} else {
						// sort ascending
						sortCriteria.addOrder(Order.asc(sort));
					}
				}
			}
		}
	}
}
