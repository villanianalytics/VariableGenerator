import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConnection } from 'app/shared/model/connection.model';
import { ConnectionService } from './connection.service';
import { ConnectionDeleteDialogComponent } from './connection-delete-dialog.component';

@Component({
  selector: 'jhi-connection',
  templateUrl: './connection.component.html',
})
export class ConnectionComponent implements OnInit, OnDestroy {
  connections?: IConnection[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected connectionService: ConnectionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.connectionService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IConnection[]>) => (this.connections = res.body || []));
      return;
    }

    this.connectionService.query().subscribe((res: HttpResponse<IConnection[]>) => (this.connections = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConnections();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConnection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConnections(): void {
    this.eventSubscriber = this.eventManager.subscribe('connectionListModification', () => this.loadAll());
  }

  delete(connection: IConnection): void {
    const modalRef = this.modalService.open(ConnectionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.connection = connection;
  }
}
