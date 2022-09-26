package operations_FileModule

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.relevantcodes.extentreports.LogStatus

import internal.GlobalVariable

public class MultifilefolderOperations_JobSub {


	@Keyword
	def executeFileOperations(String Operation,String TestCaseName ,extentTest) {

		def date = new Date()
		def sdf = new SimpleDateFormat("ddMMyyyy_HHmmss")
		def e1 = sdf.format(date)
		def e2 =sdf.format(date)
		boolean result=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		println ("Control in Keyword")
		WebUI.delay(2)
		switch (Operation) {




			case 'Delete':

				WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFileFolder'))
				extentTest.log(LogStatus.PASS, 'Double click on MultiFileFolder folder')

				WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFileFolder'))
				extentTest.log(LogStatus.PASS, 'Double click on MultiFileFolder folder')

				WebUI.delay(3)
				WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
				extentTest.log(LogStatus.PASS, ' Click on select all ')
				WebUI.rightClick(findTestObject('Object Repository/JobMonitoringPage/four'))
				extentTest.log(LogStatus.PASS, 'Right click on folder to perform Delete operation')

				WebUI.click(findTestObject('JobMonitoringPage/Delete'))

				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
			//WebUI.click(findTestObject('FilesPage/Icon_Close'))
				WebUI.delay(2)
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				WebUI.delay(2)
			//Verify notification
				result = WebUI.verifyElementPresent(findTestObject('Notificactions/Notification_DeleteFile'),5)
				println("notification status - "+result)
				return result
				break

			case 'Download':
				WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFileFolder'))
				extentTest.log(LogStatus.PASS, 'Double click on MultiFileFolder folder')
				WebUI.delay(3)

				WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFileFolder'))
				extentTest.log(LogStatus.PASS, 'Double click on MultiFileFolder folder')

				WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
				extentTest.log(LogStatus.PASS, ' Click on select all ')
				WebUI.rightClick(findTestObject('JobMonitoringPage/RowItem_JobdDeails'))
				extentTest.log(LogStatus.PASS, 'Right click on folder to perform Download operation')

				TestObject newFileOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/ContextMenu_JobSubmission_Download'), 'id', 'equals', Operation, true)
				WebUI.click(newFileOp)




				File downloadFolder = new File(GlobalVariable.G_DownloadFolder)

				List namesOfFiles = Arrays.asList(downloadFolder.list())

				if (namesOfFiles.contains('ToDownload.txt')) {
					println('success')
					//extentTest.log(LogStatus.PASS, 'file to downloaded ')

				} else {
					println('fail')
				}

				return true
				break

			case 'New File':

				WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFileFolder'))
				extentTest.log(LogStatus.PASS, 'Double click on MultiFileFolder folder')
				WebUI.delay(3)
				WebUI.doubleClick(findTestObject('JobMonitoringPage/MultiFileFolder'))
				extentTest.log(LogStatus.PASS, 'Double click on MultiFileFolder folder')
				WebUI.click(findTestObject('Object Repository/FilesPage/CheckBox_SelectAll-JS-RFB'))
				extentTest.log(LogStatus.PASS, ' Click on select all ')
				WebUI.rightClick(findTestObject('JobMonitoringPage/RowItem_JobdDeails'))
				extentTest.log(LogStatus.PASS, 'Right click on folder to perform New File operation')

				WebUI.click(findTestObject('JobMonitoringPage/New File'))
				extentTest.log(LogStatus.PASS, 'Click on New File')
				def Renameto='NewFile.txt'
				TestObject renameTextBxObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/NewFile_input'), 'value', 'equals', 'New Text Document.txt', true)
				WebUI.setText(renameTextBxObj, Renameto)
				extentTest.log(LogStatus.PASS, 'Renamed file to  '+Renameto)

				WebUI.click(findTestObject('FilesPage/btn_Save'))
				WebUI.delay(3)
				extentTest.log(LogStatus.PASS, 'Clicked on Save Button')
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				result=WebUI.verifyElementPresent(findTestObject('Object Repository/Notificactions/Notification_FileCreation'), 5)
				extentTest.log(LogStatus.PASS, "Opened Notification Panel" )


				break



		}
	}
}