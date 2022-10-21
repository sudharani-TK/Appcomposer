import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.remote.RemoteWebDriver as RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver as EventFiringWebDriver
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.exception.StepFailedException as StepFailedException
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus as LogStatus
import internal.GlobalVariable as GlobalVariable

/*
WebUI.callTestCase(findTestCase('Generic/Login'), [('username') : GlobalVariable.G_AdminUser, ('password') : GlobalVariable.G_AdminPasswd],
FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

String ReportFile=GlobalVariable.G_ReportName+".html"

def extent=CustomKeywords.'generateReports.GenerateReport.create'(ReportFile,GlobalVariable.G_Browser,GlobalVariable.G_BrowserVersion)
def LogStatus = com.relevantcodes.extentreports.LogStatus;

def extentTest = extent.startTest(TestCaseName)
String screenShot='ExtentReports/'+TestCaseName+userChoice+GlobalVariable.G_Browser+'.png'
def result
*/
'Login into PAW '
WebDriver driver = DriverFactory.getWebDriver()

EventFiringWebDriver eventFiring = ((DriverFactory.getWebDriver()) as EventFiringWebDriver)

// Get the driver wrapped inside
WebDriver wrappedWebDriver = eventFiring.getWrappedDriver()

// Cast the wrapped driver into RemoteWebDriver
RemoteWebDriver katalonWebDriver = ((wrappedWebDriver) as RemoteWebDriver)

ReportFile = (GlobalVariable.G_ReportName + '.html')

def extent = CustomKeywords.'generateReports.GenerateReport.create'(ReportFile, GlobalVariable.G_Browser, GlobalVariable.G_BrowserVersion)

def LogStatus = com.relevantcodes.extentreports.LogStatus

def extentTest = extent.startTest(TestCaseName)

CustomKeywords.'toLogin.ForLogin.Login'(extentTest)
String screenShot = ((('ExtentReports/' + TestCaseName) + userChoice) + GlobalVariable.G_Browser) + '.png'

def result

WebUI.delay(2)

try {
    WebUI.delay(2)

    //WebUI.click(findTestObject('GenericObjects/TitleLink_Jobs'))
    WebUI.click(findTestObject('Preferences/Profiletab'))

    extentTest.log(LogStatus.PASS, 'Click on profile tab')

    WebUI.delay(2)

    WebUI.click(findTestObject('Access_Management/Access_management'))

    extentTest.log(LogStatus.PASS, 'Click on access management')

    switch (userChoice) {
        case 'Add user verify assigned role':
            CustomKeywords.'create_user_role.user.add_user'(username, firstname, lastname, extentTest)

            /*WebUI.click(findTestObject('Access_Management/Add_user'))
		extentTest.log(LogStatus.PASS, 'Click on users')
		
		WebUI.click(findTestObject('Access_Management/Add_userbutton'))
		extentTest.log(LogStatus.PASS, 'Click on add user')
		
		
		
		WebUI.click(findTestObject('Access_Management/Username'))
		WebUI.doubleClick(findTestObject('Access_Management/Username'))
		WebUI.delay(3)
		WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
		WebUI.delay(2)
		rob.keyPress(KeyEvent.VK_BACK_SPACE)
		rob.keyRelease(KeyEvent.VK_BACK_SPACE)
		WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
		WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
		extentTest.log(LogStatus.PASS, 'Add username name - ' + username )
		
		WebUI.click(findTestObject('Access_Management/Firstname'))
		WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
		extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)
		
		WebUI.click(findTestObject('Access_Management/Lastname'))
		WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
		extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)
		
		
		WebUI.click(findTestObject('Access_Management/Save'))
		extentTest.log(LogStatus.PASS, 'Click on save')*/
            TestObject user = WebUI.modifyObjectProperty(findTestObject('Access_Management/Userinfo'), 'text', 'equals', 
                username, true)

            WebUI.doubleClick(findTestObject('Access_Management/Userinfo'))

            extentTest.log(LogStatus.PASS, 'Verify new user info')

            WebUI.click(findTestObject('Access_Management/Add_role'))

            extentTest.log(LogStatus.PASS, 'Assign role to new user')

            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Appuser'))

            extentTest.log(LogStatus.PASS, 'Select the role')

            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Confirm_button'))

            extentTest.log(LogStatus.PASS, 'Click on confirm button')

            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Save'))

            extentTest.log(LogStatus.PASS, 'Click on save')

            break
        case 'Add new user duplicate name':
            CustomKeywords.'create_user_role.user.add_user'(username, firstname, lastname, extentTest)

            WebUI.verifyElementPresent(findTestObject('Access_Management/Duplicate_name'), 2)

            extentTest.log(LogStatus.PASS, 'Verify error message user already exists')

            WebUI.takeScreenshot(screenShot)

            break
        case 'Add new user special characters':
            CustomKeywords.'create_user_role.user.add_user'(username, firstname, lastname, extentTest)

            extentTest.log(LogStatus.PASS, 'Verify through Screenshot')

            WebUI.takeScreenshot(screenShot)

            break
        case 'Add new user blank username':
            CustomKeywords.'create_user_role.user.add_user'(username, firstname, lastname, extentTest)

            /*  WebUI.click(findTestObject('Access_Management/Add_user'))
	    extentTest.log(LogStatus.PASS, 'Click on users')

	    WebUI.click(findTestObject('Access_Management/Add_userbutton'))
	    extentTest.log(LogStatus.PASS, 'Click on add user')

	    WebUI.click(findTestObject('Access_Management/Firstname'))
	    WebUI.setText(findTestObject('Access_Management/Firstname'), firstname)
	    extentTest.log(LogStatus.PASS, 'Add first name - ' + firstname)

	    WebUI.click(findTestObject('Access_Management/Lastname'))
	    WebUI.setText(findTestObject('Access_Management/Lastname'), lastname)
	    extentTest.log(LogStatus.PASS, 'Add last name - ' + lastname)

		WebUI.click(findTestObject('Access_Management/Username'))
		WebUI.doubleClick(findTestObject('Access_Management/Username'))
		WebUI.delay(3)
		WebUI.doubleClick(findTestObject('Object Repository/Access_Management/Username_text'))
		WebUI.delay(2)
		rob.keyPress(KeyEvent.VK_BACK_SPACE)
		rob.keyRelease(KeyEvent.VK_BACK_SPACE)
		WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), '')
		WebUI.setText(findTestObject('Object Repository/Access_Management/Username_text'), username)
		extentTest.log(LogStatus.PASS, 'Add username name - ' + username )

	    WebUI.click(findTestObject('Access_Management/Save'))
	    extentTest.log(LogStatus.PASS, 'Click on save')
		*/
            WebUI.takeScreenshot(screenShot)

            extentTest.log(LogStatus.PASS, 'Verify through screenshot ')

            break
        case 'Add user valid username':
            CustomKeywords.'create_user_role.user.add_user'(username, firstname, lastname, extentTest)

            TestObject user = WebUI.modifyObjectProperty(findTestObject('Access_Management/Userinfo'), 'text', 'equals', 
                username, true)

            WebUI.click(user)

            extentTest.log(LogStatus.PASS, 'Verify new user')

            break
        case 'Delete User':
            WebUI.click(findTestObject('Access_Management/Add_user'))

            extentTest.log(LogStatus.PASS, 'Click on users')

            TestObject user = WebUI.modifyObjectProperty(findTestObject('Access_Management/Userinfo'), 'text', 'equals', 
                username, true)

            WebUI.click(user)

            extentTest.log(LogStatus.PASS, 'Click on user to be deleted')

            WebUI.click(findTestObject('Access_Management/Delete_icon'))

            extentTest.log(LogStatus.PASS, 'Click on delete icon')

            WebUI.click(findTestObject('Access_Management/Confirm_button'))

            WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))

            extentTest.log(LogStatus.PASS, 'Opened Notification Panel')

            WebUI.delay(2)

            result = WebUI.verifyElementPresent(findTestObject('Access_Management/Delete_User'), 5)

            extentTest.log(LogStatus.PASS, 'Verified user is deleted successfully ')

            break
        case 'Delete service user':
            WebUI.click(findTestObject('Access_Management/Add_user'))

            extentTest.log(LogStatus.PASS, 'Click on users')

            //WebUI.click(findTestObject('Access_Management/Checkuser'))
            WebUI.click(findTestObject('Access_Management/Manager_userinfo'))

            extentTest.log(LogStatus.PASS, 'Click on manager ')

            WebUI.verifyElementPresent(findTestObject('Access_Management/Lock_icon'), 5)

            extentTest.log(LogStatus.PASS, 'Verify the lock icon present')

            WebUI.doubleClick(findTestObject('Access_Management/Manager_userinfo'))

            extentTest.log(LogStatus.PASS, 'Double click the manager user')

            WebUI.verifyElementNotPresent(findTestObject('Access_Management/Confirm_button'), 5)

            extentTest.log(LogStatus.PASS, 'Verify save  button is diabled')

            break
        case 'Add new role application user':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.verifyElementPresent(findTestObject('Access_Management/App_user_tickmark'), 4)

            extentTest.log(LogStatus.PASS, 'Verify application user is enable')

            WebUI.click(findTestObject('Access_Management/Save_role'))

            extentTest.log(LogStatus.PASS, 'Click on save')

            WebUI.verifyElementPresent(findTestObject('Access_Management/Verify_roleid'), 5)

            extentTest.log(LogStatus.PASS, 'Verify roleid')

            break
        case 'Add new role portal admin':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.verifyElementPresent(findTestObject('Access_Management/App_user_tickmark'), 4)

            WebUI.click(findTestObject('Access_Management/Save_role'))

            WebUI.verifyElementPresent(findTestObject('Access_Management/Verify_roleid'), 5)

            WebUI.click(findTestObject('Access_Management/Verify_roleid'))

            WebUI.verifyElementClickable(findTestObject('Access_Management/Portal_admin_tickmark'))

            break
        case 'Add new role verify assigned resources':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.click(findTestObject('Access_Management/Add_resources'))

            WebUI.click(findTestObject('Access_Management/Shellscript_resource'))

            WebUI.click(findTestObject('Access_Management/Confirm_button'))

            WebUI.click(findTestObject('Access_Management/Save_role'))

            TestObject role = WebUI.modifyObjectProperty(findTestObject('Access_Management/Roleinfo'), 'text', 'equals', 
                roleid, true)

            WebUI.doubleClick(role)

            WebUI.click(findTestObject('Access_Management/Shellscript_resource'))

            break
        case 'Add new role duplicate name':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.takeScreenshot(screenShot)

            if (GlobalVariable.G_Browser == 'IE') {
                WebUI.click(findTestObject('Access_Management/Cancel'))
            }
            
            break
        case 'Add new role blank':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.takeScreenshot(screenShot)

            if (GlobalVariable.G_Browser == 'IE') {
                WebUI.click(findTestObject('Access_Management/Cancel'))
            }
            
            break
        case 'Add new role special characters':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.takeScreenshot(screenShot)

            extentTest.log(LogStatus.PASS, 'Verify unable to use special characters')

            if (GlobalVariable.G_Browser == 'IE') {
                WebUI.click(findTestObject('Access_Management/Cancel'))
            }
            
            break
        case 'Change Assigned resources -Delete resources':
            CustomKeywords.'create_user_role.role.add_role'(roleid, extentTest)

            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Add_resources'))

            extentTest.log(LogStatus.PASS, 'Click on add resources')

            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Shellscript_resource'))

            extentTest.log(LogStatus.PASS, 'Add Shellscript')

            WebUI.click(findTestObject('Access_Management/Confirm_button'))

            extentTest.log(LogStatus.PASS, 'Click on Confirm')

            WebUI.click(findTestObject('Access_Management/Save_role'))

            extentTest.log(LogStatus.PASS, 'Click on save role')

            WebUI.delay(2)

            TestObject role = WebUI.modifyObjectProperty(findTestObject('Access_Management/Roleinfo'), 'text', 'equals', 
                roleid, true)

            WebUI.doubleClick(role)

            extentTest.log(LogStatus.PASS, 'Click on save')

            WebUI.click(findTestObject('Access_Management/Shellscript_resource'))

            extentTest.log(LogStatus.PASS, 'Click on save')

            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Delete_resource'))

            extentTest.log(LogStatus.PASS, 'Click on delete assigned resource')

            WebUI.verifyElementNotPresent(findTestObject('Access_Management/Shellscript_resource'), 5)

            extentTest.log(LogStatus.PASS, 'Verify assigned resource is deleted')

            break
        case 'Edit role':
            TestObject role = WebUI.modifyObjectProperty(findTestObject('Access_Management/Roleinfo'), 'text', 'equals', 
                roleid, true)

            WebUI.doubleClick(role)
			WebUI.delay(2)

            extentTest.log(LogStatus.PASS, 'Click on role to be edit')

            WebUI.click(findTestObject('Access_Management/Edit_role'))

            extentTest.log(LogStatus.PASS, 'Click on edit')

            WebUI.delay(2)

            //WebUI.click(findTestObject('Access_Management/Edit_roleid'))
            WebUI.delay(3)

            WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))

            WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.BACK_SPACE))

            WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')

            WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), 'newrole')

            extentTest.log(LogStatus.PASS, 'Add roleid name - newrole')

            WebUI.click(findTestObject('Access_Management/Confirm_button'))

            extentTest.log(LogStatus.PASS, 'Click on confirm')

            WebUI.click(findTestObject('Access_Management/Save_role'))

            extentTest.log(LogStatus.PASS, 'Click on save')

            WebUI.click(findTestObject('Access_Management/Newrole_info'))

            extentTest.log(LogStatus.PASS, 'Click on new role')

            break
        case 'Edit role cancel':
            TestObject role = WebUI.modifyObjectProperty(findTestObject('Access_Management/Roleinfo'), 'text', 'equals', 
                roleid, true)

            WebUI.doubleClick(role)
			WebUI.delay(2)

            extentTest.log(LogStatus.PASS, 'Click on role')

            WebUI.click(findTestObject('Access_Management/Edit_role'))

            extentTest.log(LogStatus.PASS, 'Click on edit role')

            WebUI.delay(2)

            //WebUI.click(findTestObject('Access_Management/Edit_roleid'))
            WebUI.delay(3)

            WebUI.doubleClick(findTestObject('Object Repository/AppComposer/Text'))

            WebUI.sendKeys(findTestObject('Object Repository/AppComposer/Text'), Keys.chord(Keys.BACK_SPACE))

            WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), '')

            WebUI.setText(findTestObject('Object Repository/AppComposer/Text'), 'newrole')

            extentTest.log(LogStatus.PASS, 'Add new roleid name')

            WebUI.click(findTestObject('Access_Management/Confirm_cancel'))

            extentTest.log(LogStatus.PASS, 'Click on cancel')

            WebUI.verifyElementPresent(findTestObject('Access_Management/Role_header'), 5)

            extentTest.log(LogStatus.PASS, 'Verify role header is present')

            break
        case 'Edit username cancel':
            WebUI.click(findTestObject('Access_Management/Add_user'))

            extentTest.log(LogStatus.PASS, 'Click on users')

            TestObject user = WebUI.modifyObjectProperty(findTestObject('Access_Management/Userinfo'), 'text', 'equals', 
                username, true)

            WebUI.doubleClick(user)

            extentTest.log(LogStatus.PASS, 'Click on user')

            WebUI.waitForElementPresent(findTestObject('Access_Management/Cancel_button'), 10)

            WebUI.click(findTestObject('Access_Management/Cancel_button'))

            extentTest.log(LogStatus.PASS, 'Click on cancel button')

            WebUI.click(findTestObject('Access_Management/Userinfo'))

            extentTest.log(LogStatus.PASS, 'Verify user is present' + username)

            break
        case 'Edit username':
            WebUI.click(findTestObject('Access_Management/Add_user'))

            extentTest.log(LogStatus.PASS, 'Click on users')

            TestObject user = WebUI.modifyObjectProperty(findTestObject('Access_Management/Userinfo'), 'text', 'equals', 
                username, true)

            WebUI.doubleClick(user)

            extentTest.log(LogStatus.PASS, 'Click on user info')

            WebUI.takeScreenshot(screenShot)

            extentTest.log(LogStatus.PASS, 'Verify unable to edit username')

            break
        case 'Delete role':
            TestObject role = WebUI.modifyObjectProperty(findTestObject('Access_Management/RoleinfoNew'), 'text', 'equals', 
                roleid, true)

            WebUI.mouseOver(role)

            WebUI.click(role)

            extentTest.log(LogStatus.PASS, 'Click on role')

            //WebUI.mouseOver(findTestObject('Access_Management/Selectrole'))
            //WebUI.click(findTestObject('Access_Management/Selectrole'))
            WebUI.delay(3)

            WebUI.click(findTestObject('Access_Management/Delete_role'))

            extentTest.log(LogStatus.PASS, 'Click on delete icon')

            WebUI.click(findTestObject('Access_Management/Confirm_button'))

            extentTest.log(LogStatus.PASS, 'Click on confirm button')

            WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))

            extentTest.log(LogStatus.PASS, 'Opened Notification Panel')

            WebUI.delay(2)

            WebUI.verifyElementPresent(findTestObject('Access_Management/Role_Delete'), 5)

            extentTest.log(LogStatus.PASS, 'Verify role deleted successfully')

            break
        case 'Delete role manager':
            WebUI.delay(2)

            WebUI.click(findTestObject('Access_Management/Manager_roleinfo'))

            extentTest.log(LogStatus.PASS, 'Click on manager role')

            WebUI.delay(3)

            WebUI.verifyElementPresent(findTestObject('Access_Management/Delete_role'), 5)

            //WebUI.verifyElementNotClickable(findTestObject('Access_Management/Delete_role'))
            println(WebUI.verifyElementClickable(findTestObject('Access_Management/Delete_role')))

            WebUI.verifyElementPresent(findTestObject('Access_Management/Lock_icon'), 5)

            extentTest.log(LogStatus.PASS, 'Verify the lock icon present')

            WebUI.doubleClick(findTestObject('Access_Management/Manager_roleinfo'))

            extentTest.log(LogStatus.PASS, 'Double click the manager role')

            WebUI.delay(3)

            WebUI.verifyElementPresent(findTestObject('Access_Management/Cancel_Role'), 5)

            extentTest.log(LogStatus.PASS, 'Verify save role button is diabled')

            break
    }
    
    if (GlobalVariable.G_Browser == 'IE') {
        WebUI.callTestCase(findTestCase('Generic/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
    }
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
catch (StepFailedException e) {
    String screenShotPath = (('ExtentReports/' + TestCaseName) + GlobalVariable.G_Browser) + '.png'

    WebUI.takeScreenshot(screenShotPath)

    extentTest.log(LogStatus.FAIL, e)

    KeywordUtil.markFailed('ERROR: ' + e)
} 
finally { 
    extent.endTest(extentTest)

    extent.flush()
}



