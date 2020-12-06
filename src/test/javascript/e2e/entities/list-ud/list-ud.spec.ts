import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ListUDComponentsPage, ListUDDeleteDialog, ListUDUpdatePage } from './list-ud.page-object';

const expect = chai.expect;

describe('ListUD e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let listUDComponentsPage: ListUDComponentsPage;
  let listUDUpdatePage: ListUDUpdatePage;
  let listUDDeleteDialog: ListUDDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ListUDS', async () => {
    await navBarPage.goToEntity('list-ud');
    listUDComponentsPage = new ListUDComponentsPage();
    await browser.wait(ec.visibilityOf(listUDComponentsPage.title), 5000);
    expect(await listUDComponentsPage.getTitle()).to.eq('List UDS');
    await browser.wait(ec.or(ec.visibilityOf(listUDComponentsPage.entities), ec.visibilityOf(listUDComponentsPage.noResult)), 1000);
  });

  it('should load create ListUD page', async () => {
    await listUDComponentsPage.clickOnCreateButton();
    listUDUpdatePage = new ListUDUpdatePage();
    expect(await listUDUpdatePage.getPageTitle()).to.eq('Create or edit a List UD');
    await listUDUpdatePage.cancel();
  });

  it('should create and save ListUDS', async () => {
    const nbButtonsBeforeCreate = await listUDComponentsPage.countDeleteButtons();

    await listUDComponentsPage.clickOnCreateButton();

    await promise.all([
      listUDUpdatePage.setListNameInput('listName'),
      listUDUpdatePage.setListValueInput('listValue'),
      listUDUpdatePage.listNameSelectLastOption(),
    ]);

    expect(await listUDUpdatePage.getListNameInput()).to.eq('listName', 'Expected ListName value to be equals to listName');
    expect(await listUDUpdatePage.getListValueInput()).to.eq('listValue', 'Expected ListValue value to be equals to listValue');

    await listUDUpdatePage.save();
    expect(await listUDUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await listUDComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ListUD', async () => {
    const nbButtonsBeforeDelete = await listUDComponentsPage.countDeleteButtons();
    await listUDComponentsPage.clickOnLastDeleteButton();

    listUDDeleteDialog = new ListUDDeleteDialog();
    expect(await listUDDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this List UD?');
    await listUDDeleteDialog.clickOnConfirmButton();

    expect(await listUDComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
