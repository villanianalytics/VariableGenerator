import { element, by, ElementFinder } from 'protractor';

export class VariableNameComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-variable-name div table .btn-danger'));
  title = element.all(by.css('jhi-variable-name div h2#page-heading span')).first();
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

export class VariableNameUpdatePage {
  pageTitle = element(by.id('jhi-variable-name-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  variableNameInput = element(by.id('field_variableName'));

  listUDSelect = element(by.id('field_listUD'));
  cubeSelect = element(by.id('field_cube'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setVariableNameInput(variableName: string): Promise<void> {
    await this.variableNameInput.sendKeys(variableName);
  }

  async getVariableNameInput(): Promise<string> {
    return await this.variableNameInput.getAttribute('value');
  }

  async listUDSelectLastOption(): Promise<void> {
    await this.listUDSelect.all(by.tagName('option')).last().click();
  }

  async listUDSelectOption(option: string): Promise<void> {
    await this.listUDSelect.sendKeys(option);
  }

  getListUDSelect(): ElementFinder {
    return this.listUDSelect;
  }

  async getListUDSelectedOption(): Promise<string> {
    return await this.listUDSelect.element(by.css('option:checked')).getText();
  }

  async cubeSelectLastOption(): Promise<void> {
    await this.cubeSelect.all(by.tagName('option')).last().click();
  }

  async cubeSelectOption(option: string): Promise<void> {
    await this.cubeSelect.sendKeys(option);
  }

  getCubeSelect(): ElementFinder {
    return this.cubeSelect;
  }

  async getCubeSelectedOption(): Promise<string> {
    return await this.cubeSelect.element(by.css('option:checked')).getText();
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

export class VariableNameDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-variableName-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-variableName'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
