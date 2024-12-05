import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IActivityType } from 'app/shared/model/activity-type.model';
import { getEntity, updateEntity, createEntity, reset } from './activity-type.reducer';

export const ActivityTypeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const activityTypeEntity = useAppSelector(state => state.activityType.entity);
  const loading = useAppSelector(state => state.activityType.loading);
  const updating = useAppSelector(state => state.activityType.updating);
  const updateSuccess = useAppSelector(state => state.activityType.updateSuccess);
  const handleClose = () => {
    props.history.push('/activity-type' + props.location.search);
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
      ...activityTypeEntity,
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
          ...activityTypeEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataCmsApp.activityType.home.createOrEditLabel" data-cy="ActivityTypeCreateUpdateHeading">
            Create or edit a ActivityType
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
                <ValidatedField name="id" required readOnly id="activity-type-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Activity Type Id"
                id="activity-type-activityTypeId"
                name="activityTypeId"
                data-cy="activityTypeId"
                type="text"
              />
              <ValidatedField
                label="Activity Type"
                id="activity-type-activityType"
                name="activityType"
                data-cy="activityType"
                type="text"
              />
              <ValidatedField label="Text Str" id="activity-type-textStr" name="textStr" data-cy="textStr" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/activity-type" replace color="info">
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

export default ActivityTypeUpdate;
