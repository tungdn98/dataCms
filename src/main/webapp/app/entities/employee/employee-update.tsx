import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEmpGroup } from 'app/shared/model/emp-group.model';
import { getEntities as getEmpGroups } from 'app/entities/emp-group/emp-group.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntity, updateEntity, createEntity, reset } from './employee.reducer';

export const EmployeeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const empGroups = useAppSelector(state => state.empGroup.entities);
  const employeeEntity = useAppSelector(state => state.employee.entity);
  const loading = useAppSelector(state => state.employee.loading);
  const updating = useAppSelector(state => state.employee.updating);
  const updateSuccess = useAppSelector(state => state.employee.updateSuccess);
  const handleClose = () => {
    props.history.push('/employee' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getEmpGroups({}));
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
      ...employeeEntity,
      ...values,
      empGroup: empGroups.find(it => it.id.toString() === values.empGroup.toString()),
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
          ...employeeEntity,
          createdDate: convertDateTimeFromServer(employeeEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(employeeEntity.lastModifiedDate),
          empGroup: employeeEntity?.empGroup?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.employee.home.createOrEditLabel" data-cy="EmployeeCreateUpdateHeading">
            Create or edit a Employee
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="employee-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Employee Code"
                id="employee-employeeCode"
                name="employeeCode"
                data-cy="employeeCode"
                type="text"
                validate={{
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Employee Name"
                id="employee-employeeName"
                name="employeeName"
                data-cy="employeeName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Username"
                id="employee-username"
                name="username"
                data-cy="username"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Password" id="employee-password" name="password" data-cy="password" type="text" />
              <ValidatedField label="Active" id="employee-active" name="active" data-cy="active" type="text" />
              <ValidatedField label="Company Code" id="employee-companyCode" name="companyCode" data-cy="companyCode" type="text" />
              <ValidatedField label="Company Name" id="employee-companyName" name="companyName" data-cy="companyName" type="text" />
              <ValidatedField
                label="Organization Id"
                id="employee-organizationId"
                name="organizationId"
                data-cy="organizationId"
                type="text"
              />
              <ValidatedField
                label="Employee Last Name"
                id="employee-employeeLastName"
                name="employeeLastName"
                data-cy="employeeLastName"
                type="text"
              />
              <ValidatedField
                label="Employee Middle Name"
                id="employee-employeeMiddleName"
                name="employeeMiddleName"
                data-cy="employeeMiddleName"
                type="text"
              />
              <ValidatedField
                label="Employee Title Id"
                id="employee-employeeTitleId"
                name="employeeTitleId"
                data-cy="employeeTitleId"
                type="text"
              />
              <ValidatedField
                label="Employee Title Name"
                id="employee-employeeTitleName"
                name="employeeTitleName"
                data-cy="employeeTitleName"
                type="text"
              />
              <ValidatedField
                label="Employee Full Name"
                id="employee-employeeFullName"
                name="employeeFullName"
                data-cy="employeeFullName"
                type="text"
              />
              <ValidatedField
                label="Created Date"
                id="employee-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="employee-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Last Modified Date"
                id="employee-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="employee-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
              />
              <ValidatedField id="employee-empGroup" name="empGroup" data-cy="empGroup" label="Emp Group" type="select">
                <option value="" key="0" />
                {empGroups
                  ? empGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/employee" replace color="info">
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

export default EmployeeUpdate;
