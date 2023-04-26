package operations_FileModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable

public class fileViewerOperations {


	@Keyword
	def executeFileOperations(WebDriver katalonWebDriver,String Operation,String TestCaseName ,extentTest) {
		boolean result=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		println ("Control in Keyword --- "+ Operation )
		WebUI.delay(2)
		def fileName
		if (TestCaseName.contains('Double')) {
			fileName='ForFileViewerDB.txt'
		}
		else {
			fileName='ForFileViewer.txt'
		}
		TestObject newFileObj
		switch (Operation) {

			case 'Details':
				TestObject newFileOp=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/FileViewer_Operation'), 'title', 'equals', Operation, true)
				WebUI.click(newFileOp)
				newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/Viewdetails_File'), 'title', 'equals', fileName, true)
				WebUI.verifyElementPresent(newFileObj, 3, FailureHandling.STOP_ON_FAILURE)
				extentTest.log(LogStatus.PASS, 'Clicked on file to view details')
				TestObject newFileNameDetails=WebUI.modifyObjectProperty(findTestObject('Object Repository/FileEditor/FileName_Text_Details'), 'text', 'equals', fileName, true)
				TestObject newFileOwnerDetails=WebUI.modifyObjectProperty(findTestObject('Object Repository/FileEditor/FileOwner_Text_Details'), 'text', 'equals', GlobalVariable.G_userName, true)
				def isFileNamePresent=WebUI.verifyElementPresent(newFileNameDetails, 5)
				def isFileOwnerPresent=WebUI.verifyElementPresent(newFileOwnerDetails, 5)
				if(isFileNamePresent && isFileOwnerPresent) {
					extentTest.log(LogStatus.PASS, 'File name - '+fileName+' and file owner - '+GlobalVariable.G_userName+' verified')
					result=true
				}
				else {
					extentTest.log(LogStatus.FAIL, 'File name and file owner not matched')
					result=false
				}

				WebUI.click(findTestObject('Object Repository/FilesPage/Close_DetailsPanel'))

				return result
				break


			case 'EditSave':
				def myXpath="//div[@id='ace-editor']//textarea"
				def myLineXpath='//div[@class="ace_line_group"]'
				WebUI.click(findTestObject('FilesPage/FileViewer_Edit'))
				extentTest.log(LogStatus.PASS, 'Click on file edit icon')
				WebUI.delay(3)
				WebElement textEle=katalonWebDriver.findElement(By.xpath(myXpath))

				List<WebElement> linesBeforeEdit = katalonWebDriver.findElements(By.xpath(myLineXpath))
				def num=linesBeforeEdit.size()
				extentTest.log(LogStatus.PASS, 'Number of lines in file before addig new one - '+ num)

				RemoteWebElement ele = textEle
				ele.sendKeys("new line added")
				ele.sendKeys('\n')
				extentTest.log(LogStatus.PASS, 'Added new line into file')
				WebUI.click(findTestObject('Object Repository/FilesPage/btn_Save_fileEditor'))
				extentTest.log(LogStatus.PASS, 'Saved the file ')

				WebUI.click(findTestObject('FilesPage/FileViewer_Edit'))
				extentTest.log(LogStatus.PASS, 'Click on file edit icon again to check added lines ')
				WebUI.delay(3)
				List<WebElement> linesAfterEdit = katalonWebDriver.findElements(By.xpath(myLineXpath))
				def num3=linesAfterEdit.size()

				extentTest.log(LogStatus.INFO, 'Number of lines in file after saving  '+ num3)

				if(num3>num) {
					extentTest.log(LogStatus.PASS, 'New lines added in file successfully')
					result=true
				}
				else {
					extentTest.log(LogStatus.FAIL, 'New line not added in file')

					result=false
				}
				return result
				break


			case 'EditCancel':

				def myXpath="//div[@id='ace-editor']//textarea"
				def myLineXpath='//div[@class="ace_line_group"]'
				WebUI.click(findTestObject('FilesPage/FileViewer_Edit'))
				extentTest.log(LogStatus.PASS, 'Click on file edit icon')
				WebUI.delay(3)
				WebElement textEle=katalonWebDriver.findElement(By.xpath(myXpath))

				List<WebElement> linesBeforeEdit = katalonWebDriver.findElements(By.xpath(myLineXpath))
				def num=linesBeforeEdit.size()
				extentTest.log(LogStatus.PASS, 'Number of lines in file before addig new one - '+ num)
			//extentTest.log(LogStatus.INFO, 'linesBeforeEdit - '+ num)

				RemoteWebElement ele = textEle
				ele.sendKeys("new line added")
				ele.sendKeys('\n')
				extentTest.log(LogStatus.PASS, 'Added new line into file')
				if(TestCaseName.contains('Close'))
				{

					WebUI.click(findTestObject('Object Repository/FilesPage/Close_Button'))
					def r=WebUI.verifyElementPresent(findTestObject('Object Repository/FileEditor/dialog_CancelEdit'), 3, FailureHandling.CONTINUE_ON_FAILURE)
					if(r)
					{
						extentTest.log(LogStatus.PASS, 'Cancel Edting panel displayed')
						WebUI.click(findTestObject('Object Repository/GenericObjects/btn_Yes'))
						extentTest.log(LogStatus.PASS, 'Clicked on yes button ')
					}

					if (TestCaseName.contains('tile view')) {
						WebUI.delay(2)
						newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', fileName, true)
					}
					else
					{
						newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals', fileName, true)
					}

					result=WebUI.verifyElementPresent(newFileObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
					if(result)
					{
						extentTest.log(LogStatus.PASS, 'Navigated back to files page, file - '+fileName+' exists')

					}
					else
					{
						extentTest.log(LogStatus.PASS, 'Not Navigated back to files page')

					}
				}
				else
				{
					WebUI.click(findTestObject('Object Repository/FilesPage/btn_Cancel_fileEditor'))
					extentTest.log(LogStatus.PASS, 'Clicked on Cancel button')

					WebUI.click(findTestObject('FilesPage/FileViewer_Edit'))
					extentTest.log(LogStatus.PASS, 'Click on file edit icon again to check added lines ')
					WebUI.delay(3)
					List<WebElement> linesAfterEdit = katalonWebDriver.findElements(By.xpath(myLineXpath))
					def num3=linesAfterEdit.size()
					extentTest.log(LogStatus.PASS, 'Number of lines in file after hitting cancel button - '+ num3)
					//extentTest.log(LogStatus.INFO, 'linesAfterEdit - '+ num3)

					if(num3>num) {
						extentTest.log(LogStatus.FAIL, 'New lines added in file')
						result=false
					}
					else {
						extentTest.log(LogStatus.PASS, 'New line not added in file')

						result=true
					}

				}
				return result
				break

			case 'Download':
				TestObject newFileOp=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/FileViewer_Operation'), 'title', 'equals', Operation, true)
				WebUI.click(newFileOp)
				extentTest.log(LogStatus.PASS, 'Click on file to download')
				WebUI.delay(3)
				def downloadLoc=GlobalVariable.G_DownloadFolder
				File downloadFolder = new File("C://KatalonDownloads")

				List namesOfFiles = Arrays.asList(downloadFolder.list())
				println(namesOfFiles.size())
				if (namesOfFiles.contains('ForFileViewer.txt')) {
					println('success')
					result=true
				} else {
					println('fail')
					result=false
				}

				return true
				break

			case 'Delete':

				WebUI.click(findTestObject('Object Repository/FileEditor/Icon_Delete_FileEdit'))
				extentTest.log(LogStatus.PASS, 'Click on file to delete')
				extentTest.log(LogStatus.PASS, 'Clicked on Delete menu item' )
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'Clicked on Yes on Delete confirmation pop-up ')

				String msg ="/stage/rohini/FilesModule/FileOps/'+fileName+' has been deleted successfully"
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
				extentTest.log(LogStatus.PASS, 'Click on Notification button to open Notification Panel')
				WebUI.delay(2)
				result = WebUI.verifyElementPresent(findTestObject('Object Repository/Notificactions/Notification_DeleteFile'),5)
				println("notification status - "+result)
				if (result) {
					extentTest.log(LogStatus.PASS, ' Deleted file and verified notification')
					extentTest.log(LogStatus.PASS, ('Notification with msg - "' + msg) + '" is listed')
				}
				else {
					extentTest.log(LogStatus.PASS, '  Not pasted')
					extentTest.log(LogStatus.FAIL)
				}
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction2'))
				extentTest.log(LogStatus.PASS, 'Click on Notification button to close Notification Panel')
				if (TestCaseName.contains('tile view')) {
					WebUI.delay(2)
					newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals', fileName, true)
				}
				else
				{
					newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals', fileName, true)
				}

				result=WebUI.verifyElementPresent(newFileObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
				if(result)
				{
					extentTest.log(LogStatus.FAIL, 'Navigated back to files page, file - '+fileName+' not deleted')
					result=false
				}
				else
				{
					extentTest.log(LogStatus.PASS, 'Not Navigated back to files page file - '+fileName+' deleted')
					result=true
				}
				return result
				break

		}
	}
}