import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity-object.reducer';

export const ActivityObjectDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const activityObjectEntity = useAppSelector(state => state.activityObject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityObjectDetailsHeading">ActivityObject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activityObjectEntity.id}</dd>
          <dt>
            <span id="unitCode">Unit Code</span>
          </dt>
          <dd>{activityObjectEntity.unitCode}</dd>
          <dt>
            <span id="unitName">Unit Name</span>
          </dt>
          <dd>{activityObjectEntity.unitName}</dd>
        </dl>
        <Button tag={Link} to="/activity-object" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity-object/${activityObjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityObjectDetail;
