import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISaleContract } from 'app/shared/model/sale-contract.model';
import { getEntities } from './sale-contract.reducer';

export const SaleContract = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const saleContractList = useAppSelector(state => state.saleContract.entities);
  const loading = useAppSelector(state => state.saleContract.loading);
  const totalItems = useAppSelector(state => state.saleContract.totalItems);

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
      <h2 id="sale-contract-heading" data-cy="SaleContractHeading">
        Sale Contracts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/sale-contract/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Sale Contract
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {saleContractList && saleContractList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractId')}>
                  Contract Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('companyId')}>
                  Company Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountId')}>
                  Account Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contactSignedDate')}>
                  Contact Signed Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contactSignedTitle')}>
                  Contact Signed Title <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractEndDate')}>
                  Contract End Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractNumber')}>
                  Contract Number <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractNumberInput')}>
                  Contract Number Input <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractStageId')}>
                  Contract Stage Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractStartDate')}>
                  Contract Start Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ownerEmployeeId')}>
                  Owner Employee Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paymentMethodId')}>
                  Payment Method Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractName')}>
                  Contract Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractTypeId')}>
                  Contract Type Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currencyId')}>
                  Currency Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grandTotal')}>
                  Grand Total <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paymentTermId')}>
                  Payment Term Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quoteId')}>
                  Quote Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('currencyExchangeRateId')}>
                  Currency Exchange Rate Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractStageName')}>
                  Contract Stage Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paymentStatusId')}>
                  Payment Status Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('period')}>
                  Period <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('payment')}>
                  Payment <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {saleContractList.map((saleContract, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sale-contract/${saleContract.id}`} color="link" size="sm">
                      {saleContract.id}
                    </Button>
                  </td>
                  <td>{saleContract.contractId}</td>
                  <td>{saleContract.companyId}</td>
                  <td>{saleContract.accountId}</td>
                  <td>
                    {saleContract.contactSignedDate ? (
                      <TextFormat type="date" value={saleContract.contactSignedDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{saleContract.contactSignedTitle}</td>
                  <td>
                    {saleContract.contractEndDate ? (
                      <TextFormat type="date" value={saleContract.contractEndDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{saleContract.contractNumber}</td>
                  <td>{saleContract.contractNumberInput}</td>
                  <td>{saleContract.contractStageId}</td>
                  <td>
                    {saleContract.contractStartDate ? (
                      <TextFormat type="date" value={saleContract.contractStartDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{saleContract.ownerEmployeeId}</td>
                  <td>{saleContract.paymentMethodId}</td>
                  <td>{saleContract.contractName}</td>
                  <td>{saleContract.contractTypeId}</td>
                  <td>{saleContract.currencyId}</td>
                  <td>{saleContract.grandTotal}</td>
                  <td>{saleContract.paymentTermId}</td>
                  <td>{saleContract.quoteId}</td>
                  <td>{saleContract.currencyExchangeRateId}</td>
                  <td>{saleContract.contractStageName}</td>
                  <td>{saleContract.paymentStatusId}</td>
                  <td>{saleContract.period}</td>
                  <td>{saleContract.payment}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sale-contract/${saleContract.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sale-contract/${saleContract.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sale-contract/${saleContract.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Sale Contracts found</div>
        )}
      </div>
      {totalItems ? (
        <div className={saleContractList && saleContractList.length > 0 ? '' : 'd-none'}>
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

export default SaleContract;
