import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISaleOrder } from 'app/shared/model/sale-order.model';
import { getEntities } from './sale-order.reducer';

import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';
import { Dialog } from 'primereact/dialog';
import SaleOrderImport from 'app/entities/sale-order/sale-order-import';
import SearchComponent from 'app/shared/util/search-component';

export const SaleOrder = (props: RouteComponentProps<{ url: string }>) => {
  const searchFieldTemplate = [
    {
      name: 'orderId',
      label: 'orderId',
      searchKey: 'orderId',
      placeholder: 'orderId',
      searchType: 'equals', // Customize search type as needed
      className: 'float-start me-2 form-control-sm',
    },
    {
      name: 'ownerEmployeeId',
      label: 'ownerEmployeeId',
      searchKey: 'ownerEmployeeId',
      searchType: 'contains',
      placeholder: 'ownerEmployeeId',
      className: 'float-start me-2 form-control-sm',
    },
    {
      name: 'contractId',
      label: 'contractId',
      searchKey: 'contractId',
      searchType: 'contains',
      placeholder: 'contractId',
      className: 'float-start me-2 form-control-sm',
    },
    {
      name: 'orderStageName',
      label: 'orderStageName',
      searchKey: 'orderStageName',
      searchType: 'contains',
      placeholder: 'orderStageName',
      className: 'float-start me-2 form-control-sm',
    },
  ];

  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const saleOrderList = useAppSelector(state => state.saleOrder.entities);
  const loading = useAppSelector(state => state.saleOrder.loading);
  const totalItems = useAppSelector(state => state.saleOrder.totalItems);

  const getAllEntities = (searchCriterials: any) => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
        searchCriterials,
      })
    );
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  const sortEntities = () => {
    getAllEntities(null);
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  // handle excel
  const [visibleImportDialog, setVisibleImportDialog] = useState(false);

  const fileType = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
  const fileExtension = '.xlsx';

  const downloadUploadSaleOrderTemplate = () => {
    const excelData = [];
    excelData.push({
      STT: 1,
      orderId: '',
      contractId: '',
      ownerEmployeeId: '',
      productId: '',
      totalValue: '',
      orderStageId: '',
      orderStageName: '',
    });

    const ws = XLSX.utils.json_to_sheet(excelData);
    const wb = { Sheets: { TemplateUploadSaleOrder: ws }, SheetNames: ['TemplateUploadSaleOrder'] };
    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const data = new Blob([excelBuffer], { type: fileType });
    FileSaver.saveAs(data, 'TemplateUploadSaleOrder' + fileExtension);
  };
  // end handle excel

  //handle search
  const handleSearch = data => {
    getAllEntities(data);
  };
  // end handle search

  const { match } = props;

  return (
    <div>
      <h2 id="sale-order-heading" data-cy="SaleOrderHeading">
        Sale Orders
        <div className="d-flex justify-content-end" style={{ height: '50px' }}>
          <SearchComponent fields={searchFieldTemplate} onSubmit={handleSearch} />

          <Button className="me-2" color="info" onClick={() => downloadUploadSaleOrderTemplate()} disabled={loading}>
            <i className="pi pi-download" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Download Template</span>
          </Button>

          <Button className="me-2" color="info" onClick={() => setVisibleImportDialog(true)} disabled={loading}>
            <i className="pi pi-file-import" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Import Data</span>
          </Button>

          <Link to="/sale-order/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Sale Order
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {saleOrderList && saleOrderList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orderId')}>
                  Order Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractId')}>
                  Contract Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownerEmployeeId')}>
                  Owner Employee Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productId')}>
                  Product Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalValue')}>
                  Total Value <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orderStageId')}>
                  Order Stage Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orderStageName')}>
                  Order Stage Name <FontAwesomeIcon icon="sort" />
                </th>
                {/*<th className="hand" onClick={sort('createdDate')}>*/}
                {/*  Created Date <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                {/*<th className="hand" onClick={sort('createdBy')}>*/}
                {/*  Created By <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                {/*<th className="hand" onClick={sort('lastModifiedDate')}>*/}
                {/*  Last Modified Date <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                {/*<th className="hand" onClick={sort('lastModifiedBy')}>*/}
                {/*  Last Modified By <FontAwesomeIcon icon="sort" />*/}
                {/*</th>*/}
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {saleOrderList.map((saleOrder, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sale-order/${saleOrder.id}`} color="link" size="sm">
                      {saleOrder.id}
                    </Button>
                  </td>
                  <td>{saleOrder.orderId}</td>
                  <td>{saleOrder.contractId}</td>
                  <td>{saleOrder.ownerEmployeeId}</td>
                  <td>{saleOrder.productId}</td>
                  <td>{saleOrder.totalValue}</td>
                  <td>{saleOrder.orderStageId}</td>
                  <td>{saleOrder.orderStageName}</td>
                  {/*<td>*/}
                  {/*  {saleOrder.createdDate ? <TextFormat type="date" value={saleOrder.createdDate} format={APP_DATE_FORMAT} /> : null}*/}
                  {/*</td>*/}
                  {/*<td>{saleOrder.createdBy}</td>*/}
                  {/*<td>*/}
                  {/*  {saleOrder.lastModifiedDate ? (*/}
                  {/*    <TextFormat type="date" value={saleOrder.lastModifiedDate} format={APP_DATE_FORMAT} />*/}
                  {/*  ) : null}*/}
                  {/*</td>*/}
                  {/*<td>{saleOrder.lastModifiedBy}</td>*/}
                  <td className="text-center">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sale-order/${saleOrder.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sale-order/${saleOrder.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sale-order/${saleOrder.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Sale Orders found</div>
        )}
      </div>
      {totalItems ? (
        <div className={saleOrderList && saleOrderList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}

      <Dialog
        header="Import dữ liệu sale order"
        visible={visibleImportDialog}
        style={{ width: '70vw' }}
        onHide={() => setVisibleImportDialog(false)}
        breakpoints={{ '960px': '75vw', '641px': '100vw' }}
      >
        <SaleOrderImport />
      </Dialog>
    </div>
  );
};

export default SaleOrder;
