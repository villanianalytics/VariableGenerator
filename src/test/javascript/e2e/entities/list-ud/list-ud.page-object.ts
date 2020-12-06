import { element, by, ElementFinder } from 'protractor';

export class ListUDComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-list-ud div table .btn-danger'));
  title = element.all(by.css('jhi-list-ud div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class ListUDUpdatePage {
  pageTitle = element(by.id('jhi-list-ud-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  listNameInput = element(by.id('field_listName'));
  listValueInput = element(by.id('field_listValue'));

  listNameSelect = element(by.id('field_listName'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setListNameInput(listName: string): Promise<void> {
    await this.listNameInput.sendKeys(listName);
  }

  async getListNameInput(): Promise<string> {
    return await this.listNameInput.getAttribute('value');
  }

  async setListValueInput(listValue: string): Promise<void> {
    await this.listValueInput.sendKeys(listValue);
  }

  async getListValueInput(): Promise<string> {
    return await this.listValueInput.getAttribute('value');
  }

  async listNameSelectLastOption(): Promise<void> {
    await this.listNameSelect.all(by.tagName('option')).last().click();
  }

  async listNameSelectOption(option: string): Promise<void> {
    await this.listNameSelect.sendKeys(option);
  }

  getListNameSelect(): ElementFinder {
    return this.listNameSelect;
  }

  async getListNameSelectedOption(): Promise<string> {
    return await this.listNameSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ListUDDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-listUD-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-listUD'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
