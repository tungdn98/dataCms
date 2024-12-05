import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer.reducer';

export const CustomerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">Customer</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="accountId">Account Id</span>
          </dt>
          <dd>{customerEntity.accountId}</dd>
          <dt>
            <span id="accountCode">Account Code</span>
          </dt>
          <dd>{customerEntity.accountCode}</dd>
          <dt>
            <span id="accountName">Account Name</span>
          </dt>
          <dd>{customerEntity.accountName}</dd>
          <dt>
            <span id="mappingAccount">Mapping Account</span>
          </dt>
          <dd>{customerEntity.mappingAccount}</dd>
          <dt>
            <span id="accountEmail">Account Email</span>
          </dt>
          <dd>{customerEntity.accountEmail}</dd>
          <dt>
            <span id="accountPhone">Account Phone</span>
          </dt>
          <dd>{customerEntity.accountPhone}</dd>
          <dt>
            <span id="accountTypeName">Account Type Name</span>
          </dt>
          <dd>{customerEntity.accountTypeName}</dd>
          <dt>
            <span id="genderName">Gender Name</span>
          </dt>
          <dd>{customerEntity.genderName}</dd>
          <dt>
            <span id="industryName">Industry Name</span>
          </dt>
          <dd>{customerEntity.industryName}</dd>
          <dt>
            <span id="ownerEmployeeId">Owner Employee Id</span>
          </dt>
          <dd>{customerEntity.ownerEmployeeId}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
