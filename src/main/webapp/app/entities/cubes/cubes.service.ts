import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICubes } from 'app/shared/model/cubes.model';

type EntityResponseType = HttpResponse<ICubes>;
type EntityArrayResponseType = HttpResponse<ICubes[]>;

@Injectable({ providedIn: 'root' })
export class CubesService {
  public resourceUrl = SERVER_API_URL + 'api/cubes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/cubes';

  constructor(protected http: HttpClient) {}

  create(cubes: ICubes): Observable<EntityResponseType> {
    return this.http.post<ICubes>(this.resourceUrl, cubes, { observe: 'response' });
  }

  update(cubes: ICubes): Observable<EntityResponseType> {
    return this.http.put<ICubes>(this.resourceUrl, cubes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICubes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICubes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICubes[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
