/**
 * 
 */
package com.sys.adv.model.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author amjadd
 *
 */
@Entity
@Table(name="TEMPLATE_CONTENT")
@TableGenerator(name = "TEMPLATE_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "TEMPLATE_SEQ", allocationSize = 10)
public class LawyerTemplateContent {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "TEMPLATE_SEQ_GEN")
	@Column(name="ID")
	private long id;
	
	@Column(name="CONTENT", columnDefinition = "TEXT(65535)")
	private String content;

	@OneToOne(mappedBy = "template")
	private LawyerBean lawyer;
	
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

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the lawyer
	 */
	public LawyerBean getLawyer() {
		return lawyer;
	}

	/**
	 * @param lawyer the lawyer to set
	 */
	public void setLawyer(LawyerBean lawyer) {
		this.lawyer = lawyer;
	}
}
