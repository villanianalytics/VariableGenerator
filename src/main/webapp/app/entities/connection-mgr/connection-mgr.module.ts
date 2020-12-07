import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VariableGeneratorSharedModule } from 'app/shared/shared.module';
import { ConnectionMgrComponent } from './connection-mgr.component';
import { ConnectionMgrDetailComponent } from './connection-mgr-detail.component';
import { ConnectionMgrUpdateComponent } from './connection-mgr-update.component';
import { ConnectionMgrDeleteDialogComponent } from './connection-mgr-delete-dialog.component';
import { connectionMgrRoute } from './connection-mgr.route';

@NgModule({
  imports: [VariableGeneratorSharedModule, RouterModule.forChild(connectionMgrRoute)],
  declarations: [ConnectionMgrComponent, ConnectionMgrDetailComponent, ConnectionMgrUpdateComponent, ConnectionMgrDeleteDialogComponent],
  entryComponents: [ConnectionMgrDeleteDialogComponent],
})
export class VariableGeneratorConnectionMgrModule {}
