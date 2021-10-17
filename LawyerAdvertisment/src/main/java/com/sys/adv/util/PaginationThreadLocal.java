package com.sys.adv.util;

/**
 * 
 * @author amjad_darwish
 *
 */
public class PaginationThreadLocal {
	private static ThreadLocal<PaginationData> paginationThreadLocal = new ThreadLocal<PaginationData>();

	public static PaginationData getPaginationData() {
		return paginationThreadLocal.get();
	}
	
	public static void setPaginationData(PaginationData paginationData) {
		paginationThreadLocal.set(paginationData);
	}
	
	public static void unsetPaginationData() {
		paginationThreadLocal.remove();
	}
}
