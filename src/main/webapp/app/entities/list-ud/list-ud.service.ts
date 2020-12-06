import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IListUD } from 'app/shared/model/list-ud.model';

type EntityResponseType = HttpResponse<IListUD>;
type EntityArrayResponseType = HttpResponse<IListUD[]>;

@Injectable({ providedIn: 'root' })
export class ListUDService {
  public resourceUrl = SERVER_API_URL + 'api/list-uds';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/list-uds';

  constructor(protected http: HttpClient) {}

  create(listUD: IListUD): Observable<EntityResponseType> {
    return this.http.post<IListUD>(this.resourceUrl, listUD, { observe: 'response' });
  }

  update(listUD: IListUD): Observable<EntityResponseType> {
    return this.http.put<IListUD>(this.resourceUrl, listUD, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IListUD>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IListUD[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IListUD[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
