import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoles } from 'app/shared/model/roles.model';
import { getEntities as getRoles } from 'app/entities/roles/roles.reducer';
import { IEmpGroup } from 'app/shared/model/emp-group.model';
import { getEntity, updateEntity, createEntity, reset } from './emp-group.reducer';

export const EmpGroupUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const roles = useAppSelector(state => state.roles.entities);
  const empGroupEntity = useAppSelector(state => state.empGroup.entity);
  const loading = useAppSelector(state => state.empGroup.loading);
  const updating = useAppSelector(state => state.empGroup.updating);
  const updateSuccess = useAppSelector(state => state.empGroup.updateSuccess);
  const handleClose = () => {
    props.history.push('/emp-group' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRoles({}));
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
      ...empGroupEntity,
      ...values,
      roles: mapIdList(values.roles),
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
          ...empGroupEntity,
          createdDate: convertDateTimeFromServer(empGroupEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(empGroupEntity.lastModifiedDate),
          roles: empGroupEntity?.roles?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.empGroup.home.createOrEditLabel" data-cy="EmpGroupCreateUpdateHeading">
            Create or edit a EmpGroup
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="emp-group-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Group Name"
                id="emp-group-groupName"
                name="groupName"
                data-cy="groupName"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Created Date"
                id="emp-group-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="emp-group-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Last Modified Date"
                id="emp-group-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="emp-group-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
              />
              <ValidatedField label="Role" id="emp-group-role" data-cy="role" type="select" multiple name="roles">
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/emp-group" replace color="info">
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

export default EmpGroupUpdate;
