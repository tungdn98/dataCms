import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contract-type.reducer';

export const ContractTypeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contractTypeEntity = useAppSelector(state => state.contractType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contractTypeDetailsHeading">ContractType</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{contractTypeEntity.id}</dd>
          <dt>
            <span id="contractTypeId">Contract Type Id</span>
          </dt>
          <dd>{contractTypeEntity.contractTypeId}</dd>
          <dt>
            <span id="contractTypeName">Contract Type Name</span>
          </dt>
          <dd>{contractTypeEntity.contractTypeName}</dd>
          <dt>
            <span id="contractTypeCode">Contract Type Code</span>
          </dt>
          <dd>{contractTypeEntity.contractTypeCode}</dd>
        </dl>
        <Button tag={Link} to="/contract-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contract-type/${contractTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContractTypeDetail;
