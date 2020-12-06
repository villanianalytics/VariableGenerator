import { element, by, ElementFinder } from 'protractor';

export class AppComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-app div table .btn-danger'));
  title = element.all(by.css('jhi-app div h2#page-heading span')).first();
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

export class AppUpdatePage {
  pageTitle = element(by.id('jhi-app-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  derivedValueInput = element(by.id('field_derivedValue'));
  variableValueInput = element(by.id('field_variableValue'));
  effDtInput = element(by.id('field_effDt'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  getDerivedValueInput(): ElementFinder {
    return this.derivedValueInput;
  }

  async setVariableValueInput(variableValue: string): Promise<void> {
    await this.variableValueInput.sendKeys(variableValue);
  }

  async getVariableValueInput(): Promise<string> {
    return await this.variableValueInput.getAttribute('value');
  }

  async setEffDtInput(effDt: string): Promise<void> {
    await this.effDtInput.sendKeys(effDt);
  }

  async getEffDtInput(): Promise<string> {
    return await this.effDtInput.getAttribute('value');
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

export class AppDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-app-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-app'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
