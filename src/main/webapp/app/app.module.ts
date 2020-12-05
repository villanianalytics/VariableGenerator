import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { VariableGeneratorSharedModule } from 'app/shared/shared.module';
import { VariableGeneratorCoreModule } from 'app/core/core.module';
import { VariableGeneratorAppRoutingModule } from './app-routing.module';
import { VariableGeneratorHomeModule } from './home/home.module';
import { VariableGeneratorEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    VariableGeneratorSharedModule,
    VariableGeneratorCoreModule,
    VariableGeneratorHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    VariableGeneratorEntityModule,
    VariableGeneratorAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class VariableGeneratorAppModule {}
