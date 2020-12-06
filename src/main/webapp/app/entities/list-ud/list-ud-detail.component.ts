import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListUD } from 'app/shared/model/list-ud.model';

@Component({
  selector: 'jhi-list-ud-detail',
  templateUrl: './list-ud-detail.component.html',
})
export class ListUDDetailComponent implements OnInit {
  listUD: IListUD | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listUD }) => (this.listUD = listUD));
  }

  previousState(): void {
    window.history.back();
  }
}
