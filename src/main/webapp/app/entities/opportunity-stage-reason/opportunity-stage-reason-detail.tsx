import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opportunity-stage-reason.reducer';

export const OpportunityStageReasonDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const opportunityStageReasonEntity = useAppSelector(state => state.opportunityStageReason.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opportunityStageReasonDetailsHeading">OpportunityStageReason</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{opportunityStageReasonEntity.id}</dd>
          <dt>
            <span id="opportunityStageReasonId">Opportunity Stage Reason Id</span>
          </dt>
          <dd>{opportunityStageReasonEntity.opportunityStageReasonId}</dd>
          <dt>
            <span id="opportunityStageId">Opportunity Stage Id</span>
          </dt>
          <dd>{opportunityStageReasonEntity.opportunityStageId}</dd>
          <dt>
            <span id="opportunityStageReasonName">Opportunity Stage Reason Name</span>
          </dt>
          <dd>{opportunityStageReasonEntity.opportunityStageReasonName}</dd>
        </dl>
        <Button tag={Link} to="/opportunity-stage-reason" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opportunity-stage-reason/${opportunityStageReasonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpportunityStageReasonDetail;
