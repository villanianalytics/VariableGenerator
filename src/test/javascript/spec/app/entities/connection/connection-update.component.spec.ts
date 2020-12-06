import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ConnectionUpdateComponent } from 'app/entities/connection/connection-update.component';
import { ConnectionService } from 'app/entities/connection/connection.service';
import { Connection } from 'app/shared/model/connection.model';

describe('Component Tests', () => {
  describe('Connection Management Update Component', () => {
    let comp: ConnectionUpdateComponent;
    let fixture: ComponentFixture<ConnectionUpdateComponent>;
    let service: ConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ConnectionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Connection(123);
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
        const entity = new Connection();
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
