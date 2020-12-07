import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConnectionMgr, ConnectionMgr } from 'app/shared/model/connection-mgr.model';
import { ConnectionMgrService } from './connection-mgr.service';
import { ConnectionMgrComponent } from './connection-mgr.component';
import { ConnectionMgrDetailComponent } from './connection-mgr-detail.component';
import { ConnectionMgrUpdateComponent } from './connection-mgr-update.component';

@Injectable({ providedIn: 'root' })
export class ConnectionMgrResolve implements Resolve<IConnectionMgr> {
  constructor(private service: ConnectionMgrService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConnectionMgr> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((connectionMgr: HttpResponse<ConnectionMgr>) => {
          if (connectionMgr.body) {
            return of(connectionMgr.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConnectionMgr());
  }
}

export const connectionMgrRoute: Routes = [
  {
    path: '',
    component: ConnectionMgrComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConnectionMgrs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConnectionMgrDetailComponent,
    resolve: {
      connectionMgr: ConnectionMgrResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConnectionMgrs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConnectionMgrUpdateComponent,
    resolve: {
      connectionMgr: ConnectionMgrResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConnectionMgrs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConnectionMgrUpdateComponent,
    resolve: {
      connectionMgr: ConnectionMgrResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConnectionMgrs',
    },
    canActivate: [UserRouteAccessService],
  },
];
