/**
 * 
 */
package com.sys.adv.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.sys.adv.annotation.Pageable;
import com.sys.adv.criteria.beans.LawyerPrintConfigResult;
import com.sys.adv.criteria.beans.UnPrintedPoliceRecordSearchCriteriaDTO;
import com.sys.adv.exceptions.AdvException;
import com.sys.adv.model.beans.GroupPrintedBean;
import com.sys.adv.model.beans.LawyerBean;
import com.sys.adv.model.beans.Office;
import com.sys.adv.model.beans.PoliceRecordBean;
import com.sys.adv.model.beans.PrintConfig;
import com.sys.adv.model.beans.PrintedPoliceRecordBean;
import com.sys.adv.model.dao.GeneralDAO;
import com.sys.adv.model.dao.LawyerDao;
import com.sys.adv.model.dao.PrintConfigDao;
import com.sys.adv.util.Constants;
import com.sys.adv.util.ParameterUtil;

import uk.org.okapibarcode.backend.HumanReadableLocation;
import uk.org.okapibarcode.backend.Postnet;
import uk.org.okapibarcode.output.Java2DRenderer;

/**
 * @author amjad_darwish
 *
 */
@RequestMapping(value = "/printLetters")
@SessionAttributes(names = "unprintedSearchCriteria")
@Controller
public class PrintLettersContoller extends GeneralAdvController {
	private static final Logger logger = LogManager.getLogger(PrintLettersContoller.class.getName());

	private static final String FILE_SEPERATOR = System.getProperty("file.separator");

	private static final SimpleDateFormat ddmmyyyyFormatter = new SimpleDateFormat("ddMMyyyy");
	private static final SimpleDateFormat printedDateFormatter = new SimpleDateFormat("MM/dd/yy");

	@Autowired
	private LawyerDao lawyerDao;

	@Autowired
	private PrintConfigDao configDao;

	@ModelAttribute("modelAndView")
	@RequestMapping("/viewSearchForm")
	public ModelAndView viewPrintLettersForm(ModelAndView modelAndView) {
		try {
			List<LawyerBean> lawyerBeans = lawyerDao.listLawyersForPrintLetters();

			modelAndView.addObject("lawyers", lawyerBeans);

			modelAndView.setViewName("ViewPrintLettersSearchForm");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@Pageable(pageSize = 50)
	@RequestMapping(value = "/listUnprintedRecord", method = RequestMethod.GET)
	public ModelAndView listUnprintedRecord(ModelAndView modelAndView, @RequestParam("lawyerId") long lawyerId,
			UnPrintedPoliceRecordSearchCriteriaDTO unprintedSearchCriteriaDTO) {
		try {
			List<PoliceRecordBean> unPrintedPoliceRecords = getGeneralDao()
					.listUnprintedPoliceRecord(unprintedSearchCriteriaDTO, configDao.getByLawyerId(lawyerId));

			addTotalNoOfresults(modelAndView, getGeneralDao().countUnprintedPoliceRecord(unprintedSearchCriteriaDTO,
					configDao.getByLawyerId(lawyerId)));

			modelAndView.addObject("unprintedPoliceRecords", unPrintedPoliceRecords);
			modelAndView.addObject("lawyerId", lawyerId);
			modelAndView.addObject("unprintedSearchCriteria", unprintedSearchCriteriaDTO);

			modelAndView.setViewName("listUnprintedPoliceRecords");
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	@Pageable(pageSize = 1)
	@RequestMapping(value = "/getPage", method = RequestMethod.GET)
	public ModelAndView getPage(ModelAndView modelAndView, @RequestParam("lawyerId") long lawyerId,
			@ModelAttribute(name = "unprintedSearchCriteria") UnPrintedPoliceRecordSearchCriteriaDTO unprintedSearchCriteriaDTO) {
		return listUnprintedRecord(modelAndView, lawyerId, unprintedSearchCriteriaDTO);
	}

	@RequestMapping(value = "/downloadToPrint", method = RequestMethod.POST)
	public ModelAndView downloadToPrint(@ModelAttribute("modelAndView") ModelAndView modelAndView,
			@RequestParam("lawyerId") long lawyerId, @RequestParam("recordId") List<Long> policeRecordIds,
			HttpServletResponse response) {
		try {
			GeneralDAO generalDAO = getGeneralDao();

			GroupPrintedBean groupPrintedBean = new GroupPrintedBean();
			long groupPrintedId = generalDAO.save(groupPrintedBean);

			LawyerBean lawyerBean = generalDAO.get(LawyerBean.class, lawyerId);

			Map<Long, String> faultsCodes = null;
			
			
			List<PoliceRecordBean> unPrintedPoliceRecords = generalDAO.listPoliceRecordsByIds(policeRecordIds);
			
			if (lawyerBean.getId() == Constants.UNDELETABLE_LAWYER_ID) {
				faultsCodes = getAndAssignCodes(unPrintedPoliceRecords);
			} else {
				faultsCodes = Collections.emptyMap();
			}

			String policeRecordPath = ParameterUtil.getParameterValueByName(ParameterUtil.POLICE_REPORT_UPLOAD_PATH);
			String lawyerBackgroundPath = ParameterUtil
					.getParameterValueByName(ParameterUtil.LAWYER_BACKGROUND_UPLOAD_PATH);
			String printedRecordPath = ParameterUtil.getParameterValueByName(ParameterUtil.PRINTED_RECORD_PATH);

			StringBuilder policeRecordFullPath = new StringBuilder(policeRecordPath);
			StringBuilder lawyerBackgroundFullPath = new StringBuilder(lawyerBackgroundPath);
			StringBuilder printedRecordFullPath = new StringBuilder(printedRecordPath);

			if (!policeRecordPath.endsWith(FILE_SEPERATOR)) {
				policeRecordFullPath.append(FILE_SEPERATOR);
			}

			if (!lawyerBackgroundPath.endsWith(FILE_SEPERATOR)) {
				lawyerBackgroundFullPath.append(FILE_SEPERATOR);
			}

			if (!printedRecordPath.endsWith(FILE_SEPERATOR)) {
				printedRecordFullPath.append(FILE_SEPERATOR);
			}

			if (lawyerBean.getBackground() != null && lawyerBean.isUseBackground()) {
				lawyerBackgroundFullPath.append(lawyerBean.getBackground().getName());
			}

			Calendar currentCalendar = Calendar.getInstance();

			String directory = ddmmyyyyFormatter.format(currentCalendar.getTime());

			if (!new File(printedRecordFullPath.toString() + directory + FILE_SEPERATOR + groupPrintedId).exists()) {
				new File(printedRecordFullPath.toString() + directory + FILE_SEPERATOR + groupPrintedId).mkdirs();
			}

			PrintedPoliceRecordBean printedPoliceRecordBean = null;

			printedRecordFullPath.append(directory).append(FILE_SEPERATOR).append(groupPrintedId)
					.append(FILE_SEPERATOR);

			for (PoliceRecordBean policeRecordBean : unPrintedPoliceRecords) {
				String policeRecordFileName = policeRecordBean.getPartPoliceFileName()
						.substring(policeRecordBean.getPartPoliceFileName().indexOf(FILE_SEPERATOR) + 1);

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

				String templateText = replaceHolders(lawyerBean.getTemplate().getContent(),
						sortOffices(lawyerBean.getOffices(), policeRecordBean), policeRecordBean, faultsCodes);

				try (PdfDocument lawyerTemplate = new PdfDocument(new PdfWriter(byteArrayOutputStream))) {
					ConverterProperties converterProperties = new ConverterProperties();
					lawyerTemplate.setDefaultPageSize(PageSize.LETTER);
					HtmlConverter.convertToPdf(templateText, lawyerTemplate, converterProperties);
				}

				try (PdfDocument printedDocument = new PdfDocument(
						new PdfReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())),
						new PdfWriter(printedRecordFullPath.toString() + policeRecordFileName))) {
					printedDocument.setDefaultPageSize(PageSize.LETTER);

					if (lawyerBean.isUseBackground() && lawyerBean.getBackground() != null) {
						for (int page = 1; page <= printedDocument.getNumberOfPages(); page++) {
							PdfCanvas canvas = new PdfCanvas(printedDocument.getPage(page).newContentStreamBefore(),
									printedDocument.getPage(page).getResources(), printedDocument);

							try (PdfDocument background = new PdfDocument(
									new PdfReader(lawyerBackgroundFullPath.toString()))) {
								PdfXObject mergedPage = background.getFirstPage().copyAsFormXObject(printedDocument);
								canvas.addXObject(mergedPage, 0, 0);
							}
						}
					}

					if (lawyerBean.isUsePoliceReport()) {
						try (PdfDocument policeRecordDocument = new PdfDocument(new PdfReader(
								policeRecordFullPath.toString() + policeRecordBean.getPartPoliceFileName()))) {
							if (lawyerBean.getNoOfPoliceReportPages() == 0) {
								policeRecordDocument.copyPagesTo(1, policeRecordDocument.getNumberOfPages(),
										printedDocument);
							} else {
								policeRecordDocument.copyPagesTo(1, Math.min(lawyerBean.getNoOfPoliceReportPages(),
										policeRecordDocument.getNumberOfPages()), printedDocument);
							}
						}
					}

					printedPoliceRecordBean = new PrintedPoliceRecordBean();

					printedPoliceRecordBean.setPoliceRecordBean(policeRecordBean);
					printedPoliceRecordBean.setLawyerBean(lawyerBean);
					printedPoliceRecordBean.setPrintedDate(currentCalendar);

					printedPoliceRecordBean.setFileName(new StringBuilder(directory).append(FILE_SEPERATOR)
							.append(groupPrintedId).append(FILE_SEPERATOR).append(policeRecordFileName).toString());

					printedPoliceRecordBean.setGroupPrintedId(groupPrintedId);

					generalDAO.save(printedPoliceRecordBean);
				}
			}

			modelAndView.addObject("groupPrintedId", groupPrintedId);

			modelAndView.setViewName("DownloadPrintableReport");

			return modelAndView;
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");
		}

		return modelAndView;
	}

	
	private Map<Long, String> getAndAssignCodes(List<PoliceRecordBean> unPrintedPoliceRecords) throws AdvException {
		return getGeneralDao().getAndAssignCodes(unPrintedPoliceRecords);
	}

	@RequestMapping(value = "/print/config", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LawyerPrintConfigResult getPrintConfig(@RequestParam("lawyerId") int lawyerId,
			HttpServletResponse response) {
		try {
			PrintConfig config = configDao.getByLawyerId(lawyerId);

			List<String> includedCitiesNames = getGeneralDao().getCitiesNames(config.getIncludeCitiesIds());
			List<String> excludedCitiesNames = getGeneralDao().getCitiesNames(config.getExcludeCitiesIds());
			List<String> zipCodes = getGeneralDao().getZipCodesByIds(config.getZipCodes());

			LawyerPrintConfigResult result = new LawyerPrintConfigResult();
			result.setIncludedCitiesNames(includedCitiesNames);
			result.setExcludedCitiesNames(excludedCitiesNames);
			result.setZipCodes(zipCodes);
			result.setDistance(config.getDistance());
			result.setCustomersLastNames(config.getCustomersLastNames());

			return result;
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/view")
	@ModelAttribute
	public ModelAndView viewReportRecord(ModelAndView modelAndView, HttpServletResponse response,
			@RequestParam(value = "policeRecordId", defaultValue = "0") long policeRecordId) {
		try {
			PoliceRecordBean policeRecordBean = getGeneralDao().get(PoliceRecordBean.class, policeRecordId);

			loadPoliceRecordPDF(policeRecordBean, response);

			return null;
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");

			return modelAndView;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/downloadPrintableReport")
	@ModelAttribute
	public ModelAndView downloadPrintableReport(ModelAndView modelAndView, HttpServletResponse response,
			HttpServletRequest request, @RequestParam(value = "groupPrintedId") long groupPrintedId)
			throws AdvException, IOException {
		try {
			String printedRecordPath = ParameterUtil.getParameterValueByName(ParameterUtil.PRINTED_RECORD_PATH);

			StringBuilder printedRecordFullPath = new StringBuilder(printedRecordPath);

			if (!printedRecordPath.endsWith(FILE_SEPERATOR)) {
				printedRecordFullPath.append(FILE_SEPERATOR);
			}

			String directory = ddmmyyyyFormatter.format(Calendar.getInstance().getTime());

			printedRecordFullPath.append(directory).append(FILE_SEPERATOR).append(groupPrintedId);

			File printableReports = new File(printedRecordFullPath.toString());

			response.setHeader("Content-Disposition", "attachment;filename=Reports_" + groupPrintedId + ".zip");

			try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {

				byte[] buf = new byte[1024];

				for (String fileName : printableReports.list()) {
					try (InputStream inputStream = new BufferedInputStream(
							new FileInputStream(printedRecordFullPath.toString() + FILE_SEPERATOR + fileName))) {
						zipOutputStream.putNextEntry(new ZipEntry(fileName));

						while (inputStream.read(buf) > 0) {
							zipOutputStream.write(buf);
						}

						zipOutputStream.flush();
					}
				}
			}

			return null;
		} catch (Exception e) {
			logger.error("an error", e);

			modelAndView.setViewName("abnormalError");

			return modelAndView;
		}
	}

	/**
	 * 
	 * @param policeRecordBean
	 * @param response
	 * @throws AdvException
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	private void loadPoliceRecordPDF(PoliceRecordBean policeRecordBean, HttpServletResponse response)
			throws AdvException, IOException {
		String policeReportPath = ParameterUtil.getParameterValueByName(ParameterUtil.POLICE_REPORT_UPLOAD_PATH);

		StringBuilder policeReportFullPath = new StringBuilder(policeReportPath);

		if (!policeReportPath.endsWith(FILE_SEPERATOR)) {
			policeReportFullPath.append(FILE_SEPERATOR);
		}

		policeReportFullPath.append(policeRecordBean.getPartPoliceFileName());

		File file = new File(policeReportFullPath.toString());

		response.setContentType("application/pdf");

		try (PdfDocument source = new PdfDocument(new PdfReader(file));
				PdfDocument destination = new PdfDocument(new PdfWriter(response.getOutputStream()))) {
			source.copyPagesTo(1, source.getNumberOfPages(), destination);
		}
	}

	private static String replaceHolders(String content, List<Office> offices, PoliceRecordBean policeRecordBean, Map<Long, String> faultCodes)
			throws IOException, AdvException {
		if (content == null || content.isBlank()) {
			return content = "<p></p>";
		}

		String printedDate = printedDateFormatter.format(policeRecordBean.getAccidentDate().getTime());
		String generatedReportDate = printedDateFormatter.format(policeRecordBean.getEntryDate().getTime());

		String barCodePath = generateBarCode(policeRecordBean);
		
		content = content.replace("${greeting}", policeRecordBean.getBeneficiaryTitle());
		content = content.replace("${fName}", firstLetterToUpperCase(policeRecordBean.getBeneficiaryFirstName()));
		content = content.replace("${lName}", firstLetterToUpperCase(policeRecordBean.getBeneficiaryLastName()));
		content = content.replace("${city}", policeRecordBean.getCityName());
		content = content.replace("${state}", policeRecordBean.getState());
		content = content.replace("${zipCode}", policeRecordBean.getFullZipCode());
		content = content.replace("${streetAddress}", policeRecordBean.getAddress());
		content = content.replace("${DOA}", printedDate);
		content = content.replace("${COA}", policeRecordBean.getCityBean().getName());
		content = content.replace("${GRD}", generatedReportDate);
		content = content.replace("${barcode}",
				String.format("<img src=\"%s\" alt=\"%s\" />", barCodePath, policeRecordBean.getFullZipCode()));
		
		if (faultCodes.containsKey(policeRecordBean.getId())) {
			content = content.replace("${fault_code}", faultCodes.get(policeRecordBean.getId()));
		}
		
		for (Office office : offices) {
			String add = office.getStreet();

			if (office.getSecondaryNumber() != null && !office.getSecondaryNumber().isEmpty()) {
				add += ", " + office.getSecondaryNumber();
			}

			content = content.replaceFirst("\\$\\{ostate\\}", office.getState());
			content = content.replaceFirst("\\$\\{ocity\\}", office.getCity());
			content = content.replaceFirst("\\$\\{oaddress\\}", add);
			content = content.replaceFirst("\\$\\{ozip\\}", office.getZipCode());
		}

		return content;
	}

	private static String generateBarCode(PoliceRecordBean policeRecordBean) throws IOException, AdvException {
		Postnet barcode = new Postnet();
		barcode.setFontName("Monospaced");
		barcode.setFontSize(12);
		barcode.setModuleWidth(2);
		barcode.setBarHeight(14);
		barcode.setHumanReadableLocation(HumanReadableLocation.NONE);
		barcode.setContent(policeRecordBean.getFullZipCode().replaceAll("[^0-9]", ""));

		int width = barcode.getWidth();
		int height = barcode.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2d = image.createGraphics();
		Java2DRenderer renderer = new Java2DRenderer(g2d, 1, Color.WHITE, Color.BLACK);
		renderer.render(barcode);

		File bareCodeFile = new File(ParameterUtil.getParameterValueByName(ParameterUtil.GENERATED_BARCODE_PATH), UUID.randomUUID().toString() + ".png");
		ImageIO.write(image, "png", bareCodeFile);

		return bareCodeFile.getAbsolutePath();
	}

	public static String firstLetterToUpperCase(String term) {
		if (term == null || term.trim().isEmpty()) {
			return term;
		}

		return Character.toUpperCase(term.charAt(0)) + (term.length() > 1 ? term.substring(1) : "");
	}

	private List<Office> sortOffices(List<Office> offices, PoliceRecordBean policeRecordBean) {
		Map<Office, Double> distance = new HashMap<>();

		for (Office office : offices) {
			distance.put(office, distance(office.getLatitude(), office.getLongitude(), policeRecordBean.getLatitude(),
					policeRecordBean.getLongitude()));
		}

		return distance.entrySet().stream()
				.sorted((entry1, entry2) -> Double.compare(entry1.getValue(), entry2.getValue())).map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		// The math module contains a function
		// named toRadians which converts from
		// degrees to radians.
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in kilometers. Use 3956
		// for miles
		// Kilometers -> 6371;
		// miles => 3956
		double r = 3956;

		// calculate the result
		return (c * r);
	}
}