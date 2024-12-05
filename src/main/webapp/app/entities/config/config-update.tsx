import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConfig } from 'app/shared/model/config.model';
import { getEntity, updateEntity, createEntity, reset } from './config.reducer';

export const ConfigUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const configEntity = useAppSelector(state => state.config.entity);
  const loading = useAppSelector(state => state.config.loading);
  const updating = useAppSelector(state => state.config.updating);
  const updateSuccess = useAppSelector(state => state.config.updateSuccess);
  const handleClose = () => {
    props.history.push('/config' + props.location.search);
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
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    const entity = {
      ...configEntity,
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
      ? {
          createdDate: displayDefaultDateTime(),
          lastModifiedDate: displayDefaultDateTime(),
        }
      : {
          ...configEntity,
          createdDate: convertDateTimeFromServer(configEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(configEntity.lastModifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.config.home.createOrEditLabel" data-cy="ConfigCreateUpdateHeading">
            Create or edit a Config
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="config-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Config Name"
                id="config-configName"
                name="configName"
                data-cy="configName"
                type="text"
                validate={{
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Config Value"
                id="config-configValue"
                name="configValue"
                data-cy="configValue"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Config Desc" id="config-configDesc" name="configDesc" data-cy="configDesc" type="text" />
              <ValidatedField
                label="Created Date"
                id="config-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="config-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Last Modified Date"
                id="config-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="config-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/config" replace color="info">
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

export default ConfigUpdate;
