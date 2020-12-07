import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConnectionMgr } from 'app/shared/model/connection-mgr.model';

@Component({
  selector: 'jhi-connection-mgr-detail',
  templateUrl: './connection-mgr-detail.component.html',
})
export class ConnectionMgrDetailComponent implements OnInit {
  connectionMgr: IConnectionMgr | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ connectionMgr }) => (this.connectionMgr = connectionMgr));
  }

  previousState(): void {
    window.history.back();
  }
}
