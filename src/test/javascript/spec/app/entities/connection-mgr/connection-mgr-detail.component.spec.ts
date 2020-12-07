import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ConnectionMgrDetailComponent } from 'app/entities/connection-mgr/connection-mgr-detail.component';
import { ConnectionMgr } from 'app/shared/model/connection-mgr.model';

describe('Component Tests', () => {
  describe('ConnectionMgr Management Detail Component', () => {
    let comp: ConnectionMgrDetailComponent;
    let fixture: ComponentFixture<ConnectionMgrDetailComponent>;
    const route = ({ data: of({ connectionMgr: new ConnectionMgr(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ConnectionMgrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConnectionMgrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConnectionMgrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load connectionMgr on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.connectionMgr).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
