import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './currency.reducer';

export const CurrencyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const currencyEntity = useAppSelector(state => state.currency.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="currencyDetailsHeading">Currency</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{currencyEntity.id}</dd>
          <dt>
            <span id="currencyId">Currency Id</span>
          </dt>
          <dd>{currencyEntity.currencyId}</dd>
          <dt>
            <span id="currencyNum">Currency Num</span>
          </dt>
          <dd>{currencyEntity.currencyNum}</dd>
          <dt>
            <span id="currencyCode">Currency Code</span>
          </dt>
          <dd>{currencyEntity.currencyCode}</dd>
          <dt>
            <span id="currencyName">Currency Name</span>
          </dt>
          <dd>{currencyEntity.currencyName}</dd>
          <dt>
            <span id="currencyExchangeRateId">Currency Exchange Rate Id</span>
          </dt>
          <dd>{currencyEntity.currencyExchangeRateId}</dd>
          <dt>
            <span id="conversionRate">Conversion Rate</span>
          </dt>
          <dd>{currencyEntity.conversionRate}</dd>
        </dl>
        <Button tag={Link} to="/currency" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/currency/${currencyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CurrencyDetail;
