import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IConfigLog } from 'app/shared/model/config-log.model';
import { getEntity, updateEntity, createEntity, reset } from './config-log.reducer';

export const ConfigLogUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const configLogEntity = useAppSelector(state => state.configLog.entity);
  const loading = useAppSelector(state => state.configLog.loading);
  const updating = useAppSelector(state => state.configLog.updating);
  const updateSuccess = useAppSelector(state => state.configLog.updateSuccess);
  const handleClose = () => {
    props.history.push('/config-log' + props.location.search);
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
    values.modifiedDate = convertDateTimeToServer(values.modifiedDate);

    const entity = {
      ...configLogEntity,
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
          modifiedDate: displayDefaultDateTime(),
        }
      : {
          ...configLogEntity,
          modifiedDate: convertDateTimeFromServer(configLogEntity.modifiedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.configLog.home.createOrEditLabel" data-cy="ConfigLogCreateUpdateHeading">
            Create or edit a ConfigLog
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="config-log-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Config Name"
                id="config-log-configName"
                name="configName"
                data-cy="configName"
                type="text"
                validate={{
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Value Before" id="config-log-valueBefore" name="valueBefore" data-cy="valueBefore" type="text" />
              <ValidatedField label="Value After" id="config-log-valueAfter" name="valueAfter" data-cy="valueAfter" type="text" />
              <ValidatedField
                label="Modified Date"
                id="config-log-modifiedDate"
                name="modifiedDate"
                data-cy="modifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Modified Username"
                id="config-log-modifiedUsername"
                name="modifiedUsername"
                data-cy="modifiedUsername"
                type="text"
              />
              <ValidatedField
                label="Modified Fullname"
                id="config-log-modifiedFullname"
                name="modifiedFullname"
                data-cy="modifiedFullname"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/config-log" replace color="info">
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

export default ConfigLogUpdate;
