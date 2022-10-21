import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.RemoteWebDriver
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver
import java.awt.Robot
import java.awt.event.KeyEvent as KeyEvent

'Login into PAW '
/*
WebUI.callTestCase(findTestCase('Generic/Login'), [('username') : GlobalVariable.G_AdminUser, ('password') : GlobalVariable.G_AdminPasswd],
FailureHandling.STOP_ON_FAILURE)
*/

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
def result
WebUI.delay(2)
Robot rob = new Robot()
WebUI.delay(2)
try
{
	WebUI.delay(2)
	//WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	WebUI.delay(3)
	WebUI.mouseOver(findTestObject('Cluster_Registration/Manageservice'))
	WebUI.click(findTestObject('Cluster_Registration/Manageservice'))
	extentTest.log(LogStatus.PASS, 'Click on Manage Service')
	WebUI.delay(2)
	
	switch(userChoice)
	{   
		case'Edit':
		
		
	    WebUI.rightClick(findTestObject('Cluster_Registration/Available'))
		WebUI.delay(3)
		WebUI.click(findTestObject('Cluster_Registration/Edit'))
		extentTest.log(LogStatus.PASS, 'Edit Existing Cluster')
		
		/*WebUI.click(findTestObject('Cluster_Registration/Password'))
		WebUI.setText(findTestObject('Cluster_Registration/Password'),password)
		extentTest.log(LogStatus.PASS, 'Add password')*/
		WebUI.delay(3)
		WebUI.click(findTestObject('Cluster_Registration/Addrootdir'))
			
        WebUI.click(findTestObject('Cluster_Registration/root_dir1'))
		WebUI.setText(findTestObject('Cluster_Registration/root_dir1'),rootdir1)
		extentTest.log(LogStatus.PASS, 'Click on add root dir')
		
		WebUI.click(findTestObject('Cluster_Registration/Add_Server'))
		extentTest.log(LogStatus.PASS, 'Click on add server')
		/*
		
		def submitBtn = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Cluster_Registration/Add_Server'), 
        30 , extentTest , 'add server')

    if (submitBtn) {
        
		WebUI.mouseOver(findTestObject('Cluster_Registration/Manageservice'))
		WebUI.click(findTestObject('Cluster_Registration/Manageservice'))
		extentTest.log(LogStatus.PASS, 'Click on Manage Service')

        
    }
	*/
	//WebUI.click(findTestObject('Cluster_Registration/File'))
	//WebUI.verifyElementPresent(findTestObject('Cluster_Registration/Homedir'), 5)
	
	/*
	result = CustomKeywords.'operations_JobsModule.GetJobRowDetails.newGrid'(katalonWebDriver, colid, server,
		extentTest)
	*/
		//WebUI.verifyElementPresent(findTestObject('Cluster_Registration/Verify_Url'), 2)
		//extentTest.log(LogStatus.PASS, 'Verify the edit')
		
		WebUI.rightClick(findTestObject('Cluster_Registration/Available'))
		WebUI.delay(3)
		WebUI.click(findTestObject('Cluster_Registration/Edit'))
		extentTest.log(LogStatus.PASS, 'Edit Existing Cluster')
		WebUI.verifyElementPresent(findTestObject('Cluster_Registration/root_dir1'), 3)
	break
	
	case'cancel Edit':
	
	    WebUI.delay(3)
		WebUI.rightClick(findTestObject('Cluster_Registration/Available'))
		WebUI.delay(2)
		WebUI.click(findTestObject('Cluster_Registration/Edit'))
		extentTest.log(LogStatus.PASS, 'Edit Existing Cluster')
		
		WebUI.click(findTestObject('Cluster_Registration/Cancel'))
		extentTest.log(LogStatus.PASS, 'Cancel editing')
		
		WebUI.verifyElementPresent(findTestObject('Cluster_Registration/Available'), 5)
		break
		
	
		
		if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}

}
}

catch (Exception  ex)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,ex)
	KeywordUtil.markFailed('ERROR: '+ e)

}
catch (StepErrorException  e)
{

	String screenShotPath='ExtentReports/'+TestCaseName+GlobalVariable.G_Browser+'.png'
	WebUI.takeScreenshot(screenShotPath)
	extentTest.log(LogStatus.FAIL,e)
	KeywordUtil.markFailed('ERROR: '+ e)

}
finally
{

	extent.endTest(extentTest);
	extent.flush();

}

