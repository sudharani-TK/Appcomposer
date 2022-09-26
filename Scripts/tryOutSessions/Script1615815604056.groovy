import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.exception.StepErrorException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable as GlobalVariable

String ReportFile = GlobalVariable.G_ReportName + '.html'
def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

try {
	def sessionsTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('Object Repository/GenericObjects/TitleLink_Sessions'),
			20, extentTest, 'Sessions Tab')
	if (sessionsTab) {
		WebUI.click(findTestObject('Object Repository/GenericObjects/TitleLink_Sessions'))
	}
	extentTest.log(LogStatus.PASS, 'Navigated to Sessions Tab ')



	def emptyMsgPresent = WebUI.verifyElementPresent( findTestObject('Object Repository/WIP/Label_NoSessionMsg'), 3, FailureHandling.CONTINUE_ON_FAILURE)

	if(emptyMsgPresent)
	{
		println(" no sessions msg present - "+ emptyMsgPresent)

				
	}
	else
	{
		println(" no sessions msg not present - "+ emptyMsgPresent)
		WebUI.doubleClick(findTestObject('Object Repository/tryPAWFolder/rowItem_SessionList'))
		TestObject newSessionOption=WebUI.modifyObjectProperty(findTestObject('Object Repository/tryPAWFolder/icon_SessionOpts'), 'id', 'equals', sessionOps, true)
		def isSessionOpsPresent=WebUI.verifyElementPresent(newSessionOption, 4, FailureHandling.CONTINUE_ON_FAILURE)
		if(isSessionOpsPresent)
		{
			println("found session opts")
			WebUI.click(newSessionOption)
		}
	}

}
catch (Exception ex)
{
	/*String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'
	 WebUI.takeScreenshot(screenShotPath)
	 String p = (TestCaseName + GlobalVariable.G_Browser) + '.png'
	 */extentTest.log(LogStatus.FAIL, ex)
	/*extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	 */KeywordUtil.markFailed('ERROR: ' + e)
}
catch (StepErrorException e)
{
	/*/*/extentTest.log(LogStatus.FAIL, ex)
	/*extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(p))
	 KeywordUtil.markFailed('ERROR: ' + e)
	 */}
finally
{
	extent.endTest(extentTest)
	extent.flush()
}

