import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VariableGeneratorSharedModule } from 'app/shared/shared.module';
import { VariableNameComponent } from './variable-name.component';
import { VariableNameDetailComponent } from './variable-name-detail.component';
import { VariableNameUpdateComponent } from './variable-name-update.component';
import { VariableNameDeleteDialogComponent } from './variable-name-delete-dialog.component';
import { variableNameRoute } from './variable-name.route';

@NgModule({
  imports: [VariableGeneratorSharedModule, RouterModule.forChild(variableNameRoute)],
  declarations: [VariableNameComponent, VariableNameDetailComponent, VariableNameUpdateComponent, VariableNameDeleteDialogComponent],
  entryComponents: [VariableNameDeleteDialogComponent],
})
export class VariableGeneratorVariableNameModule {}
