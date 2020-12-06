import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VariableGeneratorSharedModule } from 'app/shared/shared.module';
import { ConnectionComponent } from './connection.component';
import { ConnectionDetailComponent } from './connection-detail.component';
import { ConnectionUpdateComponent } from './connection-update.component';
import { ConnectionDeleteDialogComponent } from './connection-delete-dialog.component';
import { connectionRoute } from './connection.route';

@NgModule({
  imports: [VariableGeneratorSharedModule, RouterModule.forChild(connectionRoute)],
  declarations: [ConnectionComponent, ConnectionDetailComponent, ConnectionUpdateComponent, ConnectionDeleteDialogComponent],
  entryComponents: [ConnectionDeleteDialogComponent],
})
export class VariableGeneratorConnectionModule {}
