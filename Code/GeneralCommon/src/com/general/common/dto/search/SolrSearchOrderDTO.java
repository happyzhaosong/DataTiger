package com.general.common.dto.search;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BaseDTO;

/*
 * Record solr search result order parameters.
 * */
public class SolrSearchOrderDTO{

	private String orderColumn = "";
	
	private String direction = GeneralConstants.ORDER_BY_DESC;

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
