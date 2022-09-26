import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.RemoteWebElement
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('Generic/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)


String myText
WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)


String ReportFile = GlobalVariable.G_ReportName + '.html'

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

def result

WebUI.delay(2)

try {
def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Jobs'), 
     20,extentTest,'Jobs Tab')

    if (jobsTab) {
        WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
    }
    
	
	
	WebUI.delay(2)

	WebUI.click(findTestObject('Object Repository/JobMonitoringPage/a_Reset'))

	TestObject newJobFilterCategoryDown = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierDown'(
			'job_array_filters')

	TestObject newJobFilterCategoryRight = CustomKeywords.'buildTestObj.CreateTestObjJobs.myTestObjFilterCategoryIdentifierRight'(
			'job_array_filters')

	TestObject newJobFilterTitle = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),
			'text', 'equals', 'Job Type', true)

	TestObject newJobFilterValue = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/Title_FilterCategory'),
			'text', 'equals', 'Batch Jobs', true)

	isElementPresentDown = WebUI.waitForElementPresent(newJobFilterCategoryDown, 3, FailureHandling.CONTINUE_ON_FAILURE)

	isElementPresentRight = WebUI.waitForElementPresent(newJobFilterCategoryRight, 3, FailureHandling.CONTINUE_ON_FAILURE)

	println('**************************')

	println(isElementPresentDown)

	println(isElementPresentRight)

	println('**************************')

	WebUI.delay(4)
	WebUI.scrollToElement(newJobFilterTitle, 3)

	if (isElementPresentDown) {
		println('down')

		WebUI.click(newJobFilterValue)
	}
	

	if (isElementPresentRight) {
		println('right')

		WebUI.click(newJobFilterTitle)

		WebUI.delay(2)

		WebUI.click(newJobFilterValue)
	}




	TestObject newJobFilter = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/label_jobState'), 'text', 'equals',
			jobState, true)

	WebUI.click(newJobFilter)

	WebUI.delay(2)

	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

	println(jobState)


	TestObject newJobRow = WebUI.modifyObjectProperty(findTestObject('JobMonitoringPage/div_Completed'), 'title', 'equals',
			jobState, true)

	if(jobAction.equals('View Subjobs'))
	{
		String myXpath="//div[@col-id='jobId']"
		List<WebElement> listElement = katalonWebDriver.findElements(By.xpath(myXpath))
		println listElement.size()

		RemoteWebElement ele = listElement.get(1)
		myText=ele.getText()
		println (ele.getText())
		extentTest.log(LogStatus.PASS, 'Job ID - '+myText)
		//CustomKeywords.'operations_JobsModule.GetJobRowDetails.checkSubJobs'(katalonWebDriver,'JA',extentTest)

	}
    WebUI.delay(3)
	WebUI.doubleClick(newJobRow)
	WebUI.delay(3)
	WebUI.click(newJobRow)
	WebUI.rightClick(newJobRow)
	WebUI.click(findTestObject('JobMonitoringPage/ViewDetails_Jobs'))
	extentTest.log(LogStatus.PASS, 'Clicked on job with state  - ' + jobState)

   //WebUI.waitForElementVisible(findTestObject('JobMonitoringPage/OutputFolder_File'), 5)
	//WebUI.click(findTestObject('JobMonitoringPage/OutputFolder_File'))
	WebUI.click(findTestObject('JobMonitoringPage/RunningFolder'))
	extentTest.log(LogStatus.PASS, 'Click on Running Folder')
	
	WebUI.rightClick(findTestObject('JobMonitoringPage/SleepJob.e'))
	extentTest.log(LogStatus.PASS, 'Right Click onSleepJobArray.e file in Running Folder')
	
	WebUI.click(findTestObject('2020.1/FileUpload'))
	
	def filePathFT = (RunConfiguration.getProjectDir() + '/Upload/') + ToUpload
	def newFPFT=CustomKeywords.'generateFilePath.filePath.getFilePath'(filePathFT)
	println(newFPFT)
	
	WebUI.uploadFile(findTestObject('2020.1/FileUpload'), newFPFT )
	extentTest.log(LogStatus.PASS, 'Uploading zip file - JobsModule.zip')
	WebUI.delay(5)
	WebUI.click(findTestObject('Object Repository/FilesPage/button_Yes'))
	extentTest.log(LogStatus.PASS, 'Clicked YES on Unzip on Upload confirmation pop-up')
	WebUI.delay(2)
	
		
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, ex)

	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	extentTest.log(LogStatus.FAIL, e)

	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()
}

