import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

'Login into PAW '
WebUI.callTestCase(findTestCase('Generic/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password],
FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

String TCName = TestCaseName

def extentTest = extent.startTest(TCName)

def viewIconTilePresent

def viewIconListPresent


TestObject newFileObj

WebUI.delay(2)

try {
	def filesTab =CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
	20,extentTest,'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}
    	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

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

		extentTest.log(LogStatus.PASS, 'Changed View to ListView')

		WebUI.delay(2)
	}

	WebUI.delay(2)

	println(TestCaseName)

	if (TestCaseName.contains('Upload')) {
		println(Operation //    WebUI.click(newFileObj)
				)
	} else {
		if (TestCaseName.contains('tile view')) {
			WebUI.click(viewIconTile)

			extentTest.log(LogStatus.PASS, 'Changed View to Tile View - test case - ' + TestCaseName)

			WebUI.delay(2)

			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals',
					fileName, true)
		} else {
			newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals',
					fileName, true)
		}

		WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

		def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()

		def location = navLocation + '/FilesModule/FileViewer/'

		println('##################################################################')

		println(location)

		println('##################################################################')

		WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

		WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Navigated to /stage/JSUploads in RFB ')

		WebUI.click(findTestObject('FilesPage/FilesSearch_filter'))

		WebUI.waitForElementVisible(findTestObject('FilesPage/FilesSearch_filter'), 2)

		println(fileName)

		WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), fileName)

		//extentTest.log(LogStatus.PASS, 'Looking for file to perfrom operation - ' + Operation)

		WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))

		extentTest.log(LogStatus.PASS, 'Clicked on File  - ' + fileName)

		def fileItem = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(newFileObj,
		20,extentTest,fileName)
		println(fileItem)

		if (fileItem) {
			WebUI.waitForElementPresent(newFileObj, 3)

			WebUI.click(newFileObj)

			WebUI.doubleClick(newFileObj)
		}
	}

	WebUI.delay(2)

	//WebUI.click(findTestObject('FilesPage/ContextMenu_FileOperation_Open'))

	//println('after is else ' + Operation)

	WebUI.delay(3)

	switch(userChoice)
	{
		
		case 'Process With':
		
		      WebUI.verifyElementPresent(findTestObject('2020.4/ProcessWith'), 3)
		
		break
		
		
		case 'Open with':
		
		          
		      WebUI.verifyElementPresent(findTestObject('2020.4/ProcessWith'), 3)
			  
			  WebUI.click(findTestObject('FilesPage/FileViewer_Edit'))
			  extentTest.log(LogStatus.PASS, 'Click on file edit icon')
			  WebUI.delay(3)
			  
			  //WebUI.verifyElementNotPresent(findTestObject('2020.4/ProcessWith'), 3)
			  //WebUI.verifyElementNotClickable(findTestObject('2020.4/ProcessWith'), 3)
			  
		      
		break
		
		case'Submit job':
		
		     WebUI.click(findTestObject('2020.4/ProcessWith'))
			 WebUI.click(findTestObject('2020.4/Shellscript'))
			 
		
		
		break
		
		default:
		      println('default')
		  break
	}
	
	if (GlobalVariable.G_Browser == 'Edge') {
		WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	}
}
catch (Exception ex) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e) {
	String screenShotPath = (('ExtentReports/' + TCName) + GlobalVariable.G_Browser) + '.png'

	WebUI.takeScreenshot(screenShotPath)

	String p =TCName+GlobalVariable.G_Browser+'.png'
	extentTest.log(LogStatus.FAIL,ex)
	extentTest.log(LogStatus.FAIL,extentTest.addScreenCapture(p))


	KeywordUtil.markFailed('ERROR: ' + e)
}
finally {
	extent.endTest(extentTest)

	extent.flush()

}

