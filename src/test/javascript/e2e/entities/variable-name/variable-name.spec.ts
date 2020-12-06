import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VariableNameComponentsPage, VariableNameDeleteDialog, VariableNameUpdatePage } from './variable-name.page-object';

const expect = chai.expect;

describe('VariableName e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let variableNameComponentsPage: VariableNameComponentsPage;
  let variableNameUpdatePage: VariableNameUpdatePage;
  let variableNameDeleteDialog: VariableNameDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load VariableNames', async () => {
    await navBarPage.goToEntity('variable-name');
    variableNameComponentsPage = new VariableNameComponentsPage();
    await browser.wait(ec.visibilityOf(variableNameComponentsPage.title), 5000);
    expect(await variableNameComponentsPage.getTitle()).to.eq('Variable Names');
    await browser.wait(
      ec.or(ec.visibilityOf(variableNameComponentsPage.entities), ec.visibilityOf(variableNameComponentsPage.noResult)),
      1000
    );
  });

  it('should load create VariableName page', async () => {
    await variableNameComponentsPage.clickOnCreateButton();
    variableNameUpdatePage = new VariableNameUpdatePage();
    expect(await variableNameUpdatePage.getPageTitle()).to.eq('Create or edit a Variable Name');
    await variableNameUpdatePage.cancel();
  });

  it('should create and save VariableNames', async () => {
    const nbButtonsBeforeCreate = await variableNameComponentsPage.countDeleteButtons();

    await variableNameComponentsPage.clickOnCreateButton();

    await promise.all([variableNameUpdatePage.setVariableNameInput('variableName'), variableNameUpdatePage.cubeSelectLastOption()]);

    expect(await variableNameUpdatePage.getVariableNameInput()).to.eq(
      'variableName',
      'Expected VariableName value to be equals to variableName'
    );

    await variableNameUpdatePage.save();
    expect(await variableNameUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await variableNameComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last VariableName', async () => {
    const nbButtonsBeforeDelete = await variableNameComponentsPage.countDeleteButtons();
    await variableNameComponentsPage.clickOnLastDeleteButton();

    variableNameDeleteDialog = new VariableNameDeleteDialog();
    expect(await variableNameDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Variable Name?');
    await variableNameDeleteDialog.clickOnConfirmButton();

    expect(await variableNameComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
