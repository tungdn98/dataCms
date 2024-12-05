import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opportunity-stage.reducer';

export const OpportunityStageDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const opportunityStageEntity = useAppSelector(state => state.opportunityStage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opportunityStageDetailsHeading">OpportunityStage</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{opportunityStageEntity.id}</dd>
          <dt>
            <span id="opportunityStageId">Opportunity Stage Id</span>
          </dt>
          <dd>{opportunityStageEntity.opportunityStageId}</dd>
          <dt>
            <span id="opportunityStageName">Opportunity Stage Name</span>
          </dt>
          <dd>{opportunityStageEntity.opportunityStageName}</dd>
          <dt>
            <span id="opportunityStageCode">Opportunity Stage Code</span>
          </dt>
          <dd>{opportunityStageEntity.opportunityStageCode}</dd>
        </dl>
        <Button tag={Link} to="/opportunity-stage" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opportunity-stage/${opportunityStageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpportunityStageDetail;
