import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IConnectionMgr, ConnectionMgr } from 'app/shared/model/connection-mgr.model';
import { ConnectionMgrService } from './connection-mgr.service';
import { IApp } from 'app/shared/model/app.model';
import { AppService } from 'app/entities/app/app.service';
import { ICubes } from 'app/shared/model/cubes.model';
import { CubesService } from 'app/entities/cubes/cubes.service';
import { IVariableName } from 'app/shared/model/variable-name.model';
import { VariableNameService } from 'app/entities/variable-name/variable-name.service';

type SelectableEntity = IApp | ICubes | IVariableName;

@Component({
  selector: 'jhi-connection-mgr-update',
  templateUrl: './connection-mgr-update.component.html',
})
export class ConnectionMgrUpdateComponent implements OnInit {
  isSaving = false;
  connectionnames: IApp[] = [];
  connectionnames: ICubes[] = [];
  connectionnames: IVariableName[] = [];

  editForm = this.fb.group({
    id: [],
    description: [],
    uRL: [],
    username: [],
    password: [],
    identityDomain: [],
    connectionName: [],
    connectionName: [],
    connectionName: [],
  });

  constructor(
    protected connectionMgrService: ConnectionMgrService,
    protected appService: AppService,
    protected cubesService: CubesService,
    protected variableNameService: VariableNameService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ connectionMgr }) => {
      this.updateForm(connectionMgr);

      this.appService
        .query({ filter: 'connectionmgr-is-null' })
        .pipe(
          map((res: HttpResponse<IApp[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IApp[]) => {
          if (!connectionMgr.connectionName || !connectionMgr.connectionName.id) {
            this.connectionnames = resBody;
          } else {
            this.appService
              .find(connectionMgr.connectionName.id)
              .pipe(
                map((subRes: HttpResponse<IApp>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IApp[]) => (this.connectionnames = concatRes));
          }
        });

      this.cubesService
        .query({ filter: 'connectionmgr-is-null' })
        .pipe(
          map((res: HttpResponse<ICubes[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICubes[]) => {
          if (!connectionMgr.connectionName || !connectionMgr.connectionName.id) {
            this.connectionnames = resBody;
          } else {
            this.cubesService
              .find(connectionMgr.connectionName.id)
              .pipe(
                map((subRes: HttpResponse<ICubes>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICubes[]) => (this.connectionnames = concatRes));
          }
        });

      this.variableNameService
        .query({ filter: 'connectionmgr-is-null' })
        .pipe(
          map((res: HttpResponse<IVariableName[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IVariableName[]) => {
          if (!connectionMgr.connectionName || !connectionMgr.connectionName.id) {
            this.connectionnames = resBody;
          } else {
            this.variableNameService
              .find(connectionMgr.connectionName.id)
              .pipe(
                map((subRes: HttpResponse<IVariableName>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IVariableName[]) => (this.connectionnames = concatRes));
          }
        });
    });
  }

  updateForm(connectionMgr: IConnectionMgr): void {
    this.editForm.patchValue({
      id: connectionMgr.id,
      description: connectionMgr.description,
      uRL: connectionMgr.uRL,
      username: connectionMgr.username,
      password: connectionMgr.password,
      identityDomain: connectionMgr.identityDomain,
      connectionName: connectionMgr.connectionName,
      connectionName: connectionMgr.connectionName,
      connectionName: connectionMgr.connectionName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const connectionMgr = this.createFromForm();
    if (connectionMgr.id !== undefined) {
      this.subscribeToSaveResponse(this.connectionMgrService.update(connectionMgr));
    } else {
      this.subscribeToSaveResponse(this.connectionMgrService.create(connectionMgr));
    }
  }

  private createFromForm(): IConnectionMgr {
    return {
      ...new ConnectionMgr(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      uRL: this.editForm.get(['uRL'])!.value,
      username: this.editForm.get(['username'])!.value,
      password: this.editForm.get(['password'])!.value,
      identityDomain: this.editForm.get(['identityDomain'])!.value,
      connectionName: this.editForm.get(['connectionName'])!.value,
      connectionName: this.editForm.get(['connectionName'])!.value,
      connectionName: this.editForm.get(['connectionName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConnectionMgr>>): void {
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
