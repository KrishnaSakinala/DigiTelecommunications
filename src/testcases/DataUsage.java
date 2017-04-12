package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import util.CommonMethods;
import util.Data;
import util.Selector;
import base.BaseTest;

public class DataUsage extends BaseTest {
	
	@BeforeClass
	public void logIn()
	{
		CommonMethods.enter(Selector.customerIdTextbox, Data.expiredDataUsername);
		CommonMethods.enter(Selector.passwordTextbox, Data.expiredDataPassword);
		CommonMethods.click(Selector.singInButton);
	}
	
	@Test
	public void dataExpirationVerificaton() throws InterruptedException
	{
		test= extent.createTest("dataExpirationVerificaton");
		String status = CommonMethods.getText(Selector.subsriptionStatus);
		Assert.assertEquals(status.trim(), "Expired");
		test.info("Status is Expired");
		CommonMethods.click(Selector.file1Link);
		Thread.sleep(2000);
		String errorMessage = CommonMethods.getText(Selector.subscriptionExpiredErrorMessage);
		Assert.assertEquals(errorMessage.trim(), "Subscription expired.");
		test.info("Subscription expired message displayed");
		CommonMethods.click(Selector.subscriptionExpiredCloseButton);
		Thread.sleep(2000);
	}
	
	@AfterClass
	public void tearDown()
	{
		CommonMethods.click(Selector.logoutButton);
	}
	
}
