import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

String ReportFile = GlobalVariable.G_ReportName + '.html'
def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FoldersModule'

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

TestObject newFileObj
WebUI.delay(2)

try {

	def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('NewJobPage/AppList_ShellScript'),
			20,extentTest,'App def')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}


	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)
	
	
	if (TestCaseName.contains('tile view'))
		{
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals',FolderName, true )
		}
		else
		{
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_Folder'), 'title','equals', FolderName, true)
		}
	
	
		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))
		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)
		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))
		extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)
		WebUI.delay(2)
	
	
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('FilesPage/btn_NewFileFolder'), 10)
		WebUI.click(findTestObject('FilesPage/btn_NewFileFolder'))
		extentTest.log(LogStatus.PASS, 'Clicked on New File/Folder  Button')
	
		WebUI.click(findTestObject('FilesPage/ListItem_Folder'))
		extentTest.log(LogStatus.PASS, 'Clicked on Folder list item')
	
		WebUI.waitForElementVisible(findTestObject('FilesPage/TextBxFolder_input'), 5)
		WebUI.setText(findTestObject('FilesPage/TextBxFolder_input'), FolderName)
		extentTest.log(LogStatus.PASS, "Entered Folder Name to Create - "+FolderName )
		WebUI.click(findTestObject('FilesPage/btn_Save'))
		extentTest.log(LogStatus.PASS, "Clicked on Save Button" )
		
		def result=WebUI.verifyElementPresent(newFileObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
	
		extentTest.log(LogStatus.PASS, "result === "+result )
		
	
	}catch (Exception  ex)
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
