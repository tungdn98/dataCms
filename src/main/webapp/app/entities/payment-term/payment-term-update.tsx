import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPaymentTerm } from 'app/shared/model/payment-term.model';
import { getEntity, updateEntity, createEntity, reset } from './payment-term.reducer';

export const PaymentTermUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const paymentTermEntity = useAppSelector(state => state.paymentTerm.entity);
  const loading = useAppSelector(state => state.paymentTerm.loading);
  const updating = useAppSelector(state => state.paymentTerm.updating);
  const updateSuccess = useAppSelector(state => state.paymentTerm.updateSuccess);
  const handleClose = () => {
    props.history.push('/payment-term' + props.location.search);
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
      ...paymentTermEntity,
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
          ...paymentTermEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.paymentTerm.home.createOrEditLabel" data-cy="PaymentTermCreateUpdateHeading">
            Create or edit a PaymentTerm
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="payment-term-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Payment Term Id"
                id="payment-term-paymentTermId"
                name="paymentTermId"
                data-cy="paymentTermId"
                type="text"
              />
              <ValidatedField
                label="Payment Term Code"
                id="payment-term-paymentTermCode"
                name="paymentTermCode"
                data-cy="paymentTermCode"
                type="text"
              />
              <ValidatedField
                label="Payment Term Name"
                id="payment-term-paymentTermName"
                name="paymentTermName"
                data-cy="paymentTermName"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payment-term" replace color="info">
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

export default PaymentTermUpdate;
