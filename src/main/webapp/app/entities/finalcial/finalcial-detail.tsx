import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './finalcial.reducer';

export const FinalcialDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const finalcialEntity = useAppSelector(state => state.finalcial.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="finalcialDetailsHeading">Finalcial</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{finalcialEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{finalcialEntity.code}</dd>
          <dt>
            <span id="customerName">Customer Name</span>
          </dt>
          <dd>{finalcialEntity.customerName}</dd>
          <dt>
            <span id="customerShortName">Customer Short Name</span>
          </dt>
          <dd>{finalcialEntity.customerShortName}</dd>
          <dt>
            <span id="customerType">Customer Type</span>
          </dt>
          <dd>{finalcialEntity.customerType}</dd>
        </dl>
        <Button tag={Link} to="/finalcial" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/finalcial/${finalcialEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FinalcialDetail;
