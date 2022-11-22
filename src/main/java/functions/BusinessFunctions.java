package functions;

import java.io.*;
import java.util.Locale;
import java.util.Properties;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

public class BusinessFunctions {
	
	public static Properties Prop;
	public static WebDriver driver;

	public void HotelApp_Login(WebDriver driver, String UserName, String Password) throws FileNotFoundException, IOException {
		Prop = new Properties();
		Prop.load(new FileInputStream("./SharedUIMap/SharedMap.properties"));
		
		driver.findElement(By.id(Prop.getProperty("LoginPage_UserName"))).sendKeys(UserName);
		driver.findElement(By.xpath(Prop.getProperty("LoginPage_Password"))).sendKeys(Password);
		driver.findElement(By.cssSelector(Prop.getProperty("LoginPage_Login"))).click();		
	}

	// Method to read XLS using JXL library
	public static String readXL(int row, String column, String strFilePath) {
		Cell c = null;
		int reqCol=0;
		WorkbookSettings ws = null;
		Workbook workbook = null;
		Sheet sheet = null;
		FileInputStream fs = null;

		try {
			fs = new FileInputStream(new File(strFilePath));
			ws = new WorkbookSettings();
			ws.setLocale(new Locale("en","EN"));

			workbook = Workbook.getWorkbook(fs,ws);
			sheet = workbook.getSheet(0);

			String col = column.trim();

			for(int j=0; j<sheet.getColumns(); j++) {
				Cell cell = sheet.getCell(j,0);
				if(cell.getContents().trim().equalsIgnoreCase(col)) {
					reqCol = cell.getColumn();
					c=sheet.getCell(reqCol,row);
					fs.close();
					return c.getContents();
				}
			}
		}
		catch(BiffException be) {
			System.out.println("The given file should have .xls extension");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
		}
		System.out.println("NO MATCH FOUND IN THE GIVEN FILE");
		return null;
	}

//	// Method to reax XLSX using Apache POI Library
//	public static void readXLS(String strFilePath) throws IOException {
//
//		//Path of the excel file
//		FileInputStream fs = new FileInputStream(strFilePath);
//
//		//Creating a workbook
//		XSSFWorkbook workbook = new XSSFWorkbook(fs);
//		XSSFSheet sheet = workbook.getSheetAt(0);
//		Row row = sheet.getRow(0);
//		Cell cell = row.getCell(0);
//		System.out.println(sheet.getRow(0).getCell(0));
//		Row row1 = sheet.getRow(1);
//		Cell cell1 = row1.getCell(1);
//		System.out.println(sheet.getRow(0).getCell(1));
//		Row row2 = sheet.getRow(1);
//		Cell cell2 = row2.getCell(1);
//		System.out.println(sheet.getRow(1).getCell(0));
//		Row row3 = sheet.getRow(1);
//		Cell cell3 = row3.getCell(1);
//		System.out.println(sheet.getRow(1).getCell(1));
//		//String cellval = cell.getStringCellValue();
//		//System.out.println(cellval);
//	}

//	// Read and Write to JSON file
//	public void readWriteJSON() throws InterruptedException, IOException, ParseException {
//		JSONParser jsonParser = new JSONParser();
//		try  {
//				FileReader reader = new FileReader("Testdata.json");
//
//				//Read JSON file
//				Object obj = jsonParser.parse(reader);
//				JSONArray usersList = (JSONArray) obj;
//				System.out.println("Users List-> "+usersList); //This prints the entire json file
//
//			for(int i=0;i<usersList.size();i++) {
//					JSONObject users = (JSONObject) usersList.get(i);
//					System.out.println("Users -> "+users);//This prints every block - one json object
//					JSONObject user = (JSONObject) users.get("users");
//					System.out.println("User -> "+user); //This prints each data in the block
//					String username = (String) user.get("username");
//					String password = (String) user.get("password");
//
//					//Write JSON file
//					try (FileWriter file = new FileWriter("Testdata1.json")) {
//						file.append(usersList.toJSONString());
//						file.flush();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					System.out.println(user);
//				}
//		} catch (FileNotFoundException e) {
//				e.printStackTrace();
//		}
//	}
}
