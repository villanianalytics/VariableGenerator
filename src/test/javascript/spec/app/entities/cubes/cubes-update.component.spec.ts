import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { CubesUpdateComponent } from 'app/entities/cubes/cubes-update.component';
import { CubesService } from 'app/entities/cubes/cubes.service';
import { Cubes } from 'app/shared/model/cubes.model';

describe('Component Tests', () => {
  describe('Cubes Management Update Component', () => {
    let comp: CubesUpdateComponent;
    let fixture: ComponentFixture<CubesUpdateComponent>;
    let service: CubesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [CubesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CubesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CubesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CubesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cubes(123);
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
        const entity = new Cubes();
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
