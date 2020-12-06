import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IListUD, ListUD } from 'app/shared/model/list-ud.model';
import { ListUDService } from './list-ud.service';
import { IVariableName } from 'app/shared/model/variable-name.model';
import { VariableNameService } from 'app/entities/variable-name/variable-name.service';

@Component({
  selector: 'jhi-list-ud-update',
  templateUrl: './list-ud-update.component.html',
})
export class ListUDUpdateComponent implements OnInit {
  isSaving = false;
  listnames: IVariableName[] = [];

  editForm = this.fb.group({
    id: [],
    listName: [],
    listValue: [],
    listName: [],
  });

  constructor(
    protected listUDService: ListUDService,
    protected variableNameService: VariableNameService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listUD }) => {
      this.updateForm(listUD);

      this.variableNameService
        .query({ filter: 'listud-is-null' })
        .pipe(
          map((res: HttpResponse<IVariableName[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IVariableName[]) => {
          if (!listUD.listName || !listUD.listName.id) {
            this.listnames = resBody;
          } else {
            this.variableNameService
              .find(listUD.listName.id)
              .pipe(
                map((subRes: HttpResponse<IVariableName>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IVariableName[]) => (this.listnames = concatRes));
          }
        });
    });
  }

  updateForm(listUD: IListUD): void {
    this.editForm.patchValue({
      id: listUD.id,
      listName: listUD.listName,
      listValue: listUD.listValue,
      listName: listUD.listName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const listUD = this.createFromForm();
    if (listUD.id !== undefined) {
      this.subscribeToSaveResponse(this.listUDService.update(listUD));
    } else {
      this.subscribeToSaveResponse(this.listUDService.create(listUD));
    }
  }

  private createFromForm(): IListUD {
    return {
      ...new ListUD(),
      id: this.editForm.get(['id'])!.value,
      listName: this.editForm.get(['listName'])!.value,
      listValue: this.editForm.get(['listValue'])!.value,
      listName: this.editForm.get(['listName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IListUD>>): void {
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

  trackById(index: number, item: IVariableName): any {
    return item.id;
  }
}
