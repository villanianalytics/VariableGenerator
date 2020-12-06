import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { VariableNameDetailComponent } from 'app/entities/variable-name/variable-name-detail.component';
import { VariableName } from 'app/shared/model/variable-name.model';

describe('Component Tests', () => {
  describe('VariableName Management Detail Component', () => {
    let comp: VariableNameDetailComponent;
    let fixture: ComponentFixture<VariableNameDetailComponent>;
    const route = ({ data: of({ variableName: new VariableName(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [VariableNameDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VariableNameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VariableNameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load variableName on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.variableName).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
