import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payment-method.reducer';

export const PaymentMethodDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paymentMethodEntity = useAppSelector(state => state.paymentMethod.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentMethodDetailsHeading">PaymentMethod</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{paymentMethodEntity.id}</dd>
          <dt>
            <span id="paymentStatusId">Payment Status Id</span>
          </dt>
          <dd>{paymentMethodEntity.paymentStatusId}</dd>
          <dt>
            <span id="paymentStatusName">Payment Status Name</span>
          </dt>
          <dd>{paymentMethodEntity.paymentStatusName}</dd>
        </dl>
        <Button tag={Link} to="/payment-method" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment-method/${paymentMethodEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaymentMethodDetail;
