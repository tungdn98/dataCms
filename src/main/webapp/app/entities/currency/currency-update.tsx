import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurrency } from 'app/shared/model/currency.model';
import { getEntity, updateEntity, createEntity, reset } from './currency.reducer';

export const CurrencyUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const currencyEntity = useAppSelector(state => state.currency.entity);
  const loading = useAppSelector(state => state.currency.loading);
  const updating = useAppSelector(state => state.currency.updating);
  const updateSuccess = useAppSelector(state => state.currency.updateSuccess);
  const handleClose = () => {
    props.history.push('/currency' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...currencyEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...currencyEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.currency.home.createOrEditLabel" data-cy="CurrencyCreateUpdateHeading">
            Create or edit a Currency
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="currency-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Currency Id" id="currency-currencyId" name="currencyId" data-cy="currencyId" type="text" />
              <ValidatedField label="Currency Num" id="currency-currencyNum" name="currencyNum" data-cy="currencyNum" type="text" />
              <ValidatedField label="Currency Code" id="currency-currencyCode" name="currencyCode" data-cy="currencyCode" type="text" />
              <ValidatedField label="Currency Name" id="currency-currencyName" name="currencyName" data-cy="currencyName" type="text" />
              <ValidatedField
                label="Currency Exchange Rate Id"
                id="currency-currencyExchangeRateId"
                name="currencyExchangeRateId"
                data-cy="currencyExchangeRateId"
                type="text"
              />
              <ValidatedField
                label="Conversion Rate"
                id="currency-conversionRate"
                name="conversionRate"
                data-cy="conversionRate"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/currency" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CurrencyUpdate;
