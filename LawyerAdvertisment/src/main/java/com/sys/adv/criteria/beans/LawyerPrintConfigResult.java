package com.sys.adv.criteria.beans;

import java.util.List;

public class LawyerPrintConfigResult {
	private List<String> includedCitiesNames;
	private List<String> excludedCitiesNames;
	private List<String> zipCodes;
	private List<String> customersLastNames;
	private int distance;
	/**
	 * @return the includedCitiesNames
	 */
	public List<String> getIncludedCitiesNames() {
		return includedCitiesNames;
	}
	/**
	 * @param includedCitiesNames the includedCitiesNames to set
	 */
	public void setIncludedCitiesNames(List<String> includedCitiesNames) {
		this.includedCitiesNames = includedCitiesNames;
	}
	/**
	 * @return the excludedCitiesNames
	 */
	public List<String> getExcludedCitiesNames() {
		return excludedCitiesNames;
	}
	/**
	 * @param excludedCitiesNames the excludedCitiesNames to set
	 */
	public void setExcludedCitiesNames(List<String> excludedCitiesNames) {
		this.excludedCitiesNames = excludedCitiesNames;
	}
	/**
	 * @return the zipCodes
	 */
	public List<String> getZipCodes() {
		return zipCodes;
	}
	/**
	 * @param zipCodes the zipCodes to set
	 */
	public void setZipCodes(List<String> zipCodes) {
		this.zipCodes = zipCodes;
	}
	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * @return the customersLastNames
	 */
	public List<String> getCustomersLastNames() {
		return customersLastNames;
	}
	/**
	 * @param customersLastNames the customersLastNames to set
	 */
	public void setCustomersLastNames(List<String> customersLastNames) {
		this.customersLastNames = customersLastNames;
	}
}
