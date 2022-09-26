import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

import org.openqa.selenium.Keys as Keys

import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('Generic/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

String ReportFile=GlobalVariable.G_ReportName+".html"

def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus;

def extentTest = extent.startTest(TestCaseName)

WebUI.delay(2)
try
{
	'Navigate to Files Tab\r\n'
	
	/*
	def isElemenetPresent=CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Files'),5)
	
		if (isElemenetPresent)
		{
			WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
			extentTest.log(LogStatus.PASS, "Navigated to Files Tab" )
		}

*/
	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	
		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		def location='/stage/'+GlobalVariable.G_userName+'/Upload'
		
			WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		
			WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
			extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')
	
	WebUI.delay(2)
	WebUI.waitForElementVisible(findTestObject('2020.1/Upload_File'), 5)

	'Click Upload File Button '
	
	def filePath = (RunConfiguration.getProjectDir() + '/Upload/new1.zip')
				def newFP=(new generateFilePath.filePath()).getFilePath(filePath)
				println(newFP)
				WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFP )
	
	
    WebUI.verifyElementPresent(findTestObject('2020.1/Verify_unzip_message'), 3)
	
	WebUI.click(findTestObject('2020.1/Cancel_button'))
	
	WebUI.delay(6)
	
	
			
			WebUI.rightClick(findTestObject('2020.1/Verify_Unzipfile'))
			
			WebUI.click(findTestObject('2020.1/Uncompress'))
			

	

	if (GlobalVariable.G_Browser == 'IE') {
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
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