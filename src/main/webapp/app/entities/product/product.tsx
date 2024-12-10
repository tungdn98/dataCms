import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct, ProductImport } from 'app/shared/model/product.model';
import { getEntities } from './product.reducer';

import * as XLSX from 'xlsx';
import * as FileSaver from 'file-saver';
import { Dialog } from 'primereact/dialog';
import SaleOppoImport from 'app/entities/sale-opportunity/sale-oppo-import';
import ImportComponent from 'app/shared/util/common-import';

export const Product = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const productList = useAppSelector(state => state.product.entities);
  const loading = useAppSelector(state => state.product.loading);
  const totalItems = useAppSelector(state => state.product.totalItems);

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
      productId: '',
      productCode: '',
      productFamilyId: '',
      productPriceId: '',
      productName: '',
      productFamilyCode: '',
      productFamilyName: '',
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
      <h2 id="product-heading" data-cy="ProductHeading">
        Products
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
          <Link to="/product/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Product
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productId')}>
                  Product Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productCode')}>
                  Product Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productFamilyId')}>
                  Product Family Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productPriceId')}>
                  Product Price Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productName')}>
                  Product Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productFamilyCode')}>
                  Product Family Code <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('productFamilyName')}>
                  Product Family Name <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/product/${product.id}`} color="link" size="sm">
                      {product.id}
                    </Button>
                  </td>
                  <td>{product.productId}</td>
                  <td>{product.productCode}</td>
                  <td>{product.productFamilyId}</td>
                  <td>{product.productPriceId}</td>
                  <td>{product.productName}</td>
                  <td>{product.productFamilyCode}</td>
                  <td>{product.productFamilyName}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/product/${product.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/product/${product.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/product/${product.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Products found</div>
        )}
      </div>
      {totalItems ? (
        <div className={productList && productList.length > 0 ? '' : 'd-none'}>
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
        header="Import dữ liệu product"
        visible={visibleImportDialog}
        style={{ width: '70vw' }}
        onHide={() => setVisibleImportDialog(false)}
        breakpoints={{ '960px': '75vw', '641px': '100vw' }}
      >
        <ImportComponent model={ProductImport} endpoint="/api/products/batch" />
      </Dialog>
    </div>
  );
};

export default Product;
