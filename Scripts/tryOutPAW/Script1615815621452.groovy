import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import internal.GlobalVariable as GlobalVariable



ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus
def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)

TestObject newFileObj

def navLocation = CustomKeywords.'generateFilePath.filePath.execLocation'()
def location = navLocation + '/FilesModule/FileOps/'



def filesTab = CustomKeywords.'customWait.WaitForElement.WaitForelementPresent'(findTestObject('GenericObjects/FilesTab_disabled'),
		20, extentTest, 'Files Tab')

if (filesTab) {
	WebUI.click(findTestObject('GenericObjects/TitleLink_Files'))
}

extentTest.log(LogStatus.PASS, 'Navigated to Files Tab')

WebUI.delay(2)

CustomKeywords.'operations_FileModule.ChangeView.changePageView'(TestCaseName, extentTest)

println(TestCaseName)


WebUI.click(findTestObject("Object Repository/FilesPage/SortBy-Option"))
WebUI.delay(3)
WebUI.mouseOver(findTestObject("Object Repository/FilesPage/SortList-Option"))
WebUI.click(findTestObject("Object Repository/FilesPage/SortList-Option"))
extentTest.log(LogStatus.PASS, 'Sorted the listed files by created on, in tile view')
TestObject sortIconDown=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/SortBy-Order'), 'class', 'equals',"down-arrow tile-sortable-icon focus_enable_class", true)
def sortIconUp=WebUI.waitForElementPresent(findTestObject('Object Repository/FilesPage/SortBy-Order'), 3, FailureHandling.CONTINUE_ON_FAILURE)
def isIconPresent=WebUI.waitForElementPresent(sortIconDown, 3, FailureHandling.CONTINUE_ON_FAILURE)
if(sortIconUp) {
	WebUI.click(findTestObject('Object Repository/FilesPage/SortBy-Order'))
	WebUI.delay(2)
}
