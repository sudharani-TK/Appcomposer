package jobActions

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.TestObjectXpath
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable

public class folderOperations_Icon {


	@Keyword
	def executeFileOperations(String Operation,String TestCaseName ,extentTest) {
		boolean result=false
		def LogStatus = com.relevantcodes.extentreports.LogStatus
		println ("Control in Keyword")
		WebUI.delay(2)
		switch (Operation) {
			case 'copy_icon':
				TestObject newFolderOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/FileOperations_Icon'), 'id', 'equals', Operation, true)
				WebUI.click(newFolderOp)
				println(Operation)
				extentTest.log(LogStatus.PASS, 'Clicked on Context Menu Option for - '+Operation)
				WebUI.click(findTestObject('FilesPage/Icon_Close'))
				def fileToCheck
				if (TestCaseName.contains('tile view')) {
					TestObject newFolderObj
					newFolderObj=new TestObject('objectName')
					newFolderObj.addProperty('xpath', ConditionType.EQUALS, "//label[contains(text(),'ToPaste')]")
					fileToCheck='ToCopy_TV.txt'
					WebUI.click(newFolderObj)
					WebUI.doubleClick(newFolderObj)
					extentTest.log(LogStatus.PASS, 'Navigated to ToPaste Folder')
					WebUI.delay(2)
					WebUI.delay(2)
				}
				else {

					WebUI.click(findTestObject('Object Repository/FilesPage/Folder_ListView_ToPaste'))
					WebUI.doubleClick(findTestObject('Object Repository/FilesPage/Folder_ListView_ToPaste'))
					extentTest.log(LogStatus.PASS, 'Navigated to ToPaste Folder')
					fileToCheck='ToCopy_LV.txt'
					WebUI.delay(2)
				}

				WebUI.click(findTestObject('FilesPage/TopMenuIcon_ellipses'))
				WebUI.delay(2)
				WebUI.click(findTestObject('FilesPage/TopMenuIcons_Paste'))
				extentTest.log(LogStatus.PASS, 'Clicked on Paste Option')

				TestObject newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title', 'equals',
						fileToCheck, true)

				def isFilePresent=WebUI.waitForElementVisible(newFileObj, 10,FailureHandling.CONTINUE_ON_FAILURE)
				if(isFilePresent){
					extentTest.log(LogStatus.PASS, 'Verified Pasted Folder - '+ fileToCheck)
				}
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				extentTest.log(LogStatus.PASS, "Opened Notification Panel" )
				WebUI.delay(2)
				result = WebUI.verifyElementPresent(findTestObject('Notificactions/Notification_CopyFile'),5)
				println("notification status - "+result)
				extentTest.log(LogStatus.PASS, "Verified notification for operation - "+Operation)
				return result
				break

			case 'cut_icon':
				TestObject newFolderOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/FileOperations_Icon'), 'id', 'equals', Operation, true)
				WebUI.click(newFolderOp)
				extentTest.log(LogStatus.PASS, 'Clicked on Context Menu Option for - '+Operation)
				WebUI.click(findTestObject('FilesPage/Icon_Close'))
				if (TestCaseName.contains('tile view')) {
					TestObject newFolderObj
					newFolderObj=new TestObject('objectName')
					newFolderObj.addProperty('xpath', ConditionType.EQUALS, "//label[contains(text(),'ToPaste')]")

					WebUI.click(newFolderObj)
					WebUI.doubleClick(newFolderObj)
					extentTest.log(LogStatus.PASS, 'Navigated to ToPaste Folder')
					WebUI.delay(2)
				}
				else {

					WebUI.click(findTestObject('Object Repository/FilesPage/Folder_ListView_ToPaste'))
					WebUI.doubleClick(findTestObject('Object Repository/FilesPage/Folder_ListView_ToPaste'))
					extentTest.log(LogStatus.PASS, 'Navigated to ToPaste Folder')
					WebUI.delay(2)
				}


				WebUI.click(findTestObject('FilesPage/TopMenuIcon_ellipses'))
				WebUI.delay(2)
				WebUI.click(findTestObject('FilesPage/TopMenuIcons_Paste'))
				extentTest.log(LogStatus.PASS, 'Clicked on Paste Option')

				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				extentTest.log(LogStatus.PASS, "Opened Notification Panel" )

				WebUI.delay(2)
			//Verify notification
				result = WebUI.verifyElementPresent(findTestObject('Notificactions/Notification_CutFile'),5)
				extentTest.log(LogStatus.PASS, "verified notification for operation - "+Operation)
				println("notification status - "+result)
				return result
				break

			case 'Compress_icon':

				WebUI.click(findTestObject('FilesPage/TopMenuIcon_ellipses'))
				WebUI.click(findTestObject('FilesPage/TopMenuIcons_Compress'))
				WebUI.delay(5)

				extentTest.log(LogStatus.PASS, 'Clicked on Context Menu Option for - '+Operation)

				println"Clicked Compress"
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))
				extentTest.log(LogStatus.PASS, "Opened Notification Panel" )

				WebUI.delay(2)
			//Verify notification
				result = WebUI.verifyElementPresent(findTestObject('Notificactions/Notification_CompressFile'),5)
				extentTest.log(LogStatus.PASS, "verified notification for operation - "+Operation)
				println("notification status - "+result)
				WebUI.click(findTestObject('Landing_Page/Btn_Notifiction'))

				if (TestCaseName.contains('tile view')) {
					WebUI.delay(2)

					WebUI.click(findTestObject("Object Repository/FilesPage/SortBy-Option"))
					WebUI.delay(3)
					WebUI.mouseOver(findTestObject("Object Repository/FilesPage/SortList-Option"))
					WebUI.click(findTestObject("Object Repository/FilesPage/SortList-Option"))
					TestObject sortIconDown=WebUI.modifyObjectProperty(findTestObject('Object Repository/FilesPage/SortBy-Order'), 'class', 'equals',"down-arrow tile-sortable-icon focus_enable_class", true)
					def sortIconUp=WebUI.waitForElementPresent(findTestObject('Object Repository/FilesPage/SortBy-Order'), 3, FailureHandling.CONTINUE_ON_FAILURE)
					def isIconPresent=WebUI.waitForElementPresent(sortIconDown, 3, FailureHandling.CONTINUE_ON_FAILURE)
					println("sortIconUp - "+sortIconUp)
					println("sortIconDown - "+isIconPresent)
					if(sortIconUp)
					{
						WebUI.click(findTestObject('Object Repository/FilesPage/SortBy-Order'))
						WebUI.delay(2)
					}
					def isElemenetPresent=WebUI.waitForElementVisible(findTestObject('FilesPage/RowItem_CompressedFile_TileView'), 10, FailureHandling.CONTINUE_ON_FAILURE)
					println  "RowItem_CompressedFile_TileView - "+isElemenetPresent
					result=isElemenetPresent
				}
				else {
					def isElemenetPresent=WebUI.waitForElementVisible(findTestObject('FilesPage/RowItem_CompressedFile_ListView'), 10, FailureHandling.CONTINUE_ON_FAILURE)
					println  "RowItem_CompressedFile_ListView - "+isElemenetPresent
					result=isElemenetPresent
				}
				WebUI.delay(1)
				println "=================================================================="
				println" UNCOMPRESS SCENARIO"
				println "=================================================================="
				String compressedFolderName=(new jobActions.CreateFolderPageTestObj()).VerifyCompressedFile(TestCaseName,Operation)
				extentTest.log(LogStatus.PASS, "Compressed FolderName "+compressedFolderName)
				WebUI.setText(findTestObject('FilesPage/FilesSearch_filter'), compressedFolderName)
				WebUI.sendKeys(findTestObject('JobDetailsPage/TextBx_DetailsFilter'), Keys.chord(Keys.ENTER))
				extentTest.log(LogStatus.PASS, 'Searched for Compressed Folder ')
				TestObject newFileObj
				TestObject newFolderObj
				String UnCompressedFolderName
				if (TestCaseName.contains('tile view')) {
					WebUI.delay(2)
					newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_TileView'), 'title', 'equals',
							compressedFolderName, true )
					newFolderObj=new TestObject('objectName')
					newFolderObj.addProperty('xpath', ConditionType.EQUALS, "//label[contains(text(),'MyFolderCompress_TV_archive_')]")
					UnCompressedFolderName='MyFolderCompress_TV'
				}
				else {
					newFileObj = WebUI.modifyObjectProperty(findTestObject('FilesPage/RowItem_File_ListView'), 'title','equals', compressedFolderName, true)
					newFolderObj=new TestObject('objectName')
					newFolderObj.addProperty('xpath', ConditionType.EQUALS, "//div[contains(text(),'MyFolderCompress_LV_archive_')]")
					UnCompressedFolderName='MyFolderCompress_LV'
					WebUI.delay(3)
				}

				WebUI.click(newFileObj)
				WebUI.rightClick(newFileObj)
				extentTest.log(LogStatus.PASS, 'rightClicked on Compressed File ')
				WebUI.delay(3)
				WebUI.click(findTestObject('FilesPage/ContextMenu_FileGrid_UnCompress'))
				extentTest.log(LogStatus.PASS, 'performed Un-Compress Operation on'+ compressedFolderName)
				WebUI.delay(3)
				WebUI.rightClick(newFileObj)
				WebUI.delay(2)
				TestObject newFileOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/ContextMenu_FileOperation'), 'id', 'equals', 'Delete', true)
				WebUI.click(newFileOp)
				WebUI.click(findTestObject('GenericObjects/btn_Yes'))
				WebUI.delay(2)
				extentTest.log(LogStatus.PASS, 'Deleted Compressed File ')
				WebUI.click(newFolderObj)
				WebUI.doubleClick(newFolderObj)
				extentTest.log(LogStatus.PASS, 'Navigated to Uncompressed Folder')
				WebUI.delay(2)
				result=(new jobActions.CreateFolderPageTestObj()).VerifyUnCompressedFile(UnCompressedFolderName,Operation)
				extentTest.log(LogStatus.PASS, 'Verified Uncompressed folder contains original file ')
				return result
				break

			case 'Download_icon':
				println(Operation)
				WebUI.click(findTestObject('FilesPage/TopMenuIcon_ellipses'))
				TestObject newFolderOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/Download_Topmenu_Icons'), 'id', 'equals', Operation, true)
				WebUI.click(newFolderOp)

				WebUI.delay(5)

				File downloadFolder = new File(GlobalVariable.G_DownloadFolder)

				List namesOfFiles = Arrays.asList(downloadFolder.list())

				if (namesOfFiles.contains('MyFolder')) {
					println('success')
					//extentTest.log(LogStatus.PASS, 'file to downloaded ')

				} else {
					println('fail')
				}
				return true
				break



			case 'delete_icon':
				TestObject newFolderOp=WebUI.modifyObjectProperty(findTestObject('FilesPage/FileOperations_Icon'), 'id', 'equals', Operation, true)
				WebUI.click(newFolderOp)
				WebUI.delay(3)
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



		}
	}
}