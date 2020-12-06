import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariableName } from 'app/shared/model/variable-name.model';

@Component({
  selector: 'jhi-variable-name-detail',
  templateUrl: './variable-name-detail.component.html',
})
export class VariableNameDetailComponent implements OnInit {
  variableName: IVariableName | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variableName }) => (this.variableName = variableName));
  }

  previousState(): void {
    window.history.back();
  }
}
