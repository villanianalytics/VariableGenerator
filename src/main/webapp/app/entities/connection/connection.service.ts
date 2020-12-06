import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IConnection } from 'app/shared/model/connection.model';

type EntityResponseType = HttpResponse<IConnection>;
type EntityArrayResponseType = HttpResponse<IConnection[]>;

@Injectable({ providedIn: 'root' })
export class ConnectionService {
  public resourceUrl = SERVER_API_URL + 'api/connections';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/connections';

  constructor(protected http: HttpClient) {}

  create(connection: IConnection): Observable<EntityResponseType> {
    return this.http.post<IConnection>(this.resourceUrl, connection, { observe: 'response' });
  }

  update(connection: IConnection): Observable<EntityResponseType> {
    return this.http.put<IConnection>(this.resourceUrl, connection, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConnection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnection[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
