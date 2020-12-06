import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICubes, Cubes } from 'app/shared/model/cubes.model';
import { CubesService } from './cubes.service';

@Component({
  selector: 'jhi-cubes-update',
  templateUrl: './cubes-update.component.html',
})
export class CubesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    cube: [],
  });

  constructor(protected cubesService: CubesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cubes }) => {
      this.updateForm(cubes);
    });
  }

  updateForm(cubes: ICubes): void {
    this.editForm.patchValue({
      id: cubes.id,
      cube: cubes.cube,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cubes = this.createFromForm();
    if (cubes.id !== undefined) {
      this.subscribeToSaveResponse(this.cubesService.update(cubes));
    } else {
      this.subscribeToSaveResponse(this.cubesService.create(cubes));
    }
  }

  private createFromForm(): ICubes {
    return {
      ...new Cubes(),
      id: this.editForm.get(['id'])!.value,
      cube: this.editForm.get(['cube'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICubes>>): void {
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
}
