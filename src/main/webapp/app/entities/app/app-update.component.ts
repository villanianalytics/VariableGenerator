import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IApp, App } from 'app/shared/model/app.model';
import { AppService } from './app.service';

@Component({
  selector: 'jhi-app-update',
  templateUrl: './app-update.component.html',
})
export class AppUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    variableValue: [],
    effDt: [],
  });

  constructor(protected appService: AppService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ app }) => {
      if (!app.id) {
        const today = moment().startOf('day');
        app.effDt = today;
      }

      this.updateForm(app);
    });
  }

  updateForm(app: IApp): void {
    this.editForm.patchValue({
      id: app.id,
      variableValue: app.variableValue,
      effDt: app.effDt ? app.effDt.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const app = this.createFromForm();
    if (app.id !== undefined) {
      this.subscribeToSaveResponse(this.appService.update(app));
    } else {
      this.subscribeToSaveResponse(this.appService.create(app));
    }
  }

  private createFromForm(): IApp {
    return {
      ...new App(),
      id: this.editForm.get(['id'])!.value,
      variableValue: this.editForm.get(['variableValue'])!.value,
      effDt: this.editForm.get(['effDt'])!.value ? moment(this.editForm.get(['effDt'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApp>>): void {
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
