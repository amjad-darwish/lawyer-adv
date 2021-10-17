/**
 * 
 */
package com.sys.adv.model.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author amjad_darwish
 *
 */
@Entity
@Table(name="GROUP_PRINTED_RECORD")
@TableGenerator(name = "GROUP_PRINTED_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "GROUP_PRINTED_SEQ", allocationSize = 10)
public class GroupPrintedBean {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "GROUP_PRINTED_SEQ_GEN")
	@Column(name="ID")
	private long id;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
}
