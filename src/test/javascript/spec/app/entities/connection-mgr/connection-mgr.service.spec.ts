import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ConnectionMgrService } from 'app/entities/connection-mgr/connection-mgr.service';
import { IConnectionMgr, ConnectionMgr } from 'app/shared/model/connection-mgr.model';

describe('Service Tests', () => {
  describe('ConnectionMgr Service', () => {
    let injector: TestBed;
    let service: ConnectionMgrService;
    let httpMock: HttpTestingController;
    let elemDefault: IConnectionMgr;
    let expectedResult: IConnectionMgr | IConnectionMgr[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConnectionMgrService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ConnectionMgr(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ConnectionMgr', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ConnectionMgr()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ConnectionMgr', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            uRL: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
            identityDomain: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ConnectionMgr', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            uRL: 'BBBBBB',
            username: 'BBBBBB',
            password: 'BBBBBB',
            identityDomain: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ConnectionMgr', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
