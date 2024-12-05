import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment-term.reducer';

export const PaymentTermDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paymentTermEntity = useAppSelector(state => state.paymentTerm.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentTermDetailsHeading">PaymentTerm</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{paymentTermEntity.id}</dd>
          <dt>
            <span id="paymentTermId">Payment Term Id</span>
          </dt>
          <dd>{paymentTermEntity.paymentTermId}</dd>
          <dt>
            <span id="paymentTermCode">Payment Term Code</span>
          </dt>
          <dd>{paymentTermEntity.paymentTermCode}</dd>
          <dt>
            <span id="paymentTermName">Payment Term Name</span>
          </dt>
          <dd>{paymentTermEntity.paymentTermName}</dd>
        </dl>
        <Button tag={Link} to="/payment-term" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment-term/${paymentTermEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentTermDetail;
