import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICompany } from 'app/shared/model/company.model';
import { getEntities } from './company.reducer';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';
import { Dialog } from 'primereact/dialog';
import CompanyImport from 'app/entities/company/company-import';
import SearchComponent from 'app/shared/util/search-component';

export const Company = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  // field configuration search:
  const searchFieldTemplate = [
    {
      name: 'companyCode',
      label: 'companyCode',
      searchKey: 'companyCode',
      placeholder: 'company Code',
      searchType: 'equals', // Customize search type as needed
      className: 'float-start me-2 form-control-sm',
    },
    {
      name: 'companyName',
      label: 'companyName',
      searchKey: 'companyName',
      searchType: 'contains',
      placeholder: 'company Name',
      className: 'float-start me-2 form-control-sm',
    },
    {
      name: 'phoneNumber',
      label: 'phoneNumber',
      searchKey: 'phoneNumber',
      searchType: 'contains',
      placeholder: 'phone',
      className: 'float-start me-2 form-control-sm',
    },
  ];

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const companyList = useAppSelector(state => state.company.entities);
  const loading = useAppSelector(state => state.company.loading);
  const totalItems = useAppSelector(state => state.company.totalItems);

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

  const downloadUploadCompanyTemplate = () => {
    const excelData = [];
    excelData.push({
      STT: 1,
      companyCode: '',
      companyName: '',
      description: '',
      location: '',
      phoneNumber: '',
    });

    const ws = XLSX.utils.json_to_sheet(excelData);
    const wb = { Sheets: { TemplateUploadCompany: ws }, SheetNames: ['TemplateUploadCompany'] };
    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const data = new Blob([excelBuffer], { type: fileType });
    FileSaver.saveAs(data, 'TemplateUploadCompany' + fileExtension);
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
      <h2 id="company-heading" data-cy="CompanyHeading">
        Companies
        <div className="d-flex justify-content-end" style={{ height: '50px' }}>
          <SearchComponent fields={searchFieldTemplate} onSubmit={handleSearch} />

          <Button className="me-2" color="info" onClick={() => downloadUploadCompanyTemplate()} disabled={loading}>
            <i className="pi pi-download" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Download Template</span>
          </Button>

          <Button className="me-2" color="info" onClick={() => setVisibleImportDialog(true)} disabled={loading}>
            <i className="pi pi-file-import" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Import Data</span>
          </Button>
          <Link to="/company/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Company
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {companyList && companyList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('companyCode')}>
                  Company Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('companyName')}>
                  Company Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  Description <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('location')}>
                  Location <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phoneNumber')}>
                  Phone Number <FontAwesomeIcon icon="sort" />
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
                <th className="text-center">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {companyList.map((company, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/company/${company.id}`} color="link" size="sm">
                      {company.id}
                    </Button>
                  </td>
                  <td>{company.companyCode}</td>
                  <td>{company.companyName}</td>
                  <td>{company.description}</td>
                  <td>{company.location}</td>
                  <td>{company.phoneNumber}</td>
                  {/*<td>{company.createdDate ? <TextFormat type="date" value={company.createdDate} format={APP_DATE_FORMAT} /> : null}</td>*/}
                  {/*<td>{company.createdBy}</td>*/}
                  {/*<td>*/}
                  {/*  {company.lastModifiedDate ? <TextFormat type="date" value={company.lastModifiedDate} format={APP_DATE_FORMAT} /> : null}*/}
                  {/*</td>*/}
                  {/*<td>{company.lastModifiedBy}</td>*/}
                  <td className="text-center">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/company/${company.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/company/${company.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/company/${company.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Companies found</div>
        )}
      </div>
      {totalItems ? (
        <div className={companyList && companyList.length > 0 ? '' : 'd-none'}>
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
        header="Import dữ liệu Companys"
        visible={visibleImportDialog}
        style={{ width: '70vw' }}
        onHide={() => setVisibleImportDialog(false)}
        breakpoints={{ '960px': '75vw', '641px': '100vw' }}
      >
        <CompanyImport />
      </Dialog>
    </div>
  );
};

export default Company;
