import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { CubesDetailComponent } from 'app/entities/cubes/cubes-detail.component';
import { Cubes } from 'app/shared/model/cubes.model';

describe('Component Tests', () => {
  describe('Cubes Management Detail Component', () => {
    let comp: CubesDetailComponent;
    let fixture: ComponentFixture<CubesDetailComponent>;
    const route = ({ data: of({ cubes: new Cubes(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [CubesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CubesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CubesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cubes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cubes).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
