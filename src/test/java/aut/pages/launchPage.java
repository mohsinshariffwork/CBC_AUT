package aut.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class launchPage extends AbstractPage {

	private String verifyPopupText = "//*[contains(text(),'Save diagrams to:')]";
	private String decideLater = "//*[contains(text(),'Decide later')]";
	private String file = "//*[contains(text(),'File')]";
	private String save = "//*[text()='Save']";
	private String selectXpath = "//*[@class='geDialog']//select[contains(@style,'ellipsis')]";
	private String okXpath = "//button[@class='geBtn gePrimaryBtn' and text()='OK']";
	private String newFile = "//*[contains(text(),'New.')]";
	private String diagramName = "//*[contains(text(),'Diagram Name:')]//input";
	private String blankDiagram = "//*[contains(text(),'Blank Diagram')]";
	private String create = "//*[contains(text(),'Create')]";
	private String searchShapes = "//*[@placeholder='Search Shapes']";
	private String firstElementfrmSearch = "//*[@class='geSidebar geSearchSidebar']//a[1]";
	private String firstBlockBefore = "//*[@class='geDiagramContainer geDiagramBackdrop']//*[local-name()='g']//*[local-name()='g'][1]//*[local-name()='ellipse']";
	private String firstBlock = "//*[@class='geDiagramContainer geDiagramBackdrop']//*[local-name()='g']//*[local-name()='g'][2]//*[local-name()='ellipse']";
	private String firstBlockUpdate = "//*[@class='geDiagramContainer geDiagramBackdrop']//*[local-name()='g']//*[local-name()='g'][2]//*[local-name()='rect']";
	private String Arrange = "//*[contains(text(),'Arrange')]";
	private String Layout = "//*[contains(text(),'Layout')]";
	private String HorizontalFlow = "//*[contains(text(),'Horizontal Flow')]";
	private String iterationDiagram = "//*[@class='geDiagramContainer geDiagramBackdrop']//*[local-name()='g' and contains(@style,'visibility')]";
	private String iterationDiagramLast = "//*[@class='geDiagramContainer geDiagramBackdrop']//*[local-name()='g' and contains(@style,'visibility')][last()]";
	private String cursorBottom = "//*[contains(@style,'cursor: s-resize')]//*[local-name()='image']";
	private String arrow = "//img[contains(@title,'Click to connect and clone')]";
	private String editStyle = "//*[contains(text(),'Edit Style...')]";
	private String edit = "//*[@class='geMenubar' and contains(@style,'position')]//*[text()='Edit']";
	private String textArea = "//textarea";
	private String apply = "//button[text()='Apply']";
	private String undo = "//*[@class='geToolbar' and contains(@style,'pad')]//*[@class='geSprite geSprite-undo']";
	private String delete = "//*[@class='geToolbar' and contains(@style,'pad')]//*[@class='geSprite geSprite-delete']";
	private String space = "//*[@class='geDiagramContainer geDiagramBackdrop']";
	private String noResults = "//*[contains(text(),'No results for')]";
	private String iterationDiagramSetText = "//*[@class='geDiagramContainer geDiagramBackdrop']//*[local-name()='g' and contains(@style,'move') and contains(@style,'visible')]";
	private String zoom = "//*[contains(@title,'Zoom (Alt+Mousewheel)')]";
	private String zoomVal1 = "//td[text()='50%']";
	private String zoomVal2 = "//td[text()='100%']";

	public void launchApplication() throws InterruptedException {
		setup();
		driver.get("https://app.diagrams.net/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public void verifyAppLaunched() {
		Assert.assertTrue(driver.findElement(By.xpath(verifyPopupText)).isDisplayed());
		clickElement(decideLater);
	}

	public void createFolowchartOption() {
		clickElement(file);
		clickElement(newFile);
		setInputValue(diagramName, "Flowchart" + randomGenerator() + ".drawio");
		clickElement(blankDiagram);
		clickElement(create);
	}

	public void addShape(String shape, int x, int y) throws InterruptedException {
		Thread.sleep(1000);
		searchShape(shape);
		try {
			dragAndDrop(firstElementfrmSearch, x, y);
		} catch (NoSuchElementException e) {
			try {
				Assert.assertTrue(driver.findElement(By.xpath(noResults)).isDisplayed());
			} catch (Exception e1) {
				throw e1;
			}
		}
	}

	public void setHorizontalFlow() {
		selectAll();
		clickElement(Arrange);
		clickElement(Layout);
		clickElement(HorizontalFlow);
	}

	public void searchShape(String shape) {
		clickElement(searchShapes);
		setInputValue(searchShapes, shape);
		clickEnter(searchShapes);
	}

	public void setFlowchartValues(String arrList) throws InterruptedException {
		String[] arr = arrList.split(",");

		clickElement(space);
		Thread.sleep(1000);
		List<WebElement> eleList = driver.findElements(By.xpath(iterationDiagramSetText));// iterationDiagramSetText
																							// //iterationDiagram
		int i = 0;

		for (WebElement ele : eleList) {
			Thread.sleep(1000);
			doubleClick(ele);
			pasteFromClipBoard(arr[i].trim());
			i++;
		}
	}

	public void verifyFlowchartSaved() {
		clickElement(file);
		clickElement(save);
		Select select = new Select(driver.findElement(By.xpath(selectXpath)));
		select.selectByVisibleText("Download");
		clickElement(okXpath);
	}

	public void addShapeWOScroll(String shape, int x1, int y1, String Text) throws InterruptedException {
		searchShape(shape);
		dragAndDrop(firstElementfrmSearch, x1, y1);
		Thread.sleep(500);
		doubleClick(driver.findElement(By.xpath(iterationDiagramLast)));
		Thread.sleep(500);
		pasteFromClipBoard(Text);
		Thread.sleep(500);
		clickElement(space);
	}

	public void addShape(String shape, int x1, int y1, String Text) throws InterruptedException {
		searchShape(shape);
		dragAndDropScroll(firstElementfrmSearch, x1, y1);
		Thread.sleep(500);
		doubleClick(iterationDiagramLast);
		Thread.sleep(500);
		pasteFromClipBoard(Text);
		Thread.sleep(500);
		clickElement(space);
	}

	public void updateShape(String from, String to) throws InterruptedException {

		clickElement(space);
		Thread.sleep(500);
		moveToEle(firstBlock);
		Thread.sleep(500);
		clickElement(edit);
		clickElement(editStyle);
		String text = getText(textArea);
		setInputValue(textArea, text.replace(from, to));
		clickElement(apply);

	}

	public void verifyUpdateShape(String to) throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		moveToEle(firstBlockUpdate);
		Assert.assertEquals(driver.findElement(By.xpath(firstBlockUpdate)).isDisplayed(), true,
				"Actual shape is not equals to Expected shape");
		clickElement(undo);

	}

	public void deleteShape() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		moveToEle(firstBlock);
		Thread.sleep(500);
		clickElement(delete);
	}

	public void verifyDeleteShape() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		try {
			driver.findElement(By.xpath(firstBlock)).isDisplayed();
		} catch (NoSuchElementException e) {
			Assert.assertTrue(true);
		}
		clickElement(undo);
		Assert.assertEquals(driver.findElement(By.xpath(firstBlock)).isDisplayed(), true);
	}

	public void verifyDeleteShapeText() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		Assert.assertTrue(getText(firstBlock).equals(""), "Failed to delete");
		clickElement(undo);
	}

	public void deleteShapeText() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		moveToEle(firstBlock);
		Thread.sleep(500);
		doubleClick(firstBlock);
		delete();
	}

	public void deleteFlowchart() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		moveToEle(firstBlock);
		Thread.sleep(500);
		selectAll();
		delete();
	}

	public void verifydeleteFlowchart() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		clickElement(zoom);
		clickElement(zoomVal1);
		try {
			driver.findElement(By.xpath(firstBlock)).isDisplayed();
		} catch (NoSuchElementException e) {
			Assert.assertTrue(true, "Failed to delete Flowchart");
		}
	}

	public void createArrow() throws InterruptedException {
		clickElement(space);
		Thread.sleep(500);
		List<WebElement> eleList = driver.findElements(By.xpath(iterationDiagram));
		clickElement(zoom);
		clickElement(zoomVal1);

		int i = 0;
		for (WebElement ele : eleList) {
			ele.click();
			Thread.sleep(500);
			clickElement(cursorBottom);
			mouseHover(cursorBottom);
			if (i < eleList.size() - 1) {
				mouseHover(driver.findElements(By.xpath(arrow)).get(1));
				driver.findElements(By.xpath(arrow)).get(1).click();
			}
			i++;
		}

		clickElement(zoom);
		clickElement(zoomVal2);

	}

}
