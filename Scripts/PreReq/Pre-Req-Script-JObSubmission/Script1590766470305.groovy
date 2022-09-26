import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable
import com.relevantcodes.extentreports.LogStatus

//====================================================================================
ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)


try {


	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Files'),
		20,extentTest,'Files Tab')
println filesTab
	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}
		WebUI.delay(2)

	TestObject viewIconTile = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title',
			'equals', 'Tile View', true)

	TestObject viewIconList = WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/Icon_ViewIcon'), 'title',
			'equals', 'List View', true)

	viewIconTilePresent = WebUI.waitForElementPresent(viewIconTile, 3, FailureHandling.CONTINUE_ON_FAILURE)

	viewIconListPresent = WebUI.waitForElementPresent(viewIconList, 3, FailureHandling.CONTINUE_ON_FAILURE)

	println('viewIconTilePresent - ' + viewIconTilePresent)

	println('viewIconListPresent - ' + viewIconListPresent)

	if (viewIconListPresent) {
		WebUI.click(viewIconList)
		//extentTest.log(LogStatus.PASS, 'Changed View to ListView')
		WebUI.delay(2)
	}
	WebUI.delay(2)
	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))


	def navLocation =CustomKeywords.'generateFilePath.filePath.execLocation'()
	def location=navLocation
	println('##################################################################')
	println (location)
	println('##################################################################')


	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
	//extentTest.log(LogStatus.PASS, 'Navigated to -  '+  location)


	WebUI.waitForElementVisible(findTestObject('FilesPage/btn_NewFileFolder'), 10)

	WebUI.click(findTestObject('FilesPage/btn_NewFileFolder'))

	WebUI.click(findTestObject('FilesPage/ListItem_Folder'))

	WebUI.waitForElementVisible(findTestObject('FilesPage/TextBxFolder_input'), 5)

	WebUI.setText(findTestObject('FilesPage/TextBxFolder_input'), 'JSUploads')
	//extentTest.log(LogStatus.PASS, "Entered Folder Name to Create - JSUploads")

	WebUI.click(findTestObject('FilesPage/btn_Save'))
	//extentTest.log(LogStatus.PASS, "Clicked on Save Button" )

	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_Refresh'))

	WebUI.delay(2)
	def filePathFT = (RunConfiguration.getProjectDir() + '/Upload/') + ToUpload
	def newFPFT=CustomKeywords.'generateFilePath.filePath.getFilePath'(filePathFT)
	println(newFPFT)

	WebUI.uploadFile(findTestObject('FilesPage/UploadFileBtn'), newFPFT )
	//extentTest.log(LogStatus.PASS, 'Uploading zip file - JobsModule.zip')
	WebUI.delay(5)
	WebUI.click(findTestObject('Object Repository/FilesPage/button_Yes'))
	//extentTest.log(LogStatus.PASS, 'Clicked YES on Unzip on Upload confirmation pop-up')
	WebUI.delay(2)


		//add to check inputdeck folder is listed
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
		}
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	//extentTest.log(LogStatus.FAIL, ex)

	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) { }
finally {
	//extent.endTest(//extentTest)

	//extent.flush()


}

