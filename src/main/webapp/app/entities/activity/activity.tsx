import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntities } from './activity.reducer';

export const Activity = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const activityList = useAppSelector(state => state.activity.entities);
  const loading = useAppSelector(state => state.activity.loading);
  const totalItems = useAppSelector(state => state.activity.totalItems);

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
      <h2 id="activity-heading" data-cy="ActivityHeading">
        Activities
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/activity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Activity
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {activityList && activityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activityId')}>
                  Activity Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('companyId')}>
                  Company Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createDate')}>
                  Create Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('deadline')}>
                  Deadline <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('state')}>
                  State <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountId')}>
                  Account Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('activityTypeId')}>
                  Activity Type Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('objectTypeId')}>
                  Object Type Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('priorityId')}>
                  Priority Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('opportunityId')}>
                  Opportunity Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('orderId')}>
                  Order Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('contractId')}>
                  Contract Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('priorityName')}>
                  Priority Name <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('responsibleId')}>
                  Responsible Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  Start Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('closedOn')}>
                  Closed On <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('duration')}>
                  Duration <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('durationUnitId')}>
                  Duration Unit Id <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('conversion')}>
                  Conversion <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('textStr')}>
                  Text Str <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {activityList.map((activity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/activity/${activity.id}`} color="link" size="sm">
                      {activity.id}
                    </Button>
                  </td>
                  <td>{activity.activityId}</td>
                  <td>{activity.companyId}</td>
                  <td>{activity.createDate ? <TextFormat type="date" value={activity.createDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{activity.deadline ? <TextFormat type="date" value={activity.deadline} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{activity.name}</td>
                  <td>{activity.state}</td>
                  <td>{activity.type}</td>
                  <td>{activity.accountId}</td>
                  <td>{activity.activityTypeId}</td>
                  <td>{activity.objectTypeId}</td>
                  <td>{activity.priorityId}</td>
                  <td>{activity.opportunityId}</td>
                  <td>{activity.orderId}</td>
                  <td>{activity.contractId}</td>
                  <td>{activity.priorityName}</td>
                  <td>{activity.responsibleId}</td>
                  <td>{activity.startDate ? <TextFormat type="date" value={activity.startDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{activity.closedOn ? <TextFormat type="date" value={activity.closedOn} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{activity.duration}</td>
                  <td>{activity.durationUnitId}</td>
                  <td>{activity.conversion}</td>
                  <td>{activity.textStr}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/activity/${activity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/activity/${activity.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/activity/${activity.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
          !loading && <div className="alert alert-warning">No Activities found</div>
        )}
      </div>
      {totalItems ? (
        <div className={activityList && activityList.length > 0 ? '' : 'd-none'}>
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

export default Activity;
