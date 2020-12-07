import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConnectionMgr } from 'app/shared/model/connection-mgr.model';
import { ConnectionMgrService } from './connection-mgr.service';

@Component({
  templateUrl: './connection-mgr-delete-dialog.component.html',
})
export class ConnectionMgrDeleteDialogComponent {
  connectionMgr?: IConnectionMgr;

  constructor(
    protected connectionMgrService: ConnectionMgrService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.connectionMgrService.delete(id).subscribe(() => {
      this.eventManager.broadcast('connectionMgrListModification');
      this.activeModal.close();
    });
  }
}
