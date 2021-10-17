/**
 * 
 */
package com.sys.adv.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author amjad_darwish
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Pageable {
	int pageSize() default 30;
}
