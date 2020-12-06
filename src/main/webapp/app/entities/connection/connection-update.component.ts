import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IConnection, Connection } from 'app/shared/model/connection.model';
import { ConnectionService } from './connection.service';
import { IApp } from 'app/shared/model/app.model';
import { AppService } from 'app/entities/app/app.service';
import { ICubes } from 'app/shared/model/cubes.model';
import { CubesService } from 'app/entities/cubes/cubes.service';
import { IVariableName } from 'app/shared/model/variable-name.model';
import { VariableNameService } from 'app/entities/variable-name/variable-name.service';

type SelectableEntity = IApp | ICubes | IVariableName;

@Component({
  selector: 'jhi-connection-update',
  templateUrl: './connection-update.component.html',
})
export class ConnectionUpdateComponent implements OnInit {
  isSaving = false;
  connections: IApp[] = [];
  connections: ICubes[] = [];
  connections: IVariableName[] = [];

  editForm = this.fb.group({
    id: [],
    connection: [],
    description: [],
    uRL: [],
    username: [],
    password: [],
    identityDomain: [],
    connection: [],
    connection: [],
    connection: [],
  });

  constructor(
    protected connectionService: ConnectionService,
    protected appService: AppService,
    protected cubesService: CubesService,
    protected variableNameService: VariableNameService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ connection }) => {
      this.updateForm(connection);

      this.appService
        .query({ filter: 'connection-is-null' })
        .pipe(
          map((res: HttpResponse<IApp[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IApp[]) => {
          if (!connection.connection || !connection.connection.id) {
            this.connections = resBody;
          } else {
            this.appService
              .find(connection.connection.id)
              .pipe(
                map((subRes: HttpResponse<IApp>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IApp[]) => (this.connections = concatRes));
          }
        });

      this.cubesService
        .query({ filter: 'connection-is-null' })
        .pipe(
          map((res: HttpResponse<ICubes[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICubes[]) => {
          if (!connection.connection || !connection.connection.id) {
            this.connections = resBody;
          } else {
            this.cubesService
              .find(connection.connection.id)
              .pipe(
                map((subRes: HttpResponse<ICubes>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICubes[]) => (this.connections = concatRes));
          }
        });

      this.variableNameService
        .query({ filter: 'connection-is-null' })
        .pipe(
          map((res: HttpResponse<IVariableName[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IVariableName[]) => {
          if (!connection.connection || !connection.connection.id) {
            this.connections = resBody;
          } else {
            this.variableNameService
              .find(connection.connection.id)
              .pipe(
                map((subRes: HttpResponse<IVariableName>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IVariableName[]) => (this.connections = concatRes));
          }
        });
    });
  }

  updateForm(connection: IConnection): void {
    this.editForm.patchValue({
      id: connection.id,
      connection: connection.connection,
      description: connection.description,
      uRL: connection.uRL,
      username: connection.username,
      password: connection.password,
      identityDomain: connection.identityDomain,
      connection: connection.connection,
      connection: connection.connection,
      connection: connection.connection,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const connection = this.createFromForm();
    if (connection.id !== undefined) {
      this.subscribeToSaveResponse(this.connectionService.update(connection));
    } else {
      this.subscribeToSaveResponse(this.connectionService.create(connection));
    }
  }

  private createFromForm(): IConnection {
    return {
      ...new Connection(),
      id: this.editForm.get(['id'])!.value,
      connection: this.editForm.get(['connection'])!.value,
      description: this.editForm.get(['description'])!.value,
      uRL: this.editForm.get(['uRL'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      identityDomain: this.editForm.get(['identityDomain'])!.value,
      connection: this.editForm.get(['connection'])!.value,
      connection: this.editForm.get(['connection'])!.value,
      connection: this.editForm.get(['connection'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConnection>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
