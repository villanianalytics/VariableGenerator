import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ConnectionMgrUpdateComponent } from 'app/entities/connection-mgr/connection-mgr-update.component';
import { ConnectionMgrService } from 'app/entities/connection-mgr/connection-mgr.service';
import { ConnectionMgr } from 'app/shared/model/connection-mgr.model';

describe('Component Tests', () => {
  describe('ConnectionMgr Management Update Component', () => {
    let comp: ConnectionMgrUpdateComponent;
    let fixture: ComponentFixture<ConnectionMgrUpdateComponent>;
    let service: ConnectionMgrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ConnectionMgrUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConnectionMgrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectionMgrUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectionMgrService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConnectionMgr(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConnectionMgr();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
