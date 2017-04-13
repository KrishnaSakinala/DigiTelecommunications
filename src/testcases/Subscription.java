package testcases;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import util.CommonMethods;
import util.Data;
import util.Selector;
import base.BaseTest;

public class Subscription extends BaseTest {
	
	@BeforeClass
	public void logIn()
	{
		CommonMethods.enter(Selector.customerIdTextbox, Data.activeDataUsername);
		CommonMethods.enter(Selector.passwordTextbox, Data.activeDataPassword);
		CommonMethods.click(Selector.singInButton);
	}
	
	@Test
	public void sixSubscriptionsVerification() throws InterruptedException
	{
		test = extent.createTest("sixSubscriptionsVerification");
		int initialSubscriptionCount = Integer.parseInt(CommonMethods.getText(Selector.subscriptionCount));
		test.info("Intial Subscription count: "+initialSubscriptionCount);
		Assert.assertTrue(initialSubscriptionCount == 0);
		for(int i=1; i<=6; i++)
		{
			CommonMethods.click(Selector.addSubscriptionButton);
			CommonMethods.waitForElement(Selector.subscriberNameTextbox);
			Random rand= new Random();
			int number=rand.nextInt(10000);
			CommonMethods.enter(Selector.subscriberNameTextbox,"Subscriber"+ number);
			CommonMethods.click(Selector.submitButton);
			CommonMethods.waitForElementInvisible(Selector.submitButton);
			test.info("Added Subscription: "+i);
		}
		int finalSubscriptionCount = Integer.parseInt(CommonMethods.getText(Selector.subscriptionCount));
		Assert.assertTrue(finalSubscriptionCount == 6);
	}
	
	@Test(dependsOnMethods={"sixSubscriptionsVerification"})
	public void seventhSubscriptionVerification() throws InterruptedException
	{
		test = extent.createTest("seventhSubscriptionVerification");
		int initialSubscriptionCount = Integer.parseInt(CommonMethods.getText(Selector.subscriptionCount));
		test.info("Intial Subscription count: "+initialSubscriptionCount);
		Assert.assertTrue(initialSubscriptionCount == 6);
		CommonMethods.click(Selector.addSubscriptionButton);
		CommonMethods.waitForElement(Selector.subscriptionExceedErrorMessage);
		String errorMessage = CommonMethods.getText(Selector.subscriptionExceedErrorMessage);
		Assert.assertEquals("Maximum subscriptions limit exceeded", errorMessage.trim());
		test.info("Error displayed for 7th Subscription");
		CommonMethods.click(Selector.subscriptionExceedCloseButton);
		CommonMethods.waitForElementInvisible(Selector.subscriptionExceedCloseButton);
	}
	
	@Test
	public void activeDataUsageVerification() throws InterruptedException
	{
		test = extent.createTest("activeDataUsageVerification");
		CommonMethods.waitForElement(Selector.subsriptionStatus);
		String status = CommonMethods.getText(Selector.subsriptionStatus);
		Assert.assertEquals(status.trim(), "Active");
		test.info("Status is Active");
		CommonMethods.click(Selector.file1Link);
		CommonMethods.waitForElement(Selector.logoutButton);
	}
	
	@AfterClass
	public void tearDown()
	{
		CommonMethods.click(Selector.logoutButton);
	}
	

}
