import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRoleGroup } from 'app/shared/model/role-group.model';
import { getEntities as getRoleGroups } from 'app/entities/role-group/role-group.reducer';
import { IEmpGroup } from 'app/shared/model/emp-group.model';
import { getEntities as getEmpGroups } from 'app/entities/emp-group/emp-group.reducer';
import { IRoles } from 'app/shared/model/roles.model';
import { getEntity, updateEntity, createEntity, reset } from './roles.reducer';

export const RolesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const roleGroups = useAppSelector(state => state.roleGroup.entities);
  const empGroups = useAppSelector(state => state.empGroup.entities);
  const rolesEntity = useAppSelector(state => state.roles.entity);
  const loading = useAppSelector(state => state.roles.loading);
  const updating = useAppSelector(state => state.roles.updating);
  const updateSuccess = useAppSelector(state => state.roles.updateSuccess);
  const handleClose = () => {
    props.history.push('/roles' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getRoleGroups({}));
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
      ...rolesEntity,
      ...values,
      roleGroup: roleGroups.find(it => it.id.toString() === values.roleGroup.toString()),
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
          ...rolesEntity,
          createdDate: convertDateTimeFromServer(rolesEntity.createdDate),
          lastModifiedDate: convertDateTimeFromServer(rolesEntity.lastModifiedDate),
          roleGroup: rolesEntity?.roleGroup?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.roles.home.createOrEditLabel" data-cy="RolesCreateUpdateHeading">
            Create or edit a Roles
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="roles-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Resource Url"
                id="roles-resourceUrl"
                name="resourceUrl"
                data-cy="resourceUrl"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 1, message: 'This field is required to be at least 1 characters.' },
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Resource Desc" id="roles-resourceDesc" name="resourceDesc" data-cy="resourceDesc" type="text" />
              <ValidatedField
                label="Created Date"
                id="roles-createdDate"
                name="createdDate"
                data-cy="createdDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Created By" id="roles-createdBy" name="createdBy" data-cy="createdBy" type="text" />
              <ValidatedField
                label="Last Modified Date"
                id="roles-lastModifiedDate"
                name="lastModifiedDate"
                data-cy="lastModifiedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Last Modified By"
                id="roles-lastModifiedBy"
                name="lastModifiedBy"
                data-cy="lastModifiedBy"
                type="text"
              />
              <ValidatedField id="roles-roleGroup" name="roleGroup" data-cy="roleGroup" label="Role Group" type="select">
                <option value="" key="0" />
                {roleGroups
                  ? roleGroups.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/roles" replace color="info">
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

export default RolesUpdate;
