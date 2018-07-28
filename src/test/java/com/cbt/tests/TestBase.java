package com.cbt.tests;

import java.util.concurrent.TimeUnit;

import javax.swing.text.Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.cbt.utilities.ConfigurationReader;
import com.cbt.utilities.Driver;

public abstract class TestBase {
	protected WebDriver driver;
	protected Actions actions;
	
	@BeforeMethod
	public void setUp() {
		driver = Driver.getDriver();
		actions = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	//	driver.get(ConfigurationReader.getProperty("url"));
		driver.get(ConfigurationReader.getProperty("hrapp.url")); //for UIDB Testing

	}
	
	@AfterMethod
	public void tearDown() {
		Driver.closeDriver();
	}

}
