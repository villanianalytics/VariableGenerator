import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICubes, Cubes } from 'app/shared/model/cubes.model';
import { CubesService } from './cubes.service';
import { CubesComponent } from './cubes.component';
import { CubesDetailComponent } from './cubes-detail.component';
import { CubesUpdateComponent } from './cubes-update.component';

@Injectable({ providedIn: 'root' })
export class CubesResolve implements Resolve<ICubes> {
  constructor(private service: CubesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICubes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cubes: HttpResponse<Cubes>) => {
          if (cubes.body) {
            return of(cubes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cubes());
  }
}

export const cubesRoute: Routes = [
  {
    path: '',
    component: CubesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cubes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CubesDetailComponent,
    resolve: {
      cubes: CubesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cubes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CubesUpdateComponent,
    resolve: {
      cubes: CubesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cubes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CubesUpdateComponent,
    resolve: {
      cubes: CubesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Cubes',
    },
    canActivate: [UserRouteAccessService],
  },
];
