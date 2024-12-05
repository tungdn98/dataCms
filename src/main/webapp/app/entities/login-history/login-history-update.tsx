import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILoginHistory } from 'app/shared/model/login-history.model';
import { getEntity, updateEntity, createEntity, reset } from './login-history.reducer';

export const LoginHistoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const loginHistoryEntity = useAppSelector(state => state.loginHistory.entity);
  const loading = useAppSelector(state => state.loginHistory.loading);
  const updating = useAppSelector(state => state.loginHistory.updating);
  const updateSuccess = useAppSelector(state => state.loginHistory.updateSuccess);
  const handleClose = () => {
    props.history.push('/login-history' + props.location.search);
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
    values.loginTime = convertDateTimeToServer(values.loginTime);

    const entity = {
      ...loginHistoryEntity,
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
          loginTime: displayDefaultDateTime(),
        }
      : {
          ...loginHistoryEntity,
          loginTime: convertDateTimeFromServer(loginHistoryEntity.loginTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.loginHistory.home.createOrEditLabel" data-cy="LoginHistoryCreateUpdateHeading">
            Create or edit a LoginHistory
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
                <ValidatedField name="id" required readOnly id="login-history-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Emp Code" id="login-history-empCode" name="empCode" data-cy="empCode" type="text" />
              <ValidatedField label="Emp Username" id="login-history-empUsername" name="empUsername" data-cy="empUsername" type="text" />
              <ValidatedField label="Emp Full Name" id="login-history-empFullName" name="empFullName" data-cy="empFullName" type="text" />
              <ValidatedField label="Login Ip" id="login-history-loginIp" name="loginIp" data-cy="loginIp" type="text" />
              <ValidatedField
                label="Login Time"
                id="login-history-loginTime"
                name="loginTime"
                data-cy="loginTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/login-history" replace color="info">
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

export default LoginHistoryUpdate;
