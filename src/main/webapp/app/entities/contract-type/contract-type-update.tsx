import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContractType } from 'app/shared/model/contract-type.model';
import { getEntity, updateEntity, createEntity, reset } from './contract-type.reducer';

export const ContractTypeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const contractTypeEntity = useAppSelector(state => state.contractType.entity);
  const loading = useAppSelector(state => state.contractType.loading);
  const updating = useAppSelector(state => state.contractType.updating);
  const updateSuccess = useAppSelector(state => state.contractType.updateSuccess);
  const handleClose = () => {
    props.history.push('/contract-type' + props.location.search);
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
      ...contractTypeEntity,
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
          ...contractTypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.contractType.home.createOrEditLabel" data-cy="ContractTypeCreateUpdateHeading">
            Create or edit a ContractType
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="contract-type-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Contract Type Id"
                id="contract-type-contractTypeId"
                name="contractTypeId"
                data-cy="contractTypeId"
                type="text"
              />
              <ValidatedField
                label="Contract Type Name"
                id="contract-type-contractTypeName"
                name="contractTypeName"
                data-cy="contractTypeName"
                type="text"
              />
              <ValidatedField
                label="Contract Type Code"
                id="contract-type-contractTypeCode"
                name="contractTypeCode"
                data-cy="contractTypeCode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contract-type" replace color="info">
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

export default ContractTypeUpdate;
