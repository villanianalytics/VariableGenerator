import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CubesComponentsPage, CubesDeleteDialog, CubesUpdatePage } from './cubes.page-object';

const expect = chai.expect;

describe('Cubes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cubesComponentsPage: CubesComponentsPage;
  let cubesUpdatePage: CubesUpdatePage;
  let cubesDeleteDialog: CubesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Cubes', async () => {
    await navBarPage.goToEntity('cubes');
    cubesComponentsPage = new CubesComponentsPage();
    await browser.wait(ec.visibilityOf(cubesComponentsPage.title), 5000);
    expect(await cubesComponentsPage.getTitle()).to.eq('Cubes');
    await browser.wait(ec.or(ec.visibilityOf(cubesComponentsPage.entities), ec.visibilityOf(cubesComponentsPage.noResult)), 1000);
  });

  it('should load create Cubes page', async () => {
    await cubesComponentsPage.clickOnCreateButton();
    cubesUpdatePage = new CubesUpdatePage();
    expect(await cubesUpdatePage.getPageTitle()).to.eq('Create or edit a Cubes');
    await cubesUpdatePage.cancel();
  });

  it('should create and save Cubes', async () => {
    const nbButtonsBeforeCreate = await cubesComponentsPage.countDeleteButtons();

    await cubesComponentsPage.clickOnCreateButton();

    await promise.all([cubesUpdatePage.setCubeInput('cube')]);

    expect(await cubesUpdatePage.getCubeInput()).to.eq('cube', 'Expected Cube value to be equals to cube');

    await cubesUpdatePage.save();
    expect(await cubesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cubesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Cubes', async () => {
    const nbButtonsBeforeDelete = await cubesComponentsPage.countDeleteButtons();
    await cubesComponentsPage.clickOnLastDeleteButton();

    cubesDeleteDialog = new CubesDeleteDialog();
    expect(await cubesDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Cubes?');
    await cubesDeleteDialog.clickOnConfirmButton();

    expect(await cubesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
