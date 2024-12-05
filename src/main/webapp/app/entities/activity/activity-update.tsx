import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivity } from 'app/shared/model/activity.model';
import { getEntity, updateEntity, createEntity, reset } from './activity.reducer';

export const ActivityUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const activityEntity = useAppSelector(state => state.activity.entity);
  const loading = useAppSelector(state => state.activity.loading);
  const updating = useAppSelector(state => state.activity.updating);
  const updateSuccess = useAppSelector(state => state.activity.updateSuccess);
  const handleClose = () => {
    props.history.push('/activity' + props.location.search);
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
    values.createDate = convertDateTimeToServer(values.createDate);
    values.startDate = convertDateTimeToServer(values.startDate);
    values.closedOn = convertDateTimeToServer(values.closedOn);

    const entity = {
      ...activityEntity,
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
          createDate: displayDefaultDateTime(),
          startDate: displayDefaultDateTime(),
          closedOn: displayDefaultDateTime(),
        }
      : {
          ...activityEntity,
          createDate: convertDateTimeFromServer(activityEntity.createDate),
          startDate: convertDateTimeFromServer(activityEntity.startDate),
          closedOn: convertDateTimeFromServer(activityEntity.closedOn),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.activity.home.createOrEditLabel" data-cy="ActivityCreateUpdateHeading">
            Create or edit a Activity
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="activity-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Activity Id" id="activity-activityId" name="activityId" data-cy="activityId" type="text" />
              <ValidatedField label="Company Id" id="activity-companyId" name="companyId" data-cy="companyId" type="text" />
              <ValidatedField
                label="Create Date"
                id="activity-createDate"
                name="createDate"
                data-cy="createDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Deadline" id="activity-deadline" name="deadline" data-cy="deadline" type="date" />
              <ValidatedField label="Name" id="activity-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="State" id="activity-state" name="state" data-cy="state" type="text" />
              <ValidatedField label="Type" id="activity-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Account Id" id="activity-accountId" name="accountId" data-cy="accountId" type="text" />
              <ValidatedField
                label="Activity Type Id"
                id="activity-activityTypeId"
                name="activityTypeId"
                data-cy="activityTypeId"
                type="text"
              />
              <ValidatedField label="Object Type Id" id="activity-objectTypeId" name="objectTypeId" data-cy="objectTypeId" type="text" />
              <ValidatedField label="Priority Id" id="activity-priorityId" name="priorityId" data-cy="priorityId" type="text" />
              <ValidatedField label="Opportunity Id" id="activity-opportunityId" name="opportunityId" data-cy="opportunityId" type="text" />
              <ValidatedField label="Order Id" id="activity-orderId" name="orderId" data-cy="orderId" type="text" />
              <ValidatedField label="Contract Id" id="activity-contractId" name="contractId" data-cy="contractId" type="text" />
              <ValidatedField label="Priority Name" id="activity-priorityName" name="priorityName" data-cy="priorityName" type="text" />
              <ValidatedField label="Responsible Id" id="activity-responsibleId" name="responsibleId" data-cy="responsibleId" type="text" />
              <ValidatedField
                label="Start Date"
                id="activity-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Closed On"
                id="activity-closedOn"
                name="closedOn"
                data-cy="closedOn"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label="Duration" id="activity-duration" name="duration" data-cy="duration" type="text" />
              <ValidatedField
                label="Duration Unit Id"
                id="activity-durationUnitId"
                name="durationUnitId"
                data-cy="durationUnitId"
                type="text"
              />
              <ValidatedField label="Conversion" id="activity-conversion" name="conversion" data-cy="conversion" type="text" />
              <ValidatedField label="Text Str" id="activity-textStr" name="textStr" data-cy="textStr" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity" replace color="info">
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

export default ActivityUpdate;
