import { element, by, ElementFinder } from 'protractor';

export class CubesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-cubes div table .btn-danger'));
  title = element.all(by.css('jhi-cubes div h2#page-heading span')).first();
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

export class CubesUpdatePage {
  pageTitle = element(by.id('jhi-cubes-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  cubeInput = element(by.id('field_cube'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setCubeInput(cube: string): Promise<void> {
    await this.cubeInput.sendKeys(cube);
  }

  async getCubeInput(): Promise<string> {
    return await this.cubeInput.getAttribute('value');
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

export class CubesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-cubes-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-cubes'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
