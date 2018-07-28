package com.cbt.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cbt.utilities.BrowserUtils;
import com.cbt.utilities.Driver;

public class HRAppDeptEmpPage {
	private WebDriver driver;

	public HRAppDeptEmpPage() {
		this.driver = Driver.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="pt1:ot1") //be careful when using id's with numbers//auto-generated
	public WebElement departmentID;
	

	@FindBy(id="pt1:ot2")
	public WebElement departmentName;
	
	@FindBy(id="pt1:ot3") //be careful when using id's with numbers//auto-generated
	public WebElement managerID;
	
	@FindBy(id="pt1:ot4")
	public WebElement locationID;
	
	@FindBy(id="pt1:cb3")
	public WebElement next;

	@FindBy (id="pt1:r1:0:it1::content")
	public WebElement email;
	
	@FindBy (xpath="//button[.='Find Details']")
	public WebElement findDetails;
	
	@FindBy(id="pt1:r1:0:ot1")
	public WebElement firstName;
	
	@FindBy(id="pt1:r1:0:ot2")
	public WebElement lastName;
	
	@FindBy (xpath="//div[@id='pt1:pc1:t1::db']//tr")
	public List<WebElement> employeesCount;
	
	@FindBy(id="pt1:pc1:_dchTbr::icon")
	public WebElement detach;
	

	public void i_search_for_department_id(int deptID) {
		int currentDepId=Integer.parseInt(departmentID.getText());
		
		while(currentDepId!=deptID) {
			
			next.click();
			BrowserUtils.waitFor(2);
			currentDepId=Integer.parseInt(departmentID.getText());
		}
		
	}
}
