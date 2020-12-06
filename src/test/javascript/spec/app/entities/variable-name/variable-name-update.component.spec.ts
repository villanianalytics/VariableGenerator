import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { VariableNameUpdateComponent } from 'app/entities/variable-name/variable-name-update.component';
import { VariableNameService } from 'app/entities/variable-name/variable-name.service';
import { VariableName } from 'app/shared/model/variable-name.model';

describe('Component Tests', () => {
  describe('VariableName Management Update Component', () => {
    let comp: VariableNameUpdateComponent;
    let fixture: ComponentFixture<VariableNameUpdateComponent>;
    let service: VariableNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [VariableNameUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VariableNameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VariableNameUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VariableNameService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VariableName(123);
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
        const entity = new VariableName();
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
