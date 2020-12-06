import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IListUD, ListUD } from 'app/shared/model/list-ud.model';
import { ListUDService } from './list-ud.service';
import { ListUDComponent } from './list-ud.component';
import { ListUDDetailComponent } from './list-ud-detail.component';
import { ListUDUpdateComponent } from './list-ud-update.component';

@Injectable({ providedIn: 'root' })
export class ListUDResolve implements Resolve<IListUD> {
  constructor(private service: ListUDService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IListUD> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((listUD: HttpResponse<ListUD>) => {
          if (listUD.body) {
            return of(listUD.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ListUD());
  }
}

export const listUDRoute: Routes = [
  {
    path: '',
    component: ListUDComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ListUDS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ListUDDetailComponent,
    resolve: {
      listUD: ListUDResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ListUDS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ListUDUpdateComponent,
    resolve: {
      listUD: ListUDResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ListUDS',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ListUDUpdateComponent,
    resolve: {
      listUD: ListUDResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ListUDS',
    },
    canActivate: [UserRouteAccessService],
  },
];
