import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICustomer } from 'app/shared/model/customer.model';
import { getEntity, updateEntity, createEntity, reset } from './customer.reducer';

export const CustomerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const customerEntity = useAppSelector(state => state.customer.entity);
  const loading = useAppSelector(state => state.customer.loading);
  const updating = useAppSelector(state => state.customer.updating);
  const updateSuccess = useAppSelector(state => state.customer.updateSuccess);
  const handleClose = () => {
    props.history.push('/customer' + props.location.search);
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
      ...customerEntity,
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
          ...customerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.customer.home.createOrEditLabel" data-cy="CustomerCreateUpdateHeading">
            Create or edit a Customer
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="customer-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Account Id" id="customer-accountId" name="accountId" data-cy="accountId" type="text" />
              <ValidatedField label="Account Code" id="customer-accountCode" name="accountCode" data-cy="accountCode" type="text" />
              <ValidatedField label="Account Name" id="customer-accountName" name="accountName" data-cy="accountName" type="text" />
              <ValidatedField
                label="Mapping Account"
                id="customer-mappingAccount"
                name="mappingAccount"
                data-cy="mappingAccount"
                type="text"
              />
              <ValidatedField label="Account Email" id="customer-accountEmail" name="accountEmail" data-cy="accountEmail" type="text" />
              <ValidatedField label="Account Phone" id="customer-accountPhone" name="accountPhone" data-cy="accountPhone" type="text" />
              <ValidatedField
                label="Account Type Name"
                id="customer-accountTypeName"
                name="accountTypeName"
                data-cy="accountTypeName"
                type="text"
              />
              <ValidatedField label="Gender Name" id="customer-genderName" name="genderName" data-cy="genderName" type="text" />
              <ValidatedField label="Industry Name" id="customer-industryName" name="industryName" data-cy="industryName" type="text" />
              <ValidatedField
                label="Owner Employee Id"
                id="customer-ownerEmployeeId"
                name="ownerEmployeeId"
                data-cy="ownerEmployeeId"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer" replace color="info">
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

export default CustomerUpdate;
