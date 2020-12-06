import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ListUDUpdateComponent } from 'app/entities/list-ud/list-ud-update.component';
import { ListUDService } from 'app/entities/list-ud/list-ud.service';
import { ListUD } from 'app/shared/model/list-ud.model';

describe('Component Tests', () => {
  describe('ListUD Management Update Component', () => {
    let comp: ListUDUpdateComponent;
    let fixture: ComponentFixture<ListUDUpdateComponent>;
    let service: ListUDService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ListUDUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ListUDUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListUDUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ListUDService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ListUD(123);
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
        const entity = new ListUD();
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
