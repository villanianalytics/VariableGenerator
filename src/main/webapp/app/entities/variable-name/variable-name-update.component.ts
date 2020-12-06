import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVariableName, VariableName } from 'app/shared/model/variable-name.model';
import { VariableNameService } from './variable-name.service';
import { ICubes } from 'app/shared/model/cubes.model';
import { CubesService } from 'app/entities/cubes/cubes.service';

@Component({
  selector: 'jhi-variable-name-update',
  templateUrl: './variable-name-update.component.html',
})
export class VariableNameUpdateComponent implements OnInit {
  isSaving = false;
  cubes: ICubes[] = [];

  editForm = this.fb.group({
    id: [],
    variableName: [],
    cube: [],
  });

  constructor(
    protected variableNameService: VariableNameService,
    protected cubesService: CubesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ variableName }) => {
      this.updateForm(variableName);

      this.cubesService.query().subscribe((res: HttpResponse<ICubes[]>) => (this.cubes = res.body || []));
    });
  }

  updateForm(variableName: IVariableName): void {
    this.editForm.patchValue({
      id: variableName.id,
      variableName: variableName.variableName,
      cube: variableName.cube,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const variableName = this.createFromForm();
    if (variableName.id !== undefined) {
      this.subscribeToSaveResponse(this.variableNameService.update(variableName));
    } else {
      this.subscribeToSaveResponse(this.variableNameService.create(variableName));
    }
  }

  private createFromForm(): IVariableName {
    return {
      ...new VariableName(),
      id: this.editForm.get(['id'])!.value,
      variableName: this.editForm.get(['variableName'])!.value,
      cube: this.editForm.get(['cube'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVariableName>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICubes): any {
    return item.id;
  }
}
