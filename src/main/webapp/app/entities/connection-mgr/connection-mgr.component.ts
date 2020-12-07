import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConnectionMgr } from 'app/shared/model/connection-mgr.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ConnectionMgrService } from './connection-mgr.service';
import { ConnectionMgrDeleteDialogComponent } from './connection-mgr-delete-dialog.component';

@Component({
  selector: 'jhi-connection-mgr',
  templateUrl: './connection-mgr.component.html',
})
export class ConnectionMgrComponent implements OnInit, OnDestroy {
  connectionMgrs: IConnectionMgr[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected connectionMgrService: ConnectionMgrService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.connectionMgrs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.connectionMgrService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe((res: HttpResponse<IConnectionMgr[]>) => this.paginateConnectionMgrs(res.body, res.headers));
      return;
    }

    this.connectionMgrService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IConnectionMgr[]>) => this.paginateConnectionMgrs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.connectionMgrs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.connectionMgrs = [];
    this.links = {
      last: 0,
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConnectionMgrs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConnectionMgr): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConnectionMgrs(): void {
    this.eventSubscriber = this.eventManager.subscribe('connectionMgrListModification', () => this.reset());
  }

  delete(connectionMgr: IConnectionMgr): void {
    const modalRef = this.modalService.open(ConnectionMgrDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.connectionMgr = connectionMgr;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateConnectionMgrs(data: IConnectionMgr[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.connectionMgrs.push(data[i]);
      }
    }
  }
}
