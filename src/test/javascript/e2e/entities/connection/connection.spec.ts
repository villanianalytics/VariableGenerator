import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ConnectionComponentsPage, ConnectionDeleteDialog, ConnectionUpdatePage } from './connection.page-object';

const expect = chai.expect;

describe('Connection e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let connectionComponentsPage: ConnectionComponentsPage;
  let connectionUpdatePage: ConnectionUpdatePage;
  let connectionDeleteDialog: ConnectionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Connections', async () => {
    await navBarPage.goToEntity('connection');
    connectionComponentsPage = new ConnectionComponentsPage();
    await browser.wait(ec.visibilityOf(connectionComponentsPage.title), 5000);
    expect(await connectionComponentsPage.getTitle()).to.eq('Connections');
    await browser.wait(ec.or(ec.visibilityOf(connectionComponentsPage.entities), ec.visibilityOf(connectionComponentsPage.noResult)), 1000);
  });

  it('should load create Connection page', async () => {
    await connectionComponentsPage.clickOnCreateButton();
    connectionUpdatePage = new ConnectionUpdatePage();
    expect(await connectionUpdatePage.getPageTitle()).to.eq('Create or edit a Connection');
    await connectionUpdatePage.cancel();
  });

  it('should create and save Connections', async () => {
    const nbButtonsBeforeCreate = await connectionComponentsPage.countDeleteButtons();

    await connectionComponentsPage.clickOnCreateButton();

    await promise.all([
      connectionUpdatePage.setConnectionInput('connection'),
      connectionUpdatePage.setDescriptionInput('description'),
      connectionUpdatePage.setURLInput('uRL'),
      connectionUpdatePage.setUsernameInput('username'),
      connectionUpdatePage.setPasswordInput('password'),
      connectionUpdatePage.setIdentityDomainInput('identityDomain'),
      connectionUpdatePage.connectionSelectLastOption(),
      connectionUpdatePage.connectionSelectLastOption(),
      connectionUpdatePage.connectionSelectLastOption(),
    ]);

    expect(await connectionUpdatePage.getConnectionInput()).to.eq('connection', 'Expected Connection value to be equals to connection');
    expect(await connectionUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await connectionUpdatePage.getURLInput()).to.eq('uRL', 'Expected URL value to be equals to uRL');
    expect(await connectionUpdatePage.getUsernameInput()).to.eq('username', 'Expected Username value to be equals to username');
    expect(await connectionUpdatePage.getPasswordInput()).to.eq('password', 'Expected Password value to be equals to password');
    expect(await connectionUpdatePage.getIdentityDomainInput()).to.eq(
      'identityDomain',
      'Expected IdentityDomain value to be equals to identityDomain'
    );

    await connectionUpdatePage.save();
    expect(await connectionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await connectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Connection', async () => {
    const nbButtonsBeforeDelete = await connectionComponentsPage.countDeleteButtons();
    await connectionComponentsPage.clickOnLastDeleteButton();

    connectionDeleteDialog = new ConnectionDeleteDialog();
    expect(await connectionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Connection?');
    await connectionDeleteDialog.clickOnConfirmButton();

    expect(await connectionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
