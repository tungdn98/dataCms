import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity-type.reducer';

export const ActivityTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const activityTypeEntity = useAppSelector(state => state.activityType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityTypeDetailsHeading">ActivityType</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activityTypeEntity.id}</dd>
          <dt>
            <span id="activityTypeId">Activity Type Id</span>
          </dt>
          <dd>{activityTypeEntity.activityTypeId}</dd>
          <dt>
            <span id="activityType">Activity Type</span>
          </dt>
          <dd>{activityTypeEntity.activityType}</dd>
          <dt>
            <span id="textStr">Text Str</span>
          </dt>
          <dd>{activityTypeEntity.textStr}</dd>
        </dl>
        <Button tag={Link} to="/activity-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity-type/${activityTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityTypeDetail;
