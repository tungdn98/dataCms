import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './unit.reducer';

export const UnitDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const unitEntity = useAppSelector(state => state.unit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="unitDetailsHeading">Unit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{unitEntity.id}</dd>
          <dt>
            <span id="unitCode">Unit Code</span>
          </dt>
          <dd>{unitEntity.unitCode}</dd>
          <dt>
            <span id="unitName">Unit Name</span>
          </dt>
          <dd>{unitEntity.unitName}</dd>
        </dl>
        <Button tag={Link} to="/unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/unit/${unitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UnitDetail;
