import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VariableGeneratorSharedModule } from 'app/shared/shared.module';
import { ListUDComponent } from './list-ud.component';
import { ListUDDetailComponent } from './list-ud-detail.component';
import { ListUDUpdateComponent } from './list-ud-update.component';
import { ListUDDeleteDialogComponent } from './list-ud-delete-dialog.component';
import { listUDRoute } from './list-ud.route';

@NgModule({
  imports: [VariableGeneratorSharedModule, RouterModule.forChild(listUDRoute)],
  declarations: [ListUDComponent, ListUDDetailComponent, ListUDUpdateComponent, ListUDDeleteDialogComponent],
  entryComponents: [ListUDDeleteDialogComponent],
})
export class VariableGeneratorListUDModule {}
