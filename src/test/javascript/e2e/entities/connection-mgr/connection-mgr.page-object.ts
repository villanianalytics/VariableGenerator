import { element, by, ElementFinder } from 'protractor';

export class ConnectionMgrComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-connection-mgr div table .btn-danger'));
  title = element.all(by.css('jhi-connection-mgr div h2#page-heading span')).first();
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

export class ConnectionMgrUpdatePage {
  pageTitle = element(by.id('jhi-connection-mgr-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  uRLInput = element(by.id('field_uRL'));
  usernameInput = element(by.id('field_username'));
  passwordInput = element(by.id('field_password'));
  identityDomainInput = element(by.id('field_identityDomain'));

  connectionNameSelect = element(by.id('field_connectionName'));
  connectionNameSelect = element(by.id('field_connectionName'));
  connectionNameSelect = element(by.id('field_connectionName'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setURLInput(uRL: string): Promise<void> {
    await this.uRLInput.sendKeys(uRL);
  }

  async getURLInput(): Promise<string> {
    return await this.uRLInput.getAttribute('value');
  }

  async setUsernameInput(username: string): Promise<void> {
    await this.usernameInput.sendKeys(username);
  }

  async getUsernameInput(): Promise<string> {
    return await this.usernameInput.getAttribute('value');
  }

  async setPasswordInput(password: string): Promise<void> {
    await this.passwordInput.sendKeys(password);
  }

  async getPasswordInput(): Promise<string> {
    return await this.passwordInput.getAttribute('value');
  }

  async setIdentityDomainInput(identityDomain: string): Promise<void> {
    await this.identityDomainInput.sendKeys(identityDomain);
  }

  async getIdentityDomainInput(): Promise<string> {
    return await this.identityDomainInput.getAttribute('value');
  }

  async connectionNameSelectLastOption(): Promise<void> {
    await this.connectionNameSelect.all(by.tagName('option')).last().click();
  }

  async connectionNameSelectOption(option: string): Promise<void> {
    await this.connectionNameSelect.sendKeys(option);
  }

  getConnectionNameSelect(): ElementFinder {
    return this.connectionNameSelect;
  }

  async getConnectionNameSelectedOption(): Promise<string> {
    return await this.connectionNameSelect.element(by.css('option:checked')).getText();
  }

  async connectionNameSelectLastOption(): Promise<void> {
    await this.connectionNameSelect.all(by.tagName('option')).last().click();
  }

  async connectionNameSelectOption(option: string): Promise<void> {
    await this.connectionNameSelect.sendKeys(option);
  }

  getConnectionNameSelect(): ElementFinder {
    return this.connectionNameSelect;
  }

  async getConnectionNameSelectedOption(): Promise<string> {
    return await this.connectionNameSelect.element(by.css('option:checked')).getText();
  }

  async connectionNameSelectLastOption(): Promise<void> {
    await this.connectionNameSelect.all(by.tagName('option')).last().click();
  }

  async connectionNameSelectOption(option: string): Promise<void> {
    await this.connectionNameSelect.sendKeys(option);
  }

  getConnectionNameSelect(): ElementFinder {
    return this.connectionNameSelect;
  }

  async getConnectionNameSelectedOption(): Promise<string> {
    return await this.connectionNameSelect.element(by.css('option:checked')).getText();
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

export class ConnectionMgrDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-connectionMgr-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-connectionMgr'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
