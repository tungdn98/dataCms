import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './activity.reducer';

export const ActivityDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const activityEntity = useAppSelector(state => state.activity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="activityDetailsHeading">Activity</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{activityEntity.id}</dd>
          <dt>
            <span id="activityId">Activity Id</span>
          </dt>
          <dd>{activityEntity.activityId}</dd>
          <dt>
            <span id="companyId">Company Id</span>
          </dt>
          <dd>{activityEntity.companyId}</dd>
          <dt>
            <span id="createDate">Create Date</span>
          </dt>
          <dd>
            {activityEntity.createDate ? <TextFormat value={activityEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="deadline">Deadline</span>
          </dt>
          <dd>
            {activityEntity.deadline ? <TextFormat value={activityEntity.deadline} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{activityEntity.name}</dd>
          <dt>
            <span id="state">State</span>
          </dt>
          <dd>{activityEntity.state}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{activityEntity.type}</dd>
          <dt>
            <span id="accountId">Account Id</span>
          </dt>
          <dd>{activityEntity.accountId}</dd>
          <dt>
            <span id="activityTypeId">Activity Type Id</span>
          </dt>
          <dd>{activityEntity.activityTypeId}</dd>
          <dt>
            <span id="objectTypeId">Object Type Id</span>
          </dt>
          <dd>{activityEntity.objectTypeId}</dd>
          <dt>
            <span id="priorityId">Priority Id</span>
          </dt>
          <dd>{activityEntity.priorityId}</dd>
          <dt>
            <span id="opportunityId">Opportunity Id</span>
          </dt>
          <dd>{activityEntity.opportunityId}</dd>
          <dt>
            <span id="orderId">Order Id</span>
          </dt>
          <dd>{activityEntity.orderId}</dd>
          <dt>
            <span id="contractId">Contract Id</span>
          </dt>
          <dd>{activityEntity.contractId}</dd>
          <dt>
            <span id="priorityName">Priority Name</span>
          </dt>
          <dd>{activityEntity.priorityName}</dd>
          <dt>
            <span id="responsibleId">Responsible Id</span>
          </dt>
          <dd>{activityEntity.responsibleId}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>{activityEntity.startDate ? <TextFormat value={activityEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="closedOn">Closed On</span>
          </dt>
          <dd>{activityEntity.closedOn ? <TextFormat value={activityEntity.closedOn} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="duration">Duration</span>
          </dt>
          <dd>{activityEntity.duration}</dd>
          <dt>
            <span id="durationUnitId">Duration Unit Id</span>
          </dt>
          <dd>{activityEntity.durationUnitId}</dd>
          <dt>
            <span id="conversion">Conversion</span>
          </dt>
          <dd>{activityEntity.conversion}</dd>
          <dt>
            <span id="textStr">Text Str</span>
          </dt>
          <dd>{activityEntity.textStr}</dd>
        </dl>
        <Button tag={Link} to="/activity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/activity/${activityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActivityDetail;
