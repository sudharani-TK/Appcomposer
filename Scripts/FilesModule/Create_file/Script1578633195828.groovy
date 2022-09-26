import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable


String ReportFile = GlobalVariable.G_ReportName + '.html'
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule'
TestObject newFileObj




if (TestCaseName.contains('tile view')) {
	WebUI.delay(2)
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', fileName, true)
	location = navLocation + '/FilesModule/CreateFiles/TileView'

}
else
{
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals', fileName, true)
	location = navLocation + '/FilesModule/CreateFiles/ListView'
}


CustomKeywords.'toLogin.ForLogin.Login'(extentTest)


println('##################################################################')
println(location)
println('##################################################################')

WebUI.delay(2)

try {
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20, extentTest, 'Files Tab')
	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}
	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab ')

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)


	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)
	WebUI.delay(2)


	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
	String testLocation=WebUI.getAttribute(findTestObject('Object Repository/FilesPage/textBx_FilePath'), 'value')
	println('=================================================')
	println ('location from get attribute - '+testLocation)
	println('=================================================')

	WebUI.waitForElementVisible(findTestObject('FilesPage/btn_NewFileFolder'), 5)
	WebUI.click(findTestObject('FilesPage/btn_NewFileFolder'))
	extentTest.log(LogStatus.PASS, 'Clicked on New File/Folder  Button')
	WebUI.click(findTestObject('FilesPage/ListItem_File'))
	extentTest.log(LogStatus.PASS, 'Clicked on File list item')
	WebUI.click(findTestObject('FilesPage/TextBx_CreateFile'))
	WebUI.waitForElementVisible(findTestObject('FilesPage/TextBx_CreateFile'), 5)
	WebUI.click(findTestObject('FilesPage/TextBx_CreateFile'))
	WebUI.setText(findTestObject('FilesPage/TextBx_CreateFile'), fileName)
	extentTest.log(LogStatus.PASS, 'Enterted File Name to create ' + fileName)
	WebUI.click(findTestObject('FilesPage/btn_Save'))
	extentTest.log(LogStatus.PASS, 'Clicked on Save Button')

	switch (userChoice)
	{
		case 'Positive':

			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
			WebUI.delay(2)
			TestObject createFileNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFileCreateNotification'(fileName)
			def notification = WebUI.verifyElementPresent(createFileNotification, 5)

			String msg = ('The File ' + fileName) + ' has been created successfully'
			println(notification)
			if (notification)
			{
				extentTest.log(LogStatus.PASS, fileName + '- Created File and verified notification')
				extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, fileName + ' - Not Created')
				extentTest.log(LogStatus.FAIL)
			}
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
			def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, fileName)
			println(fileItem)
			if (fileItem)
			{
				WebUI.waitForElementPresent(newFileObj, 3)
				extentTest.log(LogStatus.PASS, ('Verified File - ' + fileName) + ' is listed')
			}
			break;

		case 'Negative':
			WebUI.verifyElementPresent(findTestObject('FilesPage/SpecialChar_popup'), 3)
			WebUI.click(findTestObject('Object Repository/FilesPage/btn_Cancel'))
			WebUI.delay(2)
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
			WebUI.delay(2)
			TestObject createFolderErrorNotification = CustomKeywords.'buildTestObj.CreateTestObjFiles.myTestObjFolderCreateErrorNotification'(fileName)
			def notification = WebUI.verifyElementPresent(createFolderErrorNotification, 5)

			String msg = 'Please provide a valid name, A file name cannot contain any of the following characters "'+'\\ / ? : " '
			println(notification)
			if (notification)
			{
				extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, fileName + ' - Is Created')
				extentTest.log(LogStatus.FAIL)
			}
			WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
			extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
			def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 3, extentTest, fileName)
			println(fileItem)
			if (fileItem)
			{
				extentTest.log(LogStatus.FAIL, ('File - ' + fileName) + ' is listed')
			}
			else
			{
				extentTest.log(LogStatus.PASS, ('Verified File - ' + fileName) + ' is not listed')

			}
			break;
	}



	if (GlobalVariable.G_Browser == 'Edge')
	{
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}
catch (Exception ex)
{
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e)
{
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	WebUI.takeScreenshot(screenShotPath)
	String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	extentTest.log(LogStatus.FAIL, ex)
	extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	KeywordUtil.markFailed('ERROR: ' + e)
}
finally
{
	extent.endTest(extentTest)
	extent.flush()
}

