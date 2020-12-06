import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ConnectionDetailComponent } from 'app/entities/connection/connection-detail.component';
import { Connection } from 'app/shared/model/connection.model';

describe('Component Tests', () => {
  describe('Connection Management Detail Component', () => {
    let comp: ConnectionDetailComponent;
    let fixture: ComponentFixture<ConnectionDetailComponent>;
    const route = ({ data: of({ connection: new Connection(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ConnectionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConnectionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConnectionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load connection on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.connection).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
