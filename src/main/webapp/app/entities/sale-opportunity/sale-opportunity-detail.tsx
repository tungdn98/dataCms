import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sale-opportunity.reducer';

export const SaleOpportunityDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const saleOpportunityEntity = useAppSelector(state => state.saleOpportunity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="saleOpportunityDetailsHeading">SaleOpportunity</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{saleOpportunityEntity.id}</dd>
          <dt>
            <span id="opportunityId">Opportunity Id</span>
          </dt>
          <dd>{saleOpportunityEntity.opportunityId}</dd>
          <dt>
            <span id="opportunityCode">Opportunity Code</span>
          </dt>
          <dd>{saleOpportunityEntity.opportunityCode}</dd>
          <dt>
            <span id="opportunityName">Opportunity Name</span>
          </dt>
          <dd>{saleOpportunityEntity.opportunityName}</dd>
          <dt>
            <span id="opportunityTypeName">Opportunity Type Name</span>
          </dt>
          <dd>{saleOpportunityEntity.opportunityTypeName}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {saleOpportunityEntity.startDate ? (
              <TextFormat value={saleOpportunityEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="closeDate">Close Date</span>
          </dt>
          <dd>
            {saleOpportunityEntity.closeDate ? (
              <TextFormat value={saleOpportunityEntity.closeDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="stageId">Stage Id</span>
          </dt>
          <dd>{saleOpportunityEntity.stageId}</dd>
          <dt>
            <span id="stageReasonId">Stage Reason Id</span>
          </dt>
          <dd>{saleOpportunityEntity.stageReasonId}</dd>
          <dt>
            <span id="employeeId">Employee Id</span>
          </dt>
          <dd>{saleOpportunityEntity.employeeId}</dd>
          <dt>
            <span id="leadId">Lead Id</span>
          </dt>
          <dd>{saleOpportunityEntity.leadId}</dd>
          <dt>
            <span id="currencyCode">Currency Code</span>
          </dt>
          <dd>{saleOpportunityEntity.currencyCode}</dd>
          <dt>
            <span id="accountId">Account Id</span>
          </dt>
          <dd>{saleOpportunityEntity.accountId}</dd>
          <dt>
            <span id="productId">Product Id</span>
          </dt>
          <dd>{saleOpportunityEntity.productId}</dd>
          <dt>
            <span id="salesPricePrd">Sales Price Prd</span>
          </dt>
          <dd>{saleOpportunityEntity.salesPricePrd}</dd>
          <dt>
            <span id="value">Value</span>
          </dt>
          <dd>{saleOpportunityEntity.value}</dd>
        </dl>
        <Button tag={Link} to="/sale-opportunity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sale-opportunity/${saleOpportunityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SaleOpportunityDetail;
