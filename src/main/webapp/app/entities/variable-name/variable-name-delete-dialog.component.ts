import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVariableName } from 'app/shared/model/variable-name.model';
import { VariableNameService } from './variable-name.service';

@Component({
  templateUrl: './variable-name-delete-dialog.component.html',
})
export class VariableNameDeleteDialogComponent {
  variableName?: IVariableName;

  constructor(
    protected variableNameService: VariableNameService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.variableNameService.delete(id).subscribe(() => {
      this.eventManager.broadcast('variableNameListModification');
      this.activeModal.close();
    });
  }
}
