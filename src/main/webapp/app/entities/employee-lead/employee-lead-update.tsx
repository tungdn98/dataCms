import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmployeeLead } from 'app/shared/model/employee-lead.model';
import { getEntity, updateEntity, createEntity, reset } from './employee-lead.reducer';

export const EmployeeLeadUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const employeeLeadEntity = useAppSelector(state => state.employeeLead.entity);
  const loading = useAppSelector(state => state.employeeLead.loading);
  const updating = useAppSelector(state => state.employeeLead.updating);
  const updateSuccess = useAppSelector(state => state.employeeLead.updateSuccess);
  const handleClose = () => {
    props.history.push('/employee-lead' + props.location.search);
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
      ...employeeLeadEntity,
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
          ...employeeLeadEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.employeeLead.home.createOrEditLabel" data-cy="EmployeeLeadCreateUpdateHeading">
            Create or edit a EmployeeLead
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
                <ValidatedField name="id" required readOnly id="employee-lead-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Lead Id" id="employee-lead-leadId" name="leadId" data-cy="leadId" type="text" />
              <ValidatedField label="Employee Id" id="employee-lead-employeeId" name="employeeId" data-cy="employeeId" type="text" />
              <ValidatedField
                label="Lead Code"
                id="employee-lead-leadCode"
                name="leadCode"
                data-cy="leadCode"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Lead Name" id="employee-lead-leadName" name="leadName" data-cy="leadName" type="text" />
              <ValidatedField
                label="Lead Potential Level Id"
                id="employee-lead-leadPotentialLevelId"
                name="leadPotentialLevelId"
                data-cy="leadPotentialLevelId"
                type="text"
              />
              <ValidatedField
                label="Lead Source Id"
                id="employee-lead-leadSourceId"
                name="leadSourceId"
                data-cy="leadSourceId"
                type="text"
              />
              <ValidatedField
                label="Lead Potential Level Name"
                id="employee-lead-leadPotentialLevelName"
                name="leadPotentialLevelName"
                data-cy="leadPotentialLevelName"
                type="text"
              />
              <ValidatedField
                label="Lead Source Name"
                id="employee-lead-leadSourceName"
                name="leadSourceName"
                data-cy="leadSourceName"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee-lead" replace color="info">
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

export default EmployeeLeadUpdate;
