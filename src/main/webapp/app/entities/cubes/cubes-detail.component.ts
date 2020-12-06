import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICubes } from 'app/shared/model/cubes.model';

@Component({
  selector: 'jhi-cubes-detail',
  templateUrl: './cubes-detail.component.html',
})
export class CubesDetailComponent implements OnInit {
  cubes: ICubes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cubes }) => (this.cubes = cubes));
  }

  previousState(): void {
    window.history.back();
  }
}
