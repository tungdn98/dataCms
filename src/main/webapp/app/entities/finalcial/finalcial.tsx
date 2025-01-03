import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { FinalcialImport, IFinalcial } from 'app/shared/model/finalcial.model';
import { getEntities } from './finalcial.reducer';
import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';
import { Dialog } from 'primereact/dialog';
import ImportComponent from 'app/shared/util/common-import';
import { ProductImport } from 'app/shared/model/product.model';

export const Finalcial = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const finalcialList = useAppSelector(state => state.finalcial.entities);
  const loading = useAppSelector(state => state.finalcial.loading);
  const totalItems = useAppSelector(state => state.finalcial.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
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

  const downloadUploadTemplate = () => {
    const excelData = [];
    excelData.push({
      STT: 1,
      code: '',
      customerName: '',
      customerShortName: '',
      customerType: '',
    });

    const ws = XLSX.utils.json_to_sheet(excelData);
    const wb = { Sheets: { TemplateUpload: ws }, SheetNames: ['TemplateUpload'] };
    const excelBuffer = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    const data = new Blob([excelBuffer], { type: fileType });
    FileSaver.saveAs(data, 'TemplateUpload' + fileExtension);
  };
  // end handle excel

  const { match } = props;

  return (
    <div>
      <h2 id="finalcial-heading" data-cy="FinalcialHeading">
        Finalcials
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={() => downloadUploadTemplate()} disabled={loading}>
            <i className="pi pi-download" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Download Template</span>
          </Button>
          <Button className="me-2" color="info" onClick={() => setVisibleImportDialog(true)} disabled={loading}>
            <i className="pi pi-file-import" style={{ fontSize: '1rem' }}></i>
            <span className="ms-1">Import Data</span>
          </Button>

          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/finalcial/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Finalcial
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {finalcialList && finalcialList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('customerName')}>
                  Customer Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('customerShortName')}>
                  Customer Short Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('customerType')}>
                  Customer Type <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {finalcialList.map((finalcial, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/finalcial/${finalcial.id}`} color="link" size="sm">
                      {finalcial.id}
                    </Button>
                  </td>
                  <td>{finalcial.code}</td>
                  <td>{finalcial.customerName}</td>
                  <td>{finalcial.customerShortName}</td>
                  <td>{finalcial.customerType}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/finalcial/${finalcial.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/finalcial/${finalcial.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/finalcial/${finalcial.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Finalcials found</div>
        )}
      </div>
      {totalItems ? (
        <div className={finalcialList && finalcialList.length > 0 ? '' : 'd-none'}>
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
        header="Import dữ liệu finalcial"
        visible={visibleImportDialog}
        style={{ width: '70vw' }}
        onHide={() => setVisibleImportDialog(false)}
        breakpoints={{ '960px': '75vw', '641px': '100vw' }}
      >
        <ImportComponent model={FinalcialImport} endpoint="/api/finalcials/batch" />
      </Dialog>
    </div>
  );
};

export default Finalcial;
