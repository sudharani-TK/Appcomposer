import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

//====================================================================================
WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)
//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
//=====================================================================================


def result

WebUI.delay(2)

try {
	WebUI.delay(2)

	def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('NewJobPage/AppList_ShellScript'),
		20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}	    
	
	extentTest.log(LogStatus.PASS, 'Navigated to Jobs Tab')
	WebUI.delay(2)

	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))
	extentTest.log(LogStatus.PASS, 'Filters reset')

	WebUI.click(findTestObject('JobMonitoringPage/JM_SearchBox'))
	extentTest.log(LogStatus.PASS, 'Entering Search text - '+searchValue)
	WebUI.setText(findTestObject('JobMonitoringPage/JM_SearchBox'),searchValue)
	WebUI.sendKeys(findTestObject('JobMonitoringPage/JM_SearchBox'), Keys.chord(Keys.ENTER))

	if(GlobalVariable.G_Browser.equals('FireFox')) {
		WebUI.delay(5)
		extentTest.log(LogStatus.PASS, 'Waiting for jobs table to load on FireFox')
	}

	String myXpath="//div[@col-id='jobId']"
	List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
	println listElement.size()


	switch (userChoice)
	{
		case "Valid":
			if(listElement.size()>=2)
			{
				for(int i =1;i<listElement.size();i++) {
					RemoteWebElement ele = listElement.get(i)
					String myText=ele.getText()
					println (ele.getText())
					extentTest.log(LogStatus.PASS, 'Job Found for matching search criteria  - '+ searchWith + ' with job id  - '+myText)
				}
			}
			else
			{
				extentTest.log(LogStatus.FAIL, 'No job found for search criteria - '+ searchWith)

			}
			break;

		case "Invalid":
			if(listElement.size()>=2)
			{
				for(int i =1;i<listElement.size();i++) {
					RemoteWebElement ele = listElement.get(i)
					String myText=ele.getText()
					println (ele.getText())
					extentTest.log(LogStatus.FAIL, 'Job Found for invalid search criteria  - '+ searchWith + ' with job id  - '+myText)
			}

			}else
			{
				extentTest.log(LogStatus.PASS, 'No job found for invalid search criteria - '+ searchWith)
				
			}
			break;
		case "NoJobs":
		def isMsgPresent=WebUI.verifyElementPresent(findTestObject('Object Repository/JobMonitoringPage/NoJobsMsg'), 5, FailureHandling.CONTINUE_ON_FAILURE)
		if(isMsgPresent)
		{
			extentTest.log(LogStatus.PASS, 'No job found for user - '+GlobalVariable.G_userName)
			
			}
		else
		{
			extentTest.log(LogStatus.PASS, 'Jobs found for user - '+GlobalVariable.G_userName)
			
			}
			break;
	}

}
catch (Exception ex) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
    WebUI.takeScreenshot(screenShotPath)
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	} 
catch (StepErrorException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
    WebUI.takeScreenshot(screenShotPath)
    extentTest.log(LogStatus.FAIL, e)
} 
finally { 
    extentTest.log(LogStatus.PASS, 'Closing the browser after executinge test case - '+ TestCaseName)
    extent.endTest(extentTest)
    extent.flush()
}
//=====================================================================================
