import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICubes } from 'app/shared/model/cubes.model';
import { CubesService } from './cubes.service';

@Component({
  templateUrl: './cubes-delete-dialog.component.html',
})
export class CubesDeleteDialogComponent {
  cubes?: ICubes;

  constructor(protected cubesService: CubesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cubesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cubesListModification');
      this.activeModal.close();
    });
  }
}
