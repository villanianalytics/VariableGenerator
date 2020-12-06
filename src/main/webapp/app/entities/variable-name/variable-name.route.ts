import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVariableName, VariableName } from 'app/shared/model/variable-name.model';
import { VariableNameService } from './variable-name.service';
import { VariableNameComponent } from './variable-name.component';
import { VariableNameDetailComponent } from './variable-name-detail.component';
import { VariableNameUpdateComponent } from './variable-name-update.component';

@Injectable({ providedIn: 'root' })
export class VariableNameResolve implements Resolve<IVariableName> {
  constructor(private service: VariableNameService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVariableName> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((variableName: HttpResponse<VariableName>) => {
          if (variableName.body) {
            return of(variableName.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new VariableName());
  }
}

export const variableNameRoute: Routes = [
  {
    path: '',
    component: VariableNameComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VariableNames',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VariableNameDetailComponent,
    resolve: {
      variableName: VariableNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VariableNames',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VariableNameUpdateComponent,
    resolve: {
      variableName: VariableNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VariableNames',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VariableNameUpdateComponent,
    resolve: {
      variableName: VariableNameResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'VariableNames',
    },
    canActivate: [UserRouteAccessService],
  },
];
