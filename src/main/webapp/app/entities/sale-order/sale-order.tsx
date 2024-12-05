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

export const SaleOrder = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const saleOrderList = useAppSelector(state => state.saleOrder.entities);
  const loading = useAppSelector(state => state.saleOrder.loading);
  const totalItems = useAppSelector(state => state.saleOrder.totalItems);

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

  const { match } = props;

  return (
    <div>
      <h2 id="sale-order-heading" data-cy="SaleOrderHeading">
        Sale Orders
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
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
                <th className="hand" onClick={sort('createdDate')}>
                  Created Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  Created By <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastModifiedDate')}>
                  Last Modified Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lastModifiedBy')}>
                  Last Modified By <FontAwesomeIcon icon="sort" />
                </th>
                <th />
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
                  <td>
                    {saleOrder.createdDate ? <TextFormat type="date" value={saleOrder.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{saleOrder.createdBy}</td>
                  <td>
                    {saleOrder.lastModifiedDate ? (
                      <TextFormat type="date" value={saleOrder.lastModifiedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{saleOrder.lastModifiedBy}</td>
                  <td className="text-end">
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
    </div>
  );
};

export default SaleOrder;
