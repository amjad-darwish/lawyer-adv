package com.sys.adv.util;

import java.io.Serializable;

/**
 * 
 * @author adarwish
 *
 */
public class PaginationData implements Serializable {
	private static final long serialVersionUID = 7839482094967950636L;
	private int targetedPage;
	private int recordsPerPage;
	/**
	 * @return the targetedPage
	 */
	public int getTargetedPage() {
		return targetedPage;
	}
	/**
	 * @param targetedPage the targetedPage to set
	 */
	public void setTargetedPage(int targetedPage) {
		this.targetedPage = targetedPage;
	}
	/**
	 * @return the recordsPerPage
	 */
	public int getRecordsPerPage() {
		return recordsPerPage;
	}
	/**
	 * @param recordsPerPage the recordsPerPage to set
	 */
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString() {
		final String COMMA = ",";
		final String COLON = ":";
		    
		StringBuilder retValue = new StringBuilder();
		    
		retValue.append("PaginationData ( ")
				.append(super.toString())
		    	.append(COLON)
		    	.append("targetedPage = ").append(this.targetedPage).append(COMMA)
		    	.append("recordsPerPage = ").append(this.recordsPerPage)
		    	.append(" )");
	
		return retValue.toString();
	}
	
	
}
