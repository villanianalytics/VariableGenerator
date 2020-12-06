import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IApp } from 'app/shared/model/app.model';

type EntityResponseType = HttpResponse<IApp>;
type EntityArrayResponseType = HttpResponse<IApp[]>;

@Injectable({ providedIn: 'root' })
export class AppService {
  public resourceUrl = SERVER_API_URL + 'api/apps';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/apps';

  constructor(protected http: HttpClient) {}

  create(app: IApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(app);
    return this.http
      .post<IApp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(app: IApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(app);
    return this.http
      .put<IApp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApp[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(app: IApp): IApp {
    const copy: IApp = Object.assign({}, app, {
      effDt: app.effDt && app.effDt.isValid() ? app.effDt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.effDt = res.body.effDt ? moment(res.body.effDt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((app: IApp) => {
        app.effDt = app.effDt ? moment(app.effDt) : undefined;
      });
    }
    return res;
  }
}
