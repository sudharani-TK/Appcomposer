import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver

import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus

import internal.GlobalVariable as GlobalVariable

WebDriver driver = DriverFactory.getWebDriver()
EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

ReportFile = (GlobalVariable.G_ReportName + '.html')
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)
CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

TestObject newFileObj
def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/LotOfFiles/'
def isLinkPresent
String data

WebUI.delay(2)

try {
	def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/TitleLink_Files'),
			10, extentTest, 'Files Tab')

	if (filesTab) {
		WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
	}

	extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

	WebUI.delay(2)

	CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

	WebUI.delay(2)

	println(TestCaseName)

	WebUI.click(findTestObject('Object Repository/FilesPage/Icon_EditFilePath'))

	WebUI.setText(findTestObject('Object Repository/FilesPage/textBx_FilePath'), location)

	WebUI.sendKeys(findTestObject('Object Repository/FilesPage/textBx_FilePath'), Keys.chord(Keys.ENTER))

	extentTest.log(LogStatus.PASS, 'Navigated to - ' + location)

	switch (userChoice) {
		case 'last':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)

			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to last page arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('3')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}

			break
		case 'previous':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_PreviousPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)

			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'))

				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_PreviousPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to last page arrow ')

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to previous arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('2')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}

			break
		case 'first':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)

			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_LastPage'))

				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_FirstPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to last page arrow ')

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to first arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('1')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}

			break
		case 'next':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.delay(2)
			if (isLinkPresent) {
				WebUI.click(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'))

				extentTest.log(LogStatus.PASS, 'Clicked on navigate to next arrow ')

				WebUI.delay(1)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('2')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box - ' + data)
				}
			}

			break
		case 'page':
			isLinkPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/PageNavigation/NavTo_NextPage'),
			5, FailureHandling.CONTINUE_ON_FAILURE)
			WebUI.delay(2)
			if (isLinkPresent) {
				WebUI.click(findTestObject('FilesPage/PageHolder'))
				WebUI.delay(1)
				WebUI.setText(findTestObject('FilesPage/PageHolder'), '2')

				WebUI.click(findTestObject('Object Repository/FilesPage/GoButton'))

				WebUI.delay(2)

				data = WebUI.getAttribute(findTestObject('FilesPage/PageHolder'), 'value')

				if (data.equalsIgnoreCase('2')) {
					extentTest.log(LogStatus.PASS, 'Verified the page number in page number box ---page case ----- ' + data)
				}
			}

			break
	}

	CustomKeywords.'operations_FileModule.getRowDetails.getFilePage'(katalonWebDriver, extentTest,TestCaseName)
	extentTest.log(LogStatus.PASS, 'Test case for pagination passed')
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



