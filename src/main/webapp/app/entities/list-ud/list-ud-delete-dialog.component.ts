import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListUD } from 'app/shared/model/list-ud.model';
import { ListUDService } from './list-ud.service';

@Component({
  templateUrl: './list-ud-delete-dialog.component.html',
})
export class ListUDDeleteDialogComponent {
  listUD?: IListUD;

  constructor(protected listUDService: ListUDService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.listUDService.delete(id).subscribe(() => {
      this.eventManager.broadcast('listUDListModification');
      this.activeModal.close();
    });
  }
}
