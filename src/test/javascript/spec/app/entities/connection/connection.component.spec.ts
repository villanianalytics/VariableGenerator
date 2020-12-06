import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ConnectionComponent } from 'app/entities/connection/connection.component';
import { ConnectionService } from 'app/entities/connection/connection.service';
import { Connection } from 'app/shared/model/connection.model';

describe('Component Tests', () => {
  describe('Connection Management Component', () => {
    let comp: ConnectionComponent;
    let fixture: ComponentFixture<ConnectionComponent>;
    let service: ConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ConnectionComponent],
      })
        .overrideTemplate(ConnectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Connection(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.connections && comp.connections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
