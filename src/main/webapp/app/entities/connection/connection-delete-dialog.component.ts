import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConnection } from 'app/shared/model/connection.model';
import { ConnectionService } from './connection.service';

@Component({
  templateUrl: './connection-delete-dialog.component.html',
})
export class ConnectionDeleteDialogComponent {
  connection?: IConnection;

  constructor(
    protected connectionService: ConnectionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.connectionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('connectionListModification');
      this.activeModal.close();
    });
  }
}
