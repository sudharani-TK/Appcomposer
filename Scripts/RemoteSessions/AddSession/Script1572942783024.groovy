import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

WebUI.callTestCase(findTestCase('Obsolete/Login'), [('username') : GlobalVariable.G_userName, ('password') : GlobalVariable.G_Password], 
    FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('GenericObjects/TitleLink_Sessions'))

WebUI.delay(2)

WebUI.click(findTestObject('SessionObjects/button_New Session'))

WebUI.doubleClick(findTestObject('SessionObjects/ApplicationList_Gedit'))

WebUI.click(findTestObject('JobSubmissionForm/button_Submit_Job'))

WebUI.waitForElementPresent(findTestObject('Notificactions/Notification_JobSubmission'), 7)

WebUI.switchToWindowTitle('Altair Access: Gedit')

WebUI.closeWindowTitle('Altair Access: Gedit')

WebUI.delay(2)

WebUI.switchToWindowUrl('https://10.75.36.105:4443/pbsworks/jobsubmission/newjob?context=interactive')

WebUI.waitForElementVisible(findTestObject('GenericObjects/TitleLink_Sessions'), 2)

WebUI.delay(2)

WebUI.click(findTestObject('GenericObjects/TitleLink_Sessions'))

