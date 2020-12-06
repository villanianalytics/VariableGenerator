import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConnection, Connection } from 'app/shared/model/connection.model';
import { ConnectionService } from './connection.service';
import { ConnectionComponent } from './connection.component';
import { ConnectionDetailComponent } from './connection-detail.component';
import { ConnectionUpdateComponent } from './connection-update.component';

@Injectable({ providedIn: 'root' })
export class ConnectionResolve implements Resolve<IConnection> {
  constructor(private service: ConnectionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConnection> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((connection: HttpResponse<Connection>) => {
          if (connection.body) {
            return of(connection.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Connection());
  }
}

export const connectionRoute: Routes = [
  {
    path: '',
    component: ConnectionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Connections',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConnectionDetailComponent,
    resolve: {
      connection: ConnectionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Connections',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConnectionUpdateComponent,
    resolve: {
      connection: ConnectionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Connections',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConnectionUpdateComponent,
    resolve: {
      connection: ConnectionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Connections',
    },
    canActivate: [UserRouteAccessService],
  },
];
