package com.mini.project.service;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Example;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mini.project.model.CitizenPlan;
import com.mini.project.model.SearchRequest;
import com.mini.project.repository.CitizenPlanRepository;

@Service
public class CitizenPlanServiceImpl implements ICitizenPlanService {

	@Autowired
	private CitizenPlanRepository citizenPlanRepository;

	public String save(CitizenPlan citizenPlan) {
		citizenPlan = citizenPlanRepository.save(citizenPlan);
		if (citizenPlan.getCid() != null) {
			return "CitizenPlan Created Successfully";
		} else {
			return "CitizenPlan Creation Failed";
		}
	}

	@Override
	public CitizenPlan getOneCitizenPlan(Integer cid) {

		Optional<CitizenPlan> findById = citizenPlanRepository.findById(cid);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public String update(CitizenPlan CitizenPlan) {
		if (citizenPlanRepository.existsById(CitizenPlan.getCid())) {
			CitizenPlan = citizenPlanRepository.save(CitizenPlan);
			return "CitizenPlan updated Successfully";
		} else {
			return "CitizenPlan Not Found";
		}
	}

	@Override
	public String deleteCitizenPlan(Integer cid) {

		if (citizenPlanRepository.existsById(cid)) {
			citizenPlanRepository.deleteById(cid);
			return "CitizenPlan deleted Successfully";
		}
		return "CitizenPlan Not Found";
	}

	@Override
	public List<String> getPlanNames() {
		return citizenPlanRepository.getPlanNames();
	}

	@Override
	public List<String> getPlanStatuses() {
		return citizenPlanRepository.getPlanStatuses();
	}

	@Override
	public List<CitizenPlan> getAllCitizenPlans(SearchRequest request) {
		CitizenPlan entity = new CitizenPlan();
		
		if(request.getPlanName() != null && !request.getPlanName().equals("")) {
			entity.setPlanName(request.getPlanName());
		}
		
		if(request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		
		if(request.getGender() != null && !request.getGender().equals("")) {
			entity.setGender(request.getGender());
		}
		
		Example<CitizenPlan> example = Example.of(entity);
		
		List<CitizenPlan> records = citizenPlanRepository.findAll(example);
	    return records;	
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws Exception {
		//0. Add apache POI depedency
		//1. Take all records
		List<CitizenPlan> records = citizenPlanRepository.findAll();
		
		//2. Create one work book then create one sheet and create one row for header row
		/*
		 * HSSF is a legacy method it will support MS Excel 2003
		 * HSSFWorkbook workbook = new HSSFWorkbook(); HSSFSheet sheet =
		 * workbook.createSheet("Citizen_Plan_Info"); HSSFRow headerRow =
		 * sheet.createRow(0);
		 */
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Citizen_Plan_Info");
		XSSFRow headerRow = sheet.createRow(0);
		
		//3. Give name for each column
		headerRow.createCell(0).setCellValue("Sl.No");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("Phone.No");
		headerRow.createCell(4).setCellValue("Gender");
		headerRow.createCell(5).setCellValue("SSN");
		headerRow.createCell(6).setCellValue("Plan Name");
		headerRow.createCell(7).setCellValue("Plan Status");
		
		//4. From row 2(index=1) need to insert the records
		int dataRowIndex = 1;
		
		for(CitizenPlan citizen : records) {
			//HSSFRow dataRow = sheet.createRow(dataRowIndex);
			XSSFRow dataRow = sheet.createRow(dataRowIndex);
			
			dataRow.createCell(0).setCellValue(citizen.getCid());
			dataRow.createCell(1).setCellValue(citizen.getCname());
			dataRow.createCell(2).setCellValue(citizen.getCemail());
			dataRow.createCell(3).setCellValue(citizen.getPhno());
			dataRow.createCell(4).setCellValue(citizen.getGender());
			dataRow.createCell(5).setCellValue(citizen.getSsn());
			dataRow.createCell(6).setCellValue(citizen.getPlanName());
			dataRow.createCell(7).setCellValue(citizen.getPlanStatus());
			
			
			dataRowIndex ++;
			
		}
		
		//5. Get the response into output stream then write that ops into the workbook after close all files
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}

	@Override
	public void exportPdf(HttpServletResponse response) throws DocumentException, IOException {
		//0. Add OpenPdf Dependency
		//1. Create Document(A4) and pass that into pdf writer
		Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        
        //2. Open the document and set the font style,size and color of heading
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        
        //3. Give paragraph(title) name, align to center and add to page
        Paragraph p = new Paragraph("Citizen Plans Info ", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
        
        //4. Create table with configuration
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.0f, 3.5f, 3.0f, 2.0f, 3.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);
        
        //5. Create Table Header
        //5.1 Take first cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        //5.2 Select the font
        Font tf = FontFactory.getFont(FontFactory.HELVETICA);
        tf.setColor(Color.WHITE);
        //5.3 Give the column names
        cell.setPhrase(new Phrase("ID", tf));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Name", tf));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("E-mail", tf));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Phone.No", tf));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", tf));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("SSN", tf));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Plan Name", tf));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Plan Status", tf));
        table.addCell(cell);
        
        //6. Add the records into the table
        //6.1 Take all records
        List<CitizenPlan> records = citizenPlanRepository.findAll();
        
        for (CitizenPlan record : records) {
            table.addCell(String.valueOf(record.getCid()));
            table.addCell(record.getCname());
            table.addCell(record.getCemail());
            table.addCell(String.valueOf(record.getPhno()));
            table.addCell(record.getGender());
            table.addCell(String.valueOf(record.getSsn()));
            table.addCell(record.getPlanName());
            table.addCell(record.getPlanStatus());        
        }
        //6.2 Add the table
        document.add(table);
        
        //7. Close document
        document.close();
        
		
	}

}
