package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class FileAnalysisService {
	public void analyzeExcelFile(MultipartFile file) {
		try {
			List<EmployeeRecord> employeeRecords = new ArrayList<>();

			try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

				Sheet sheet = workbook.getSheetAt(0);

				for (Row row : sheet) {
					if (row.getRowNum() == 0)
						continue; // Skip the header row

					String positionID = getStringValue(row.getCell(0));
					String positionStatus = getStringValue(row.getCell(1));
					Date time = getDateValue(row.getCell(2));
					Date timeOut = getDateValue(row.getCell(3));
					String timecardHours = getStringValue(row.getCell(4));
					Date payCycleStartDate = getDateValue1(row.getCell(5));
					Date payCycleEndDate = getDateValue1(row.getCell(6));
					String employeeName = getStringValue(row.getCell(7));
					String fileNumber = getStringValue(row.getCell(8));

					EmployeeRecord employee = new EmployeeRecord(positionID, positionStatus, time, timeOut,
							timecardHours, payCycleStartDate, payCycleEndDate, employeeName, fileNumber);
					employeeRecords.add(employee);
				}
			}

			analyzeEmployeeRecords(employeeRecords);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getStringValue(Cell cell) {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}

		return null;
	}

	private Date getDateValue(Cell cell) {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getDateCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			// Handle parsing the string as a date
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
			try {
				return dateFormat.parse(cell.getStringCellValue());
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		return null;
	}

	private Date getDateValue1(Cell cell) {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			return cell.getDateCellValue();
		} else if (cell.getCellType() == CellType.STRING) {
			// Handle parsing the string as a date
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try {
				if (cell.getStringCellValue() != null)
					return dateFormat.parse(cell.getStringCellValue());
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		return null;
	}

	private void analyzeEmployeeRecords(List<EmployeeRecord> employeeRecords) throws ParseException {

		for (int i = 1; i < employeeRecords.size(); i++) {
			EmployeeRecord currentRecord = employeeRecords.get(i);

			// Check for 7 consecutive days
			if (isConsecutiveDaysWorked(currentRecord)) {

				System.out.println(
						"Employee:- " + currentRecord.getEmployeeName() + " has worked more than 7 consecutive days.");
			}

			// Check for less than 10 hours between shifts but greater than 1 hour
			if (!Objects.isNull(currentRecord) && !Objects.isNull(currentRecord.getTimeOut())
					&& !Objects.isNull(currentRecord.getTime() != null)) {
				try {
					// Calculate the time difference in hours
					long timeDifferenceMillis = currentRecord.getTimeOut().getTime()
							- currentRecord.getTime().getTime();
					long timeDifferenceHours = timeDifferenceMillis / (60 * 60 * 1000);

					if (timeDifferenceHours < 10 && timeDifferenceHours > 1) {

						System.out.println("Employee:- " + currentRecord.getEmployeeName()
								+ " has less than 10 hours and more than 1 between shifts.");
					}
				} catch (NumberFormatException e) {
					// Handle the case where the time difference cannot be calculated
					e.printStackTrace();
					System.out.println(
							"Employee:- " + currentRecord.getEmployeeName() + " has an invalid time difference.");
				}

				// Check for more than 14 hours in a single shift
				if (currentRecord.getTime() != null && currentRecord.getTimeOut() != null) {

					long shiftDuration = (currentRecord.getTimeOut().getTime() - currentRecord.getTime().getTime())
							/ (60 * 60 * 1000);
					if (shiftDuration > 14) {

						System.out.println("Employee: " + currentRecord.getEmployeeName()
								+ " worked for more than 14 hours in a single shift.");
					}
				}
			}
		}
	}

	private boolean isConsecutiveDaysWorked(EmployeeRecord currentRecord) {

		if (currentRecord != null && currentRecord.getPayCycleStartDate() != null
				&& currentRecord.getPayCycleEndDate() != null) {
			long difference_in_time = currentRecord.getPayCycleEndDate().getTime()
					- currentRecord.getPayCycleStartDate().getTime();
			long difference_in_days = (difference_in_time / (1000 * 60 * 60 * 24)) % 365;
			if (difference_in_days > 7) {

				return true;
			}
		}
		return false;

	}
}