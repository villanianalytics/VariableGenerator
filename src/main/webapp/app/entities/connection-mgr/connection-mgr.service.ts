import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IConnectionMgr } from 'app/shared/model/connection-mgr.model';

type EntityResponseType = HttpResponse<IConnectionMgr>;
type EntityArrayResponseType = HttpResponse<IConnectionMgr[]>;

@Injectable({ providedIn: 'root' })
export class ConnectionMgrService {
  public resourceUrl = SERVER_API_URL + 'api/connection-mgrs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/connection-mgrs';

  constructor(protected http: HttpClient) {}

  create(connectionMgr: IConnectionMgr): Observable<EntityResponseType> {
    return this.http.post<IConnectionMgr>(this.resourceUrl, connectionMgr, { observe: 'response' });
  }

  update(connectionMgr: IConnectionMgr): Observable<EntityResponseType> {
    return this.http.put<IConnectionMgr>(this.resourceUrl, connectionMgr, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConnectionMgr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnectionMgr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnectionMgr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
