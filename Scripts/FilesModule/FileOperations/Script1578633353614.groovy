import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

TestObject newFileObj

def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'

if (TestCaseName.contains('tile view')) {
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', fileName,
			true //	WebUI.click(newFileObj)
			)
} else {
	newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals', fileName,
			true)
}

try {
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
			20, extentTest, 'Files Tab')

	if (filesTab)
	{
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

	WebUI.delay(2)

	println ("==============================================")
	println(TestCaseName)
	println ("==============================================")

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)


	if (TestCaseName.contains('Upload'))
	{
		println(TestOperation)
	}
	else
	{

		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Navigated to ' + location)

		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

		WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)

		println(fileName)

		WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)

		extentTest.log(LogStatus.PASS, (('Looking for file - ' + fileName) + ' to perfrom operation - ') + TestOperation)

		WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Found File  - ' + fileName)

		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj, 20, extentTest, fileName)

		println(fileItem)

		if (fileItem) {
			WebUI.waitForElementPresent(newFileObj, 3)

			WebUI.click(newFileObj)

			extentTest.log(LogStatus.PASS, 'Clicked on file ' + fileName)

			WebUI.rightClick(newFileObj)

			extentTest.log(LogStatus.PASS, 'Right Clicked File to invoke context menu on  - ' + fileName)
		}
	}

	WebUI.delay(2)

	def result = CustomKeywords.'operations_FileModule.fileOperations.executeFileOperations'(TestOperation, TestCaseName,
			extentTest)

	if (result) {
		extentTest.log(LogStatus.PASS, ('Verified - ' + TestCaseName) + '  Sucessfully')
	} else {
		extentTest.log(LogStatus.FAIL, TestCaseName + ' - failed')
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
	extent.endTest(extentTest)

	extent.flush()
}

