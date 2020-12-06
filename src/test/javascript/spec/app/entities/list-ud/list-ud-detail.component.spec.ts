import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VariableGeneratorTestModule } from '../../../test.module';
import { ListUDDetailComponent } from 'app/entities/list-ud/list-ud-detail.component';
import { ListUD } from 'app/shared/model/list-ud.model';

describe('Component Tests', () => {
  describe('ListUD Management Detail Component', () => {
    let comp: ListUDDetailComponent;
    let fixture: ComponentFixture<ListUDDetailComponent>;
    const route = ({ data: of({ listUD: new ListUD(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VariableGeneratorTestModule],
        declarations: [ListUDDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ListUDDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListUDDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load listUD on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.listUD).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
