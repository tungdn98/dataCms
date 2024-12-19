import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISaleOpportunity } from 'app/shared/model/sale-opportunity.model';
import { getEntities } from './sale-opportunity.reducer';

import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';
import { Dialog } from 'primereact/dialog';
import SaleOppoImport from 'app/entities/sale-opportunity/sale-oppo-import';
import SearchComponent from 'app/shared/util/search-component';

export const SaleOpportunity = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const saleOpportunityList = useAppSelector(state => state.saleOpportunity.entities);
  const loading = useAppSelector(state => state.saleOpportunity.loading);
  const totalItems = useAppSelector(state => state.saleOpportunity.totalItems);

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

  const downloadUploadTemplate = () => {
    const excelData = [];
    excelData.push({
      STT: 1,
      opportunityId: '',
      opportunityCode: '',
      opportunityName: '',
      opportunityTypeName: '',
      startDate: '',
      closeDate: '',
      stageId: 0,
      stageReasonId: 0,
      employeeId: 0,
      leadId: 0,
      currencyCode: '',
      accountId: 0,
      productId: 0,
      salesPricePrd: 0,
      value: 0,
    });

    const ws = XLSX.utils.json_to_sheet(excelData);
    const wb = { Sheets: { TemplateUploadSaleOppo: ws }, SheetNames: ['TemplateUploadSaleOppo'] };
    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const data = new Blob([excelBuffer], { type: fileType });
    FileSaver.saveAs(data, 'TemplateUploadSaleOppo' + fileExtension);
  };
  // end handle excel

  // start search
  const searchFieldTemplate = [
    {
      name: 'opportunityCode',
      label: 'opportunityCode',
      searchKey: 'opportunityCode',
      placeholder: 'opportunityCode',
      searchType: 'equals', // Customize search type as needed
      className: 'float-start me-2 form-control-sm',
    },
    {
      name: 'opportunityName',
      label: 'opportunityName',
      searchKey: 'opportunityName',
      searchType: 'contains',
      placeholder: 'opportunityName',
      className: 'float-start me-2 form-control-sm',
    },
  ];

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
  const handleSearch = data => {
    getAllEntities(data);
  };
  // end start search

  const { match } = props;

  return (
    <div>
      <h2 id="sale-opportunity-heading" data-cy="SaleOpportunityHeading">
        Sale Opportunities

        <div className="d-flex justify-content-end" style={{ height: '50px' }}>
          <SearchComponent fields={searchFieldTemplate} onSubmit={handleSearch} />
          <Button className="me-2" color="info" onClick={() => downloadUploadTemplate()} disabled={loading}>
            <i className="pi pi-download" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Download Template</span>
          </Button>

          <Button className="me-2" color="info" onClick={() => setVisibleImportDialog(true)} disabled={loading}>
            <i className="pi pi-file-import" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Import Data</span>
        <div className="d-flex justify-content-end">
          <Link to="/sale-opportunity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Sale Opportunity
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {saleOpportunityList && saleOpportunityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('opportunityId')}>
                  Opportunity Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('opportunityCode')}>
                  Opportunity Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('opportunityName')}>
                  Opportunity Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('opportunityTypeName')}>
                  Opportunity Type Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  Start Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('closeDate')}>
                  Close Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stageId')}>
                  Stage Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stageReasonId')}>
                  Stage Reason Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('employeeId')}>
                  Employee Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('leadId')}>
                  Lead Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currencyCode')}>
                  Currency Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountId')}>
                  Account Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productId')}>
                  Product Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('salesPricePrd')}>
                  Sales Price Prd <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('value')}>
                  Value <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {saleOpportunityList.map((saleOpportunity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sale-opportunity/${saleOpportunity.id}`} color="link" size="sm">
                      {saleOpportunity.id}
                    </Button>
                  </td>
                  <td>{saleOpportunity.opportunityId}</td>
                  <td>{saleOpportunity.opportunityCode}</td>
                  <td>{saleOpportunity.opportunityName}</td>
                  <td>{saleOpportunity.opportunityTypeName}</td>
                  <td>
                    {saleOpportunity.startDate ? (
                      <TextFormat type="date" value={saleOpportunity.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {saleOpportunity.closeDate ? (
                      <TextFormat type="date" value={saleOpportunity.closeDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{saleOpportunity.stageId}</td>
                  <td>{saleOpportunity.stageReasonId}</td>
                  <td>{saleOpportunity.employeeId}</td>
                  <td>{saleOpportunity.leadId}</td>
                  <td>{saleOpportunity.currencyCode}</td>
                  <td>{saleOpportunity.accountId}</td>
                  <td>{saleOpportunity.productId}</td>
                  <td>{saleOpportunity.salesPricePrd}</td>
                  <td>{saleOpportunity.value}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/sale-opportunity/${saleOpportunity.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sale-opportunity/${saleOpportunity.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sale-opportunity/${saleOpportunity.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Sale Opportunities found</div>
        )}
      </div>
      {totalItems ? (
        <div className={saleOpportunityList && saleOpportunityList.length > 0 ? '' : 'd-none'}>
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
    </div>
  );
};

export default SaleOpportunity;
