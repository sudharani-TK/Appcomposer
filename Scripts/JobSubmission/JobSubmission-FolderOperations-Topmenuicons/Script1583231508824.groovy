import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('Generic/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

String ReportFile = GlobalVariable.G_ReportName + '.html'

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

String TestCaseNameExtent = TestCaseName + ' Using Top-menu Icon'

def extentTest = extent.startTest(TestCaseNameExtent)


try {

		def jobsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('NewJobPage/AppList_ShellScript'),
		20,extentTest,'Jobs Tab')

	if (jobsTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
	}




	extentTest.log(LogStatus.PASS, 'Navigated Job Tab')
	WebUI.delay(2)

	TestObject newAppObj = WebUI.modifyObjectProperty(findTestObject('NewJobPage/AppList_ShellScript'), 'id', 'equals',
			AppName, true)

	WebUI.click(newAppObj)
	extentTest.log(LogStatus.PASS, 'Navigated to Job Submission For for - '+AppName)

	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/NewJobPage/GenericProfile'))
	WebUI.delay(2)

	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
	def location=navLocation+'/JobsModule/JobsModuleFolderOpsIcons'
	println('##################################################################')
	println (location)
	println('##################################################################')


	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to '+location)

	WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

	WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)


	println(FolderName)

	WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), FolderName)
	extentTest.log(LogStatus.PASS, 'Looking for file to perfrom operation - ' +Operation)

	WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File'), 'title', 'equals',
			FolderName, true) //	WebUI.click(newFileObj)


	def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj,20,extentTest,FolderName)

	println(fileItem)

	if (fileItem) {

		WebUI.waitForElementPresent(newFileObj, 3)

		WebUI.click(newFileObj)
		extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + FolderName)
	}



	WebUI.delay(2)
	println "after is else "+Operation
	def result=CustomKeywords.'operations_FileModule.fileOperations_Icon.executeFileOperations'(Operation, TestCaseName , extentTest)

	if(result)
	{
		extentTest.log(LogStatus.PASS, 'File Operation - ' + TestCaseName +' Performed Sucessfully')
	}
	else
	{
		extentTest.log(LogStatus.FAIL,'File Operation - ' + TestCaseName +' failed')
	}
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}


}


catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseNameExtent) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

		String p =TestCaseNameExtent+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))



KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TestCaseNameExtent) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TestCaseNameExtent+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))



	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()


}


