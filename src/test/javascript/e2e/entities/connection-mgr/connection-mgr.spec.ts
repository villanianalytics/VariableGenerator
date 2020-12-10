import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ConnectionMgrComponentsPage, ConnectionMgrDeleteDialog, ConnectionMgrUpdatePage } from './connection-mgr.page-object';

const expect = chai.expect;

describe('ConnectionMgr e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let connectionMgrComponentsPage: ConnectionMgrComponentsPage;
  let connectionMgrUpdatePage: ConnectionMgrUpdatePage;
  let connectionMgrDeleteDialog: ConnectionMgrDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ConnectionMgrs', async () => {
    await navBarPage.goToEntity('connection-mgr');
    connectionMgrComponentsPage = new ConnectionMgrComponentsPage();
    await browser.wait(ec.visibilityOf(connectionMgrComponentsPage.title), 5000);
    expect(await connectionMgrComponentsPage.getTitle()).to.eq('Connection Mgrs');
    await browser.wait(
      ec.or(ec.visibilityOf(connectionMgrComponentsPage.entities), ec.visibilityOf(connectionMgrComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ConnectionMgr page', async () => {
    await connectionMgrComponentsPage.clickOnCreateButton();
    connectionMgrUpdatePage = new ConnectionMgrUpdatePage();
    expect(await connectionMgrUpdatePage.getPageTitle()).to.eq('Create or edit a Connection Mgr');
    await connectionMgrUpdatePage.cancel();
  });

  it('should create and save ConnectionMgrs', async () => {
    const nbButtonsBeforeCreate = await connectionMgrComponentsPage.countDeleteButtons();

    await connectionMgrComponentsPage.clickOnCreateButton();

    await promise.all([
      connectionMgrUpdatePage.setDescriptionInput('description'),
      connectionMgrUpdatePage.setURLInput('uRL'),
      connectionMgrUpdatePage.setUsernameInput('username'),
      connectionMgrUpdatePage.setPasswordInput('password'),
      connectionMgrUpdatePage.setIdentityDomainInput('identityDomain'),
      connectionMgrUpdatePage.connectionNameSelectLastOption(),
      connectionMgrUpdatePage.connectionNameSelectLastOption(),
      connectionMgrUpdatePage.connectionNameSelectLastOption(),
    ]);

    expect(await connectionMgrUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await connectionMgrUpdatePage.getURLInput()).to.eq('uRL', 'Expected URL value to be equals to uRL');
    expect(await connectionMgrUpdatePage.getUsernameInput()).to.eq('username', 'Expected Username value to be equals to username');
    expect(await connectionMgrUpdatePage.getPasswordInput()).to.eq('password', 'Expected Password value to be equals to password');
    expect(await connectionMgrUpdatePage.getIdentityDomainInput()).to.eq(
      'identityDomain',
      'Expected IdentityDomain value to be equals to identityDomain'
    );

    await connectionMgrUpdatePage.save();
    expect(await connectionMgrUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await connectionMgrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ConnectionMgr', async () => {
    const nbButtonsBeforeDelete = await connectionMgrComponentsPage.countDeleteButtons();
    await connectionMgrComponentsPage.clickOnLastDeleteButton();

    connectionMgrDeleteDialog = new ConnectionMgrDeleteDialog();
    expect(await connectionMgrDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Connection Mgr?');
    await connectionMgrDeleteDialog.clickOnConfirmButton();

    expect(await connectionMgrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
