/**
 * 
 */
package com.sys.adv.model.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

/**
 * @author amjad_darwish
 *
 */
@Entity
@Table(name = "LAWYER")
@TableGenerator(name = "LAWYER_SEQ_GEN", table = "PK_SEQUENCES", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_VAL", pkColumnValue = "LAWYER_SEQ", allocationSize = 10)
public class LawyerBean {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "LAWYER_SEQ_GEN")
	@Column(name = "ID")
	private long id;

	@Column(name = "NAME")
	private String name;

	@OneToMany(mappedBy = "lawyer", fetch = FetchType.EAGER)
	@Where(clause = "deleted = 'F'")
	private List<Office> offices;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TEMPLATE_CONTENT_FK_ID")
	private LawyerTemplateContent template;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BACKGROUND_FK_ID")
	private LawyerBackground background;

	@Column(name = "USE_BACKGROUND")
	@Type(type = "true_false")
	private boolean useBackground;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRINT_CONFIG_FK")
	private PrintConfig printConfig;

	@Column(name = "DELETED")
	@Type(type = "true_false")
	private boolean deleted;
	
	@OneToMany(mappedBy = "lawyerBean")
	private List<PrintedPoliceRecordBean> printedPoliceRecords;
	
	@Column(name = "USE_POLICE_REPORT")
	@Type(type = "true_false")
	private boolean usePoliceReport;
	
	@Column(name = "NO_OF_PAGES_TO_USE")
	private int noOfPoliceReportPages;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the offices
	 */
	public List<Office> getOffices() {
		return offices;
	}

	/**
	 * @param offices the offices to set
	 */
	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	/**
	 * @return the template
	 */
	public LawyerTemplateContent getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(LawyerTemplateContent template) {
		this.template = template;
	}

	/**
	 * @return the background
	 */
	public LawyerBackground getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(LawyerBackground background) {
		this.background = background;
	}

	/**
	 * @return the useBackground
	 */
	public boolean isUseBackground() {
		return useBackground;
	}

	/**
	 * @param useBackground the useBackground to set
	 */
	public void setUseBackground(boolean useBackground) {
		this.useBackground = useBackground;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the printConfig
	 */
	public PrintConfig getPrintConfig() {
		return printConfig;
	}

	/**
	 * @param printConfig the printConfig to set
	 */
	public void setPrintConfig(PrintConfig printConfig) {
		this.printConfig = printConfig;
	}

	/**
	 * @return the printedPoliceRecords
	 */
	public List<PrintedPoliceRecordBean> getPrintedPoliceRecords() {
		return printedPoliceRecords;
	}

	/**
	 * @param printedPoliceRecords the printedPoliceRecords to set
	 */
	public void setPrintedPoliceRecords(List<PrintedPoliceRecordBean> printedPoliceRecords) {
		this.printedPoliceRecords = printedPoliceRecords;
	}

	/**
	 * @return the noOfPoliceReportPages
	 */
	public int getNoOfPoliceReportPages() {
		return noOfPoliceReportPages;
	}

	/**
	 * @param noOfPoliceReportPages the noOfPoliceReportPages to set
	 */
	public void setNoOfPoliceReportPages(int noOfPoliceReportPages) {
		this.noOfPoliceReportPages = noOfPoliceReportPages;
	}

	/**
	 * @return the usePoliceReport
	 */
	public boolean isUsePoliceReport() {
		return usePoliceReport;
	}

	/**
	 * @param usePoliceReport the usePoliceReport to set
	 */
	public void setUsePoliceReport(boolean usePoliceReport) {
		this.usePoliceReport = usePoliceReport;
	}
}
