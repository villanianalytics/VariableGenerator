import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IVariableName } from 'app/shared/model/variable-name.model';

type EntityResponseType = HttpResponse<IVariableName>;
type EntityArrayResponseType = HttpResponse<IVariableName[]>;

@Injectable({ providedIn: 'root' })
export class VariableNameService {
  public resourceUrl = SERVER_API_URL + 'api/variable-names';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/variable-names';

  constructor(protected http: HttpClient) {}

  create(variableName: IVariableName): Observable<EntityResponseType> {
    return this.http.post<IVariableName>(this.resourceUrl, variableName, { observe: 'response' });
  }

  update(variableName: IVariableName): Observable<EntityResponseType> {
    return this.http.put<IVariableName>(this.resourceUrl, variableName, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVariableName>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVariableName[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVariableName[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
